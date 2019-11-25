package id.co.mine.footballclub.favorite

import android.graphics.Typeface
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import id.co.mine.footballclub.R.id.event_date
import id.co.mine.footballclub.R.id.home_team
import id.co.mine.footballclub.R.id.home_score
import id.co.mine.footballclub.R.id.away_team
import id.co.mine.footballclub.R.id.away_score
import id.co.mine.footballclub.R.color.colorPrimaryDark
import id.co.mine.footballclub.R.string.vs
import id.co.mine.footballclub.db.FavoriteEvent


import org.jetbrains.anko.*
import java.text.SimpleDateFormat
import java.util.*

class FavoritesEventAdapter(private val events: List<FavoriteEvent>, private val listener: (FavoriteEvent) -> Unit)
    : RecyclerView.Adapter<FavoritesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritesViewHolder {
        return FavoritesViewHolder(EventUI().createView(AnkoContext.create(parent.context, parent)))
    }

    override fun onBindViewHolder(holder: FavoritesViewHolder, position: Int) {
        holder.bindItem(events[position], listener)
    }
    override fun getItemCount(): Int = events.size
}

class EventUI : AnkoComponent<ViewGroup> {
    override fun createView(ui: AnkoContext<ViewGroup>): View {
        return with(ui) {
             linearLayout {
                 lparams(width= matchParent, height = wrapContent)
                orientation = LinearLayout.VERTICAL
                padding = dip(5)


                textView {
                    id = event_date
                    textSize = 12f
                    textColorResource = colorPrimaryDark
                    textAlignment = TextView.TEXT_ALIGNMENT_CENTER
                    padding = dip(4)
                }

                linearLayout {
                    lparams(width= matchParent, height = wrapContent)
                    orientation = LinearLayout.HORIZONTAL

                    textView {
                        id = home_team
                        textSize = 16f
                        textAlignment = TextView.TEXT_ALIGNMENT_TEXT_END
                    }.lparams {
                        leftMargin = dip(5)
                        rightMargin = dip(10)
                        width = dip(0)
                        weight = 2.0f
                    }

                    linearLayout {
                        orientation = LinearLayout.HORIZONTAL
                        textAlignment = LinearLayout.TEXT_ALIGNMENT_CENTER
                        textView {
                            id = home_score
                            textSize = 16f
                            typeface = Typeface.DEFAULT_BOLD
                        }
                        textView {
                            textSize = 14f
                            text = context.getString(vs)
                        }.lparams {
                            leftMargin = dip(5)
                            rightMargin = dip(5)
                        }
                        textView {
                            id = away_score
                            textSize = 16f
                            typeface = Typeface.DEFAULT_BOLD
                        }

                    }.lparams {
                        width = dip(0)
                        weight = 1.0f
                    }

                    textView {
                        id = away_team
                        textSize = 16f
                        textAlignment = TextView.TEXT_ALIGNMENT_TEXT_START
                    }.lparams {
                        leftMargin = dip(5)
                        rightMargin = dip(5)
                        width = dip(0)
                        weight = 2.0f
                        bottomMargin = dip(5)
                    }

                }
            }
        }
    }
}

class FavoritesViewHolder(view: View) : RecyclerView.ViewHolder(view){

    private val eventDate: TextView = view.find(event_date)
    private val homeTeam: TextView = view.find(home_team)
    private val homeScore: TextView = view.find(home_score)
    private val awayTeam: TextView = view.find(away_team)
    private val awayScore: TextView = view.find(away_score)



    fun bindItem(events: FavoriteEvent, listener: (FavoriteEvent) -> Unit) {
        homeTeam.text = events.homeTeam
        homeScore.text = events.homeScore
        if (events.homeScore == null){
            homeScore.text = "   "
        }

        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val newDate: Date = sdf.parse(events.eventDate)
        val spf = SimpleDateFormat("EEEE, dd MMM yyyy")
        val date = spf.format(newDate)
        eventDate.text = date

        awayTeam.text = events.awayTeam
        awayScore.text = events.awayScore
        itemView.setOnClickListener {
            listener(events)
        }
    }

}
