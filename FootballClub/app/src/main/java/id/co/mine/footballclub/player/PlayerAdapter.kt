package id.co.mine.footballclub.player

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
import id.co.mine.footballclub.model.Player

import id.co.mine.footballclub.model.Team

import org.jetbrains.anko.*
import org.w3c.dom.Text
import java.text.SimpleDateFormat
import java.util.*

class PlayerAdapter(private val players: List<Player>, private val listener: (Player) -> Unit)
    : RecyclerView.Adapter<PlayerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerViewHolder {
        return PlayerViewHolder(PlayerUI().createView(AnkoContext.create(parent.context, parent)))
    }

    override fun onBindViewHolder(holder: PlayerViewHolder, position: Int) {
        holder.bindItem(players[position], listener)
    }
    override fun getItemCount(): Int = players.size

}

class PlayerUI : AnkoComponent<ViewGroup> {

    override fun createView(ui: AnkoContext<ViewGroup>): View {
        return with(ui) {
            linearLayout{
                    lparams(width = matchParent, height = wrapContent)
                    orientation = LinearLayout.HORIZONTAL
                    padding = dip(10)
                    setVerticalGravity(Gravity.CENTER_VERTICAL)
                imageView{
                    id = player_cutout
                }.lparams(width = dip(50), height = dip(50))

                linearLayout{
                    lparams(width= matchParent, height = matchParent)
                    orientation = LinearLayout.VERTICAL
                    setVerticalGravity(Gravity.CENTER_VERTICAL)

                    textView {
                        id = player_name
                        textSize= 14f

                    }.lparams{
                        leftMargin = dip(10)
                    }

                    textView {
                        id = player_position
                        textSize= 12f

                    }.lparams{
                        leftMargin = dip(10)
                    }
                }

            }
        }
    }
}

class PlayerViewHolder(view: View) : RecyclerView.ViewHolder(view){


    private val playerPhoto: ImageView = view.find(player_cutout)
    private val playerName: TextView = view.find(player_name)
    private val playerPos: TextView = view.find(player_position)

    fun bindItem(players: Player, listener: (Player) -> Unit) {
        Picasso.get().load(players.playerPhoto).into(playerPhoto)
        playerName.text = players.playerName
        playerPos.text = players.playerPosition
        itemView.setOnClickListener {
            listener(players)
        }

    }

}
