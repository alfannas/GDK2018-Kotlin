package com.walukustudio.kotlin.activities.teams

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.walukustudio.kotlin.R
import com.walukustudio.kotlin.ui.OverviewUI
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.find
import org.jetbrains.anko.support.v4.ctx

class FragmentOverview: Fragment() {
    private lateinit var overview:TextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = OverviewUI<Fragment>().createView(AnkoContext.create(ctx, this))

        val strOverview = this.arguments?.getString("overview")

        overview = view.find(R.id.overview)
        overview.text = strOverview

        return view
    }
}