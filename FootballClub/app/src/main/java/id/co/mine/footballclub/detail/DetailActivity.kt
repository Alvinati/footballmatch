package id.co.mine.footballclub.detail

import android.database.sqlite.SQLiteConstraintException
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView

import com.google.gson.Gson
import com.squareup.picasso.Picasso

import id.co.mine.footballclub.R
import id.co.mine.footballclub.R.id.*
import id.co.mine.footballclub.api.ApiRepository
import id.co.mine.footballclub.api.TheSportDBApi
import id.co.mine.footballclub.model.Detail
import id.co.mine.footballclub.model.TeamResponse
import id.co.mine.footballclub.util.gone
import id.co.mine.footballclub.util.visible
import id.co.mine.footballclub.R.menu.detail_menu
import id.co.mine.footballclub.db.database
import id.co.mine.footballclub.model.Event
import id.co.mine.footballclub.R.drawable.ic_add_to_favorites
import id.co.mine.footballclub.R.drawable.ic_added_to_favorites
import id.co.mine.footballclub.db.FavoriteEvent

import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import org.jetbrains.anko.design.snackbar
import org.jetbrains.anko.support.v4.onRefresh

import java.text.SimpleDateFormat
import java.util.*


class DetailActivity : AppCompatActivity(), DetailView {

    private lateinit var events: Event

    private lateinit var eventDate: TextView
    private lateinit var homeLogo: ImageView
    private lateinit var awayLogo: ImageView
    private lateinit var homeTeam: TextView
    private lateinit var awayTeam: TextView
    private lateinit var awayScore: TextView
    private lateinit var homeScore: TextView
    private lateinit var swipeRefresh: SwipeRefreshLayout


    private lateinit var homeGD: TextView
    private lateinit var awayGD: TextView
    private lateinit var homeShots: TextView
    private lateinit var awayShots: TextView

    private lateinit var homeGK: TextView
    private lateinit var awayGK: TextView
    private lateinit var homeDefense: TextView
    private lateinit var awayDefense: TextView
    private lateinit var homeMidfield: TextView
    private lateinit var awayMidfield: TextView
    private lateinit var homeForward: TextView
    private lateinit var awayForward: TextView
    private lateinit var homeSubtitute: TextView
    private lateinit var awaySubtitute: TextView

    private lateinit var progressBar: ProgressBar
    private lateinit var presenter: DetailPresenter

    private var menuItem : Menu? = null
    private var isFavorite: Boolean = false

    private lateinit var eventId: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        eventId = intent.getStringExtra("eventid")
        supportActionBar?.title = "Event Detail"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        swipeRefresh = findViewById(swipe_detail)

        eventDate = findViewById(event_date)
        homeLogo = findViewById(home_logo)
        awayLogo = findViewById(away_logo)
        homeTeam = findViewById(home_team)
        awayTeam = findViewById(away_team)
        awayScore = findViewById(away_score)
        homeScore = findViewById(home_score)

        homeGD = findViewById(home_goal_detail)
        awayGD = findViewById(away_goal_detail)
        homeShots = findViewById(home_shot)
        awayShots = findViewById(away_shot)

        homeGK = findViewById(home_gk)
        awayGK = findViewById(away_gk)
        homeDefense = findViewById(home_defense)
        awayDefense = findViewById(away_defense)
        homeMidfield = findViewById(home_midfield)
        awayMidfield = findViewById(away_midfield)
        homeForward = findViewById(home_forward)
        awayForward = findViewById(away_forward)
        homeSubtitute = findViewById(home_subtitutes)
        awaySubtitute = findViewById(away_subtitutes)

        progressBar = findViewById(pb_detail)
        progressBar.gone()

        favoriteState()
        val request = ApiRepository()
        val gson = Gson()
        presenter = DetailPresenter(this, request, gson)
        presenter.getDetail(eventId, "lookupevent.php")

