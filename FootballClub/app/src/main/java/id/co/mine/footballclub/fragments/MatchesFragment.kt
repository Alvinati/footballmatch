package id.co.mine.footballclub.fragments


import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import id.co.mine.footballclub.R
import id.co.mine.footballclub.main.ViewPagerAdapter

/**
 * A simple [Fragment] subclass.
 */
class MatchesFragment : Fragment() {

    private lateinit var pagerAdapter: ViewPagerAdapter
    private lateinit var viewPager: ViewPager
    private lateinit var tabLayout: TabLayout


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_matches, container, false)


        pagerAdapter = ViewPagerAdapter(childFragmentManager)
        viewPager = v.findViewById(R.id.matches_viewPager)
        tabLayout = v.findViewById(R.id.matches_tablayout)

        viewPager.adapter = pagerAdapter
        tabLayout.setupWithViewPager(viewPager)

        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }


}// Required empty public constructor
