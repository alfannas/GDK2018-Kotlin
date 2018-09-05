package com.walukustudio.kotlin.ui

import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import org.jetbrains.anko.*

class ClubItemUI : AnkoComponent<ViewGroup>{

    companion object {
        const val tvClubName = 1
        const val ivClubImage = 2
    }

    override fun createView(ui: AnkoContext<ViewGroup>): View = with(ui){
        linearLayout{
            padding = dip(16)
            lparams(matchParent, wrapContent)
            imageView{
                id = ivClubImage
            }.lparams(dip(50),dip(50))
            textView{
                id = tvClubName
                text = "Barcelona"
            }.lparams(wrapContent,wrapContent){
                verticalGravity = Gravity.CENTER_VERTICAL
                margin = dip(10)
            }
        }
    }

}