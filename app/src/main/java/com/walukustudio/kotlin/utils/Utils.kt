package com.walukustudio.kotlin.utils

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import android.view.View
import java.text.SimpleDateFormat
import java.util.*


fun View.visible() {
    visibility = View.VISIBLE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun View.gone(){
    visibility = View.GONE
}

inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> FragmentTransaction) {
    beginTransaction().func().commit()
}

fun AppCompatActivity.addFragment(fragment: Fragment, frameId: Int){
    supportFragmentManager.inTransaction { add(frameId, fragment) }
}
fun AppCompatActivity.replaceFragment(fragment: Fragment, frameId: Int) {
    supportFragmentManager.inTransaction{replace(frameId, fragment)}
}

fun dateConvert(date:String?):String {

    val result = if (date != null) {
        val locale = Locale("id")

        val input = SimpleDateFormat("dd/MM/yy", Locale.US)
        val output = SimpleDateFormat("E, dd MMM yyyy", locale)

        val indate: Date = input.parse(date)
        output.format(indate)
    }else{
        "undefined"
    }
    return result
}




