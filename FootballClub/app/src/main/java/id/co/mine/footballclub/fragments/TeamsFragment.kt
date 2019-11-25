package id.co.mine.footballclub.fragments


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.*
import android.widget.*
import com.google.gson.Gson


import id.co.mine.footballclub.R
import id.co.mine.footballclub.R.array.league
import id.co.mine.footballclub.R.id.search
import id.co.mine.footballclub.R.menu.search_menu
import id.co.mine.footballclub.detail.DetailActivity

import id.co.mine.footballclub.model.Team
import id.co.mine.footballclub.teams.TeamAdapter
import id.co.mine.footballclub.teams.TeamPresenter
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.UI
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.swipeRefreshLayout

import id.co.mine.footballclub.api.ApiRepository
import id.co.mine.footballclub.teams.TeamDetailActivity
import id.co.mine.footballclub.teams.TeamView
import id.co.mine.footballclub.util.invisible
import id.co.mine.footballclub.util.visible


/**
 * A simple [Fragment] subclass.
 */
class TeamsFragment : Fragment(), TeamView {

    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var listTeam: RecyclerView
    private lateinit var progressBar: ProgressBar

    private var teams: MutableList<Team> = mutableListOf()
    private var filtered: MutableList<Team> = mutableListOf()
    private lateinit var presenter: TeamPresenter
    private lateinit var adapter: TeamAdapter

    private lateinit var spinner: Spinner

    private lateinit var searchView: SearchView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        adapter = TeamAdapter(teams) {
            ctx.startActivity<TeamDetailActivity>( "teamid" to "${it.teamId}",
                    "teamBadge" to "${it.teamBadge}",
                    "stadium" to "${it.stadium}",
                    "desc" to "${it.teamDesc}", "year" to "${it.formedYear}",
                    "nama" to "${it.teamName}"
                    )
        }
        listTeam.adapter = adapter

        val request = ApiRepository()
        val gson = Gson()

        val spinnerItems = resources.getStringArray(league)
        val spinnerAdapter = ArrayAdapter(ctx, android.R.layout.simple_spinner_dropdown_item, spinnerItems)
        spinner.adapter = spinnerAdapter

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                val selected = spinner.selectedItem.toString()
                presenter = TeamPresenter(this@TeamsFragment, request, gson)
                presenter.getTeamList(selected)

                swipeRefresh.onRefresh {
                    presenter.getTeamList(selected)
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>) {}
        }


    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return UI {
            linearLayout {
                lparams(width = matchParent, height = wrapContent)
                orientation = LinearLayout.VERTICAL
                topPadding = dip(16)
                leftPadding = dip(16)
                rightPadding = dip(16)

                spinner = spinner()

                swipeRefresh = swipeRefreshLayout {
                    id = R.id.swipe
                    setColorSchemeResources(R.color.colorAccent,
                            android.R.color.holo_green_light,
                            android.R.color.holo_orange_light,
                            android.R.color.holo_red_light
                    )

                    relativeLayout {
                        lparams(width = matchParent, height = wrapContent)

                        listTeam = recyclerView {
                            id = R.id.list_teams
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
    override fun showTeamList(data: List<Team>) {
        swipeRefresh.isRefreshing = false
        teams.clear()
        filtered.clear()
        teams.addAll(data)
        filtered.addAll(data)
        adapter.notifyDataSetChanged()
    }

    override fun hideLoading() {
        progressBar.invisible()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(search_menu, menu)
        val searchItem: MenuItem = menu.findItem(search)

        searchView = SearchView(activity)
        searchView.queryHint = "Cari..."
        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextChange(p0: String): Boolean {

                teams.clear()
                if(p0.equals("")) teams.addAll(filtered)
                filtered.forEach {
                    if(it.teamName!!.toString().toLowerCase().contains(p0.toLowerCase())){
                        teams.add(it)
                    }
                }
                adapter.notifyDataSetChanged()
                return true
            }

            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }
        })

        searchItem.setActionView(searchView)
    }





}// Required empty public constructor
