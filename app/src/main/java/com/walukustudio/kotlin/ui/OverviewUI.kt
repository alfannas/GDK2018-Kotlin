package com.walukustudio.kotlin.ui

import android.view.View
import android.widget.LinearLayout
import com.walukustudio.kotlin.R
import org.jetbrains.anko.*

class OverviewUI<T> : AnkoComponent<T> {
    override fun createView(ui: AnkoContext<T>): View {
        return with(ui) {
            linearLayout {
                lparams(width = matchParent, height = wrapContent)
                orientation = LinearLayout.VERTICAL
                topPadding = dip(16)
                leftPadding = dip(16)
                rightPadding = dip(16)

                scrollView {
                    lparams(width = matchParent, height = wrapContent)

                    textView{
                        id = R.id.overview
                    }.lparams(width = matchParent, height = wrapContent)
                }

            }
        }
    }
}