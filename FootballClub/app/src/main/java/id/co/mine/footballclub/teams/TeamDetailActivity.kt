package id.co.mine.footballclub.teams

import android.content.Context
import android.database.sqlite.SQLiteConstraintException
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.design.widget.CoordinatorLayout
import android.support.design.widget.TabLayout
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewPager

import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import id.co.mine.footballclub.R

import id.co.mine.footballclub.db.FavoriteTeam
import id.co.mine.footballclub.db.database

import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import org.jetbrains.anko.design.snackbar


class TeamDetailActivity : AppCompatActivity() {


    private lateinit var pagerAdapter: ViewPagerAdapterTeam
    private lateinit var viewPager: ViewPager
    private lateinit var tabLayout: TabLayout
    private lateinit var badgeImage: ImageView
    private lateinit var teamformedYear: TextView
    private lateinit var teamStadium: TextView

    private var menuItem : Menu? = null
    private var isFavorite: Boolean = false
    private lateinit var layout: CoordinatorLayout

    private lateinit var teamId: String
    private lateinit var teamName: String
    private lateinit var teamLogo: String
    private lateinit var formedYear: String
    private lateinit var stadium: String
    private lateinit var description: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_team_detail)

        supportActionBar?.setTitle("")
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val sharedPreferences = getSharedPreferences("preferensiKu", Context.MODE_PRIVATE)

        pagerAdapter = ViewPagerAdapterTeam(supportFragmentManager)
        viewPager = findViewById(R.id.detail_team_viewpager)
        tabLayout = findViewById(R.id.detail_team_tablayout)

        viewPager.adapter = pagerAdapter
        tabLayout.setupWithViewPager(viewPager)


        val mAppBarLayout = findViewById(R.id.appbar) as AppBarLayout
        mAppBarLayout.addOnOffsetChangedListener(object : AppBarLayout.OnOffsetChangedListener {
           var isShow = false
            var scrollRange = -1

            override fun onOffsetChanged(appBarLayout: AppBarLayout, verticalOffset: Int) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.totalScrollRange
                }
            }
        })

        layout = findViewById(R.id.team_detail_layout)

        teamId = intent.getStringExtra("teamid")
        teamName = intent.getStringExtra("nama")
        teamLogo = intent.getStringExtra("teamBadge")
        formedYear = intent.getStringExtra("year")
        stadium = intent.getStringExtra("stadium")
        description = intent.getStringExtra("desc")

        favoriteState()

        val edit = sharedPreferences.edit()
        edit.putString("desc", description)
        edit.putString("namaTeam", teamName)
        edit.apply()

        badgeImage = findViewById(R.id.detail_team_badge)
        teamformedYear = findViewById(R.id.detail_team_year)
        teamStadium = findViewById(R.id.detail_team_stadium)

        Picasso.get().load(teamLogo).into(badgeImage)
        teamformedYear.text = formedYear
        teamStadium.text = stadium
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.detail_menu, menu)
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
            R.id.add_to_favorite -> {
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
            val result = select(FavoriteTeam.TABLE_FAVORITE_TEAM)
                    .whereArgs("(${FavoriteTeam.TEAM_ID} = {id})",
                            "id" to teamId)
            val favorite = result.parseList(classParser<FavoriteTeam>())
            if (!favorite.isEmpty()) isFavorite = true
        }
    }

    private fun addToFavorite(){
        try {
            database.use {
                insert(FavoriteTeam.TABLE_FAVORITE_TEAM,
                        FavoriteTeam.TEAM_ID to teamId,
                        FavoriteTeam.TEAM_NAME to teamName,
                        FavoriteTeam.TEAM_BADGE to teamLogo,
                        FavoriteTeam.FORMED_YEAR to formedYear,
                        FavoriteTeam.STADIUM to stadium,
                        FavoriteTeam.TEAM_DESC to description)
            }
            snackbar(layout, "Added to favorite").show()
        } catch (e: SQLiteConstraintException) {
            snackbar(layout, e.localizedMessage).show()
        }
    }

    private fun removeFromFavorite() {
        try {
            database.use {
                delete(FavoriteTeam.TABLE_FAVORITE_TEAM, "(${FavoriteTeam.TEAM_ID}= {id})",
                        "id" to teamId)
            }
            snackbar(layout, "Removed from favorite").show()
        } catch (e: SQLiteConstraintException) {
            snackbar(layout, e.localizedMessage).show()
        }
    }



    private fun setFavorite() {
        if(isFavorite)
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_added_to_favorites)
        else
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_add_to_favorites)
    }
}


