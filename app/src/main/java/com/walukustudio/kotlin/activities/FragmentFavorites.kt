package com.walukustudio.kotlin.activities

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.walukustudio.kotlin.R
import com.walukustudio.kotlin.activities.favorites.FragmentMatches
import com.walukustudio.kotlin.activities.favorites.FragmentTeams
import com.walukustudio.kotlin.adapter.TabPagerAdapter
import kotlinx.android.synthetic.main.fragment_fav_match.view.*

class FragmentFavorites : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        //return View.inflate(context, R.layout.fragment_matches,container)

        val view = inflater.inflate(R.layout.fragment_fav_match,container,false)

        val fragmentAdapter = TabPagerAdapter(childFragmentManager, FragmentMatches(), FragmentTeams(),"Match","Team")
        view.viewpager_main.adapter = fragmentAdapter

        view.tabs_main.setupWithViewPager(view.viewpager_main)

        setupToolbar(view)

        return view
    }

    fun setupToolbar(view: View){
        (activity as AppCompatActivity).setSupportActionBar(view.toolbar)
        val actionBar = (activity as AppCompatActivity).supportActionBar
        actionBar?.title = "Football Apps"
    }

    companion object {
        fun newInstance(): FragmentFavorites = FragmentFavorites()
    }
}