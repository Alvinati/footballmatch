package id.co.mine.footballclub.main

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.*
import android.widget.*
import com.google.gson.Gson
import id.co.mine.footballclub.R.array.nextleague

import id.co.mine.footballclub.R.color.colorAccent
import id.co.mine.footballclub.R.id.search
import id.co.mine.footballclub.R.menu.search_menu
import id.co.mine.footballclub.api.ApiRepository
import id.co.mine.footballclub.detail.DetailActivity
import id.co.mine.footballclub.model.Event
import id.co.mine.footballclub.util.invisible
import id.co.mine.footballclub.util.visible
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.UI
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.swipeRefreshLayout


class NextMatchFragment : Fragment(), MainView {

    private lateinit var listTeam: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var swipeRefresh: SwipeRefreshLayout

    private var events: MutableList<Event> = mutableListOf()
    private lateinit var presenter: MainPresenter
    private lateinit var adapter: MainAdapter
    private var filtered: MutableList<Event> = mutableListOf()

    private lateinit var searchView: SearchView



    private lateinit var spinner: Spinner


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setHasOptionsMenu(true)
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        adapter = MainAdapter(events){
            ctx.startActivity<DetailActivity>("eventid" to "${it.eventId}")
        }
        listTeam.adapter = adapter

        val request = ApiRepository()
        val gson = Gson()

        val spinnerItems = resources.getStringArray(nextleague)
        val spinnerAdapter = ArrayAdapter(ctx, android.R.layout.simple_spinner_dropdown_item, spinnerItems)
        spinner.adapter = spinnerAdapter

        val leagueid = arrayOf("4331", "4332", "4331", "4335")
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                presenter = MainPresenter(this@NextMatchFragment, request, gson)
                presenter.getEventList(leagueid[position], "eventsnextleague.php")

                swipeRefresh.onRefresh {
                    presenter.getEventList(leagueid[position], "eventsnextleague.php")
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
                topPadding = dip(10)

                spinner = spinner {
                    leftPadding = dip(16)
                    rightPadding = dip(16)
                }
                swipeRefresh = swipeRefreshLayout {
                    setColorSchemeResources(colorAccent,
                            android.R.color.holo_green_light,
                            android.R.color.holo_orange_light,
                            android.R.color.holo_red_light
                    )


                    relativeLayout {
                        lparams(width = matchParent, height = wrapContent)

                        listTeam = recyclerView {
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

    override fun showEventList(data: List<Event>) {
        swipeRefresh.isRefreshing = false
        events.clear()
        filtered.clear()
        events.addAll(data)
        filtered.addAll(data)
        adapter.notifyDataSetChanged()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(search_menu, menu)
        val searchItem: MenuItem = menu.findItem(search)

        searchView = SearchView(activity)
        searchView.queryHint = "Cari..."
        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextChange(p0: String): Boolean {


                events.clear()
                if(p0.equals("")) events.addAll(filtered)
                filtered.forEach {
                    if(it.homeTeam!!.toString().toLowerCase().contains(p0.toLowerCase()) ||
                            it.awayTeam!!.toString().toLowerCase().contains(p0.toLowerCase())){
                        events.add(it)
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

}
