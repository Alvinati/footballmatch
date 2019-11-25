package id.co.mine.footballclub.player

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import com.google.gson.JsonDeserializationContext
import com.squareup.picasso.Picasso
import id.co.mine.footballclub.R
import id.co.mine.footballclub.model.Player
import org.w3c.dom.Text
import android.content.Intent
import android.view.MenuItem


class PlayerDetailActivity : AppCompatActivity() {

    private lateinit var thumbnailPlayer: ImageView
    private lateinit var weightPlayer: TextView
    private lateinit var heightPlayer: TextView
    private lateinit var positionPlayer: TextView
    private lateinit var detailPlayer:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player_detail)

        val name = intent.getStringExtra("name")
        supportActionBar?.title = name
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val thumbnail = intent.getStringExtra("thumbnail")
        val weight = intent.getStringExtra("weight")
        val height = intent.getStringExtra("height")
        val position = intent.getStringExtra("position")
        val descript = intent.getStringExtra("desc")

        thumbnailPlayer = findViewById(R.id.player_thumb)
        weightPlayer = findViewById(R.id.detail_weight)
        heightPlayer = findViewById(R.id.detail_height)
        positionPlayer = findViewById(R.id.detail_position)
        detailPlayer = findViewById(R.id.detail_playerdetail)

        Picasso.get().load(thumbnail).into(thumbnailPlayer)
        weightPlayer.text = weight
        heightPlayer.text = height
        positionPlayer.text = position
        detailPlayer.text = descript

    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        super.onOptionsItemSelected(item)
        when (item!!.getItemId()) {
            android.R.id.home -> {
               finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
