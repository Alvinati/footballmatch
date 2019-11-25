package id.co.mine.footballclub.teams


import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter


class ViewPagerAdapterTeam (fm: FragmentManager): FragmentPagerAdapter (fm) {

    private val pages = listOf(
            OverviewFragment(),
            PlayersFragment()
    )

    override fun getItem(position: Int): Fragment? {
        return pages[position]
    }

    override fun getCount(): Int {
        return 2
    }

    override fun getPageTitle(position: Int): CharSequence? {

        return when(position) {
            0 -> "Overview"
            else -> "Players"
        }
    }

}