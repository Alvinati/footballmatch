package id.co.mine.footballclub.teams

import android.graphics.Color
import android.graphics.Typeface
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout

import android.widget.TextView
import com.squareup.picasso.Picasso
import id.co.mine.footballclub.R.color.colorPrimaryDark
import id.co.mine.footballclub.R.id.*
import id.co.mine.footballclub.R.string.vs

import id.co.mine.footballclub.model.Team

import org.jetbrains.anko.*
import java.text.SimpleDateFormat
import java.util.*

class TeamAdapter(private val teams: List<Team>, private val listener: (Team) -> Unit)
    : RecyclerView.Adapter<TeamViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamViewHolder {
        return TeamViewHolder(TeamUI().createView(AnkoContext.create(parent.context, parent)))
    }

    override fun onBindViewHolder(holder: TeamViewHolder, position: Int) {
        holder.bindItem(teams[position], listener)
    }

    override fun getItemCount(): Int = teams.size

}

class TeamUI : AnkoComponent<ViewGroup> {

    override fun createView(ui: AnkoContext<ViewGroup>): View {
        return with(ui) {
            linearLayout{
                    lparams(width = matchParent, height = wrapContent)
                    orientation = LinearLayout.HORIZONTAL
                    padding = dip(10)
                    setVerticalGravity(Gravity.CENTER_VERTICAL)
                imageView{
                    id = team_badge
                }.lparams(width = dip(50), height = dip(50))

                textView {
                    id = team_name
                    textSize= 14f

                }.lparams{
                    leftMargin = dip(10)
                }
            }
        }
    }
}

class TeamViewHolder(view: View) : RecyclerView.ViewHolder(view){


    private val teamBadge: ImageView = view.find(team_badge)
    private val teamName: TextView = view.find(team_name)

    fun bindItem(teams: Team, listener: (Team) -> Unit) {
        Picasso.get().load(teams.teamBadge).into(teamBadge)
        teamName.text = teams.teamName
        itemView.setOnClickListener {
            listener(teams)
        }
    }

}