        swipeRefresh.onRefresh {
            presenter.getDetail(eventId, "lookupevent.php")
        }

    }

    override fun showLoading() {
        progressBar.visible()
    }


    override fun showDetail(data: List<Detail>) {
        events = Event(data[0].eventId,
                        data[0].homeTeam,
                        data[0].awayTeam,
                        data[0].dateEvent,
                        data[0].homeScore,
                        data[0].awayScore
                        )

        var id1 = ""
        var id2 = ""

        swipeRefresh.isRefreshing = false

       data.forEach{

           val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
           val newDate: Date = sdf.parse(it.dateEvent)
           val spf = SimpleDateFormat("EEEE, dd MMM yyyy")
           val date = spf.format(newDate)

           eventDate.setText(date)
           homeTeam.setText(it.homeTeam)
           awayTeam.setText(it.awayTeam)
           homeScore.setText(it.homeScore)
           awayScore.setText(it.awayScore)

           homeGD.setText(it.homeGDetails)
           awayGD.setText(it.awayGDetails)
           homeShots.setText(it.homeShots)
           awayShots.setText(it.awayShots)

           homeGK.setText(it.homeLGK)
           awayGK.setText(it.awayLGK)
           homeDefense.setText(it.homeDefense)
           awayDefense.setText(it.awayDefense)
           homeMidfield.setText(it.homeMidfield)
           awayMidfield.setText(it.awayMidfield)
           homeForward.setText(it.homeForward)
           awayForward.setText(it.awayForward)
           homeSubtitute.setText(it.homeSubtitute)
           awaySubtitute.setText(it.awaySubtitute)

           id1= it.idHomeTeam.toString()
           id2 = it.idAwayTeam.toString()
       }
        val request = ApiRepository()
        val gson = Gson()
        doAsync {
            val badgeHome = gson.fromJson(request
                    .doRequest(TheSportDBApi.getData(id1, "lookupteam.php")),
                    TeamResponse::class.java
            )
            val badgeAway = gson.fromJson(request
                    .doRequest(TheSportDBApi.getData(id2, "lookupteam.php")),
                    TeamResponse::class.java
            )

            uiThread {
                badgeHome.teams.forEach {
                    Picasso.get().load(it.teamBadge).into(homeLogo)
                }

                badgeAway.teams.forEach {
                    Picasso.get().load(it.teamBadge).into(awayLogo)
                }
            }
        }
    }

    override fun hideLoading() {
        progressBar.gone()
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(detail_menu, menu)
        menuItem = menu
        setFavorite()
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            android.R.id.home -> {
                finish()
                true
            }
            add_to_favorite -> {
                if(isFavorite) removeFromFavorite() else addToFavorite()

                isFavorite = !isFavorite
                setFavorite()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }


    private fun favoriteState(){
        database.use {
            val result = select(FavoriteEvent.TABLE_FAVORITE)
                    .whereArgs("(${FavoriteEvent.EVENT_ID} = {id})",
                            "id" to eventId)
            val favorite = result.parseList(classParser<FavoriteEvent>())
            if (!favorite.isEmpty()) isFavorite = true
        }
    }

    private fun addToFavorite(){
        try {
            database.use {
                insert(FavoriteEvent.TABLE_FAVORITE,
                        FavoriteEvent.EVENT_ID to events.eventId,
                        FavoriteEvent.HOME_TEAM to events.homeTeam,
                        FavoriteEvent.AWAY_TEAM to events.awayTeam,
                        FavoriteEvent.EVENT_DATE to events.dateEvent,
                        FavoriteEvent.HOME_SCORE to events.homeScore,
                        FavoriteEvent.AWAY_SCORE to events.awayScore)
            }
            snackbar(swipeRefresh, "Added to favorite").show()
        } catch (e: SQLiteConstraintException) {
            snackbar(swipeRefresh, e.localizedMessage).show()
        }
    }

    private fun removeFromFavorite() {
        try {
            database.use {
                delete(FavoriteEvent.TABLE_FAVORITE, "(${FavoriteEvent.EVENT_ID}= {id})",
                        "id" to eventId)
            }
            snackbar(swipeRefresh, "Removed from favorite").show()
        } catch (e: SQLiteConstraintException) {
            snackbar(swipeRefresh, e.localizedMessage).show()
        }
    }

    private fun setFavorite() {
        if(isFavorite)
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, ic_added_to_favorites)
        else
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, ic_add_to_favorites)
    }

}
