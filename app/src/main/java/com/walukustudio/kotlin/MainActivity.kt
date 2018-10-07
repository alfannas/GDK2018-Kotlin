package com.walukustudio.kotlin

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import com.walukustudio.kotlin.activities.FragmentFavorites
import com.walukustudio.kotlin.activities.matches.FragmentNext
import com.walukustudio.kotlin.activities.matches.FragmentPrev
import kotlinx.android.synthetic.main.activity_main.*
import com.walukustudio.kotlin.utils.replaceFragment


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //val bottomNavigation: BottomNavigationView = findViewById(R.id.navigationView);
        navigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        navigationView.selectedItemId = R.id.nav_prev
    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId){
            R.id.nav_prev -> {
                replaceFragment(FragmentPrev.newInstance(),R.id.container)
            }
            R.id.nav_next -> {
                replaceFragment(FragmentNext.newInstance(),R.id.container)
            }
            R.id.nav_fav -> {
                replaceFragment(FragmentFavorites.newInstance(),R.id.container)
            }
        }
        true
    }


}
