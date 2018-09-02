package com.walukustudio.kotlin

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import org.jetbrains.anko.*

class ClubDetailActivity : AppCompatActivity() {

    lateinit var ivClubImage: ImageView
    lateinit var tvClubName: TextView
    lateinit var tvClubDesc: TextView

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        verticalLayout {
            lparams(matchParent, wrapContent)
            padding = dip(16)
            ivClubImage = imageView().lparams(dip(100),dip(100)){
                gravity = Gravity.CENTER_HORIZONTAL
                margin = dip(10)
            }
            tvClubName = textView("Barcelona"){
                textSize = 18f
            }.lparams(wrapContent, wrapContent){
                gravity = Gravity.CENTER_HORIZONTAL
                margin = dip(10)
            }
            tvClubDesc = textView("Barcelona Description").lparams(matchParent, wrapContent){
                gravity = Gravity.CENTER_HORIZONTAL
                margin = dip(10)
            }
        }

        val intent = intent

        val item: Item = intent.getParcelableExtra("item")

        Glide.with(this).load(item.image).into(ivClubImage)
        tvClubName.text = item.name
        tvClubDesc.text = item.description

    }
}