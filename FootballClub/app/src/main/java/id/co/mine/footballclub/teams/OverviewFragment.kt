package id.co.mine.footballclub.teams

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout


import id.co.mine.footballclub.R.id.team_desc
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.UI
import android.R.attr.name
import org.jetbrains.anko.support.v4.ctx


class OverviewFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val sharedPreferences = this.activity!!.getSharedPreferences("preferensiKu", Context.MODE_PRIVATE)
        val desc = sharedPreferences.getString("desc", "")

        return UI {
            frameLayout {
                lparams(width = matchParent, height = wrapContent)
                topPadding = dip(16)
                leftPadding = dip(16)
                rightPadding = dip(16)

                textView {
                    id = team_desc
                    text = desc
                }
                }
        }.view

    }


}
