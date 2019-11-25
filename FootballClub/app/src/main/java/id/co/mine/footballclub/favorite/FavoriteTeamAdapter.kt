package id.co.mine.footballclub.favorite


import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout

import android.widget.TextView
import com.squareup.picasso.Picasso
import id.co.mine.footballclub.R.id.*
import id.co.mine.footballclub.db.FavoriteTeam


import id.co.mine.footballclub.model.Team

import org.jetbrains.anko.*
import java.text.SimpleDateFormat
import java.util.*

class FavoriteTeamAdapter(private val teams: List<FavoriteTeam>, private val listener: (FavoriteTeam) -> Unit)
    : RecyclerView.Adapter<FavoriteTeamViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteTeamViewHolder {
        return FavoriteTeamViewHolder(TeamUI().createView(AnkoContext.create(parent.context, parent)))
    }

    override fun onBindViewHolder(holder: FavoriteTeamViewHolder, position: Int) {
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

class FavoriteTeamViewHolder(view: View) : RecyclerView.ViewHolder(view){


    private val teamBadge: ImageView = view.find(team_badge)
    private val teamName: TextView = view.find(team_name)

    fun bindItem(teams: FavoriteTeam, listener: (FavoriteTeam) -> Unit) {
        Picasso.get().load(teams.teamBadge).into(teamBadge)
        teamName.text = teams.teamName
        itemView.setOnClickListener {
            listener(teams)
        }
    }

}
