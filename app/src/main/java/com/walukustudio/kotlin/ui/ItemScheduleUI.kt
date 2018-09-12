package com.walukustudio.kotlin.ui

import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.walukustudio.kotlin.R
import org.jetbrains.anko.*
import org.jetbrains.anko.cardview.v7.cardView

class ItemScheduleUI: AnkoComponent<ViewGroup> {
    override fun createView(ui: AnkoContext<ViewGroup>) : View {
        return with(ui) {
            cardView {
                //lparams(width = matchParent, height = wrapContent)
                layoutParams = FrameLayout.LayoutParams(matchParent, wrapContent).apply {
                    bottomMargin = dip(10)
                }
                padding = dip(16)

                verticalLayout {
                    lparams(width = matchParent,height = wrapContent)
                    padding = dip(10)


                    textView {
                        id = R.id.item_date
                        textColor = R.color.colorAccent
                        textSize = 14f
                    }.lparams {
                        width = wrapContent
                        height = wrapContent
                        horizontalGravity = Gravity.CENTER_HORIZONTAL
                    }

                    textView {
                        id = R.id.item_team
                        textSize = 18f
                    }.lparams{
                        width = wrapContent
                        height = wrapContent
                        gravity = Gravity.CENTER_HORIZONTAL
                    }
                }
            }

        }
    }

}