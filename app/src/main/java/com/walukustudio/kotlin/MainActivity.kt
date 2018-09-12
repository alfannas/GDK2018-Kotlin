package com.walukustudio.kotlin

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*
import android.os.StrictMode
import com.walukustudio.kotlin.utils.addFragment
import com.walukustudio.kotlin.utils.replaceFragment


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // entah kenapa butuh ini, fix error null saat request api schedule
        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

        // initiate fragment
        addFragment(FragmentPrev.newInstance(),R.id.container)

        //val bottomNavigation: BottomNavigationView = findViewById(R.id.navigationView);
        navigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId){
            R.id.nav_prev -> {
                replaceFragment(FragmentPrev.newInstance(),R.id.container)
            }
            R.id.nav_next -> {
                replaceFragment(FragmentNext.newInstance(),R.id.container)
            }
        }
        false
    }


}
