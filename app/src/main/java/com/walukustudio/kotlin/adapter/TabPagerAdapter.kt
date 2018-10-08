package com.walukustudio.kotlin.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

class TabPagerAdapter (
        fm: FragmentManager,
        val fragment1:Fragment,
        val fragment2:Fragment,
        val title1:String,
        val title2:String) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                fragment1
            }
            else -> {
                fragment2
            }
        }
    }

    override fun getCount(): Int {
        return 2
    }

    override fun getPageTitle(position: Int): CharSequence {
        return when (position) {
            0 -> title1
            else -> {
                title2
            }
        }
    }
}