package id.co.mine.footballclub.fragments

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


import id.co.mine.footballclub.R
import id.co.mine.footballclub.favorite.FavoritePagerAdapter




class FavoritesFragment: Fragment() {

    private lateinit var pagerAdapter: FavoritePagerAdapter
    private lateinit var viewPager: ViewPager
    private lateinit var tabLayout: TabLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_favorites, container, false)


        pagerAdapter = FavoritePagerAdapter(childFragmentManager)
        viewPager = v.findViewById(R.id.favorites_viewPager)
        tabLayout = v.findViewById(R.id.favorites_tablayout)

        viewPager.adapter = pagerAdapter
        tabLayout.setupWithViewPager(viewPager)

        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }


}