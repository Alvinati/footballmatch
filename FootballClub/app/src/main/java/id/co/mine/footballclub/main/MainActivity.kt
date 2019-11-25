package id.co.mine.footballclub.main

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import id.co.mine.footballclub.R
import id.co.mine.footballclub.R.layout.activity_main
import kotlinx.android.synthetic.main.activity_main.*
import id.co.mine.footballclub.R.id.favorites
import id.co.mine.footballclub.R.id.teams
import id.co.mine.footballclub.R.id.matches
import id.co.mine.footballclub.fragments.*


class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activity_main)

        bottom_navigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                matches -> {
                    loadFragment(savedInstanceState, MatchesFragment(),
                            MatchesFragment::class.java.simpleName)
                }
                teams -> {
                    loadFragment(savedInstanceState, TeamsFragment(),
                            TeamsFragment::class.java.simpleName)
                }
                favorites -> {
                    loadFragment(savedInstanceState, FavoritesFragment(),
                            FavoritesFragment::class.java.simpleName)
                }
            }

            true
        }
        bottom_navigation.selectedItemId = matches

    }

    private fun loadFragment(savedInstanceState: Bundle?, fragment: Fragment, name: String) {
        if (savedInstanceState == null) {
            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.main_container, fragment, name)
                    .commit()
        }
    }
}