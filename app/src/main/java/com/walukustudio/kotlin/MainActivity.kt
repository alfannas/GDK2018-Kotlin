package com.walukustudio.kotlin

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.test.espresso.idling.CountingIdlingResource
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
                replaceFragment(FragmentFav.newInstance(),R.id.container)
            }
        }
        true
    }


}
