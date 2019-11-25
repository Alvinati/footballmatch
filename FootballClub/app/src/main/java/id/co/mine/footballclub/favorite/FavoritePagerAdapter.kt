package id.co.mine.footballclub.favorite


import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import id.co.mine.footballclub.main.LastMatchFragment
import id.co.mine.footballclub.main.NextMatchFragment
import id.co.mine.footballclub.teams.OverviewFragment
import id.co.mine.footballclub.teams.PlayersFragment


class FavoritePagerAdapter (fm: FragmentManager): FragmentPagerAdapter (fm) {


    private val pages = listOf<Fragment>(
            FavoriteMatchFragment(),
            FavoriteTeamFragment()
    )

    override fun getItem(position: Int): Fragment? {
       return pages[position]
    }

    override fun getCount(): Int {
        return 2
    }

    override fun getPageTitle(position: Int): CharSequence? {

        return when(position) {
            0 -> "Match"
            else -> "Team"
        }
    }

}