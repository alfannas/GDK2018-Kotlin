package com.walukustudio.kotlin.activities.teams

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.ImageView
import com.squareup.picasso.Picasso
import com.walukustudio.kotlin.R
import com.walukustudio.kotlin.model.Player
import kotlinx.android.synthetic.main.activity_player.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class PlayerDetailActivity:AppCompatActivity() {
    private lateinit var player:Player

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)

        val intent = intent
        player = intent.getParcelableExtra("player")

        setupToolbar()
        setupContent()
    }

    private fun setupToolbar(){

        // Set the toolbar as support action bar
        setSupportActionBar(toolbar)

        // Now get the support action bar
        val actionBar = supportActionBar

        // Set toolbar title/app title
        actionBar?.title = player.playerName

        // Display the app icon in action bar/toolbar
        actionBar?.setDisplayShowHomeEnabled(true)
        actionBar?.setDisplayHomeAsUpEnabled(true)

        toolbar.setNavigationOnClickListener{
            finish()
        }
    }

    private fun setupContent(){
        loadPicture(player.playerThumb,player_pic)
        player_weight.text = player.playerWeight
        player_height.text = player.playerHeight
        player_position.text = player.playerPosition
        player_description.text = player.playerDescription
    }

    private fun loadPicture(pictureUrl: String?,iv: ImageView){
        doAsync {

            uiThread {
                Picasso.get().load(pictureUrl).into(iv)

            }
        }
    }

}

