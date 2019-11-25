package id.co.mine.footballclub.teams


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import com.google.gson.Gson

import id.co.mine.footballclub.R
import id.co.mine.footballclub.R.id.list_players

import id.co.mine.footballclub.api.ApiRepository
import id.co.mine.footballclub.detail.DetailActivity
import id.co.mine.footballclub.model.Event
import id.co.mine.footballclub.model.Player
import id.co.mine.footballclub.player.PlayerAdapter
import id.co.mine.footballclub.player.PlayerDetailActivity
import id.co.mine.footballclub.player.PlayerPresenter
import id.co.mine.footballclub.player.PlayerView
import id.co.mine.footballclub.util.invisible
import id.co.mine.footballclub.util.visible
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.UI
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.swipeRefreshLayout
import java.io.Serializable


/**
 * A simple [Fragment] subclass.
 */
class PlayersFragment : Fragment(), PlayerView {

    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var listPlayer: RecyclerView
    private lateinit var progressBar: ProgressBar

    private var players: MutableList<Player> = mutableListOf()
    private lateinit var adapter: PlayerAdapter
    private lateinit var presenter: PlayerPresenter

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val sharedPreferences = this.activity!!.getSharedPreferences("preferensiKu", Context.MODE_PRIVATE)
        val teamName = sharedPreferences.getString("namaTeam", "")

        adapter = PlayerAdapter(players) {
           ctx.startActivity<PlayerDetailActivity>("thumbnail" to "${it.playerThumb}",
                   "name" to "${it.playerName}", "weight" to "${it.playerWeight}", "height" to "${it.playerHeight}",
                   "position" to "${it.playerPosition}","desc" to "${it.playerDescEN}"
                   )
        }
        listPlayer.adapter = adapter

        val request = ApiRepository()
        val gson = Gson()
        presenter = PlayerPresenter(this@PlayersFragment, request, gson)
        presenter.getPlayerList(teamName)


        swipeRefresh.onRefresh {
            presenter.getPlayerList(teamName)
        }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return UI {
                frameLayout {
                    lparams(width = matchParent, height = wrapContent)
                    topPadding = dip(16)
                    leftPadding = dip(16)
                    rightPadding = dip(16)

                    swipeRefresh = swipeRefreshLayout {
                        setColorSchemeResources(R.color.colorAccent,
                                android.R.color.holo_green_light,
                                android.R.color.holo_orange_light,
                                android.R.color.holo_red_light
                        )

                        relativeLayout {
                            lparams(width = matchParent, height = wrapContent)

                            listPlayer = recyclerView {
                                id = list_players
                                lparams(width = matchParent, height = wrapContent)
                                layoutManager = LinearLayoutManager(ctx)
                            }

                            progressBar = progressBar {
                            }.lparams {
                                centerHorizontally()
                            }
                        }
                    }
            }

        }.view
    }

    override fun showLoading() {
        progressBar.visible()
    }

    override fun hideLoading() {
        progressBar.invisible()
    }

    override fun showPlayerList(data: List<Player>) {
        swipeRefresh.isRefreshing = false
        players.clear()
        players.addAll(data)
        adapter.notifyDataSetChanged()
    }


}
