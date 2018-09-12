package com.walukustudio.kotlin

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import com.walukustudio.kotlin.model.Schedule
import org.jetbrains.anko.*

class MatchActivity : AppCompatActivity() {
    private lateinit var ivClubImage: ImageView
    private lateinit var tvClubName: TextView
    private lateinit var tvClubDesc: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        verticalLayout {
            lparams(matchParent, wrapContent)
            padding = dip(16)

            tvClubName = textView("Barcelona"){
                textSize = 18f
            }.lparams(wrapContent, wrapContent){
                margin = dip(10)
            }


        }

        val intent = intent

        val schedule: Schedule = intent.getParcelableExtra("schedule")

        tvClubName.text = schedule.homeTeam

        setupToolbar()
    }

    private fun setupToolbar(){
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Match Detail"
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return if (item?.itemId == android.R.id.home) {
            finish()
            true
        } else {
            super.onOptionsItemSelected(item)
        }
    }
}