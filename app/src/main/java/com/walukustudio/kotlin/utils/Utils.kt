package com.walukustudio.kotlin.utils

import android.annotation.SuppressLint
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import android.view.View
import java.text.ParseException
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

@SuppressLint("SimpleDateFormat")
fun dateConvert(date:String?):String {

    val result = if (date != null) {

        val input = SimpleDateFormat("yyyy-MM-dd")
        val output = SimpleDateFormat("E, dd MMM yyyy")

        val indate: Date = input.parse(date)
        output.format(indate)
    }else{
        "undefined"
    }

    return result
}

fun timeConvert(time:String):String{
    val t : List<String> = time.split(":")

    val hour: String = if(t[0].toInt() < 10) "0"+t[0] else t[0]
    val minute: String = if(t[1].toInt() < 10) t[1]+"0" else t[1]

    return "$hour:$minute"
}

fun getNormalizedTime(time:String?):String{
    val result:String

    if (time != null) {
        val t : List<String> = time.split(":")
        result = t[0]+":"+t[1]
    }else{
        result = "null"
    }
    return result
}

@SuppressLint("SimpleDateFormat")
fun stringToDate(date: String?,time:String): Date {
    val pattern:String = if(time != "null") "yyyy-MM-dd HH:mm" else "yyyy-MM-dd"

    val dateTime = "$date $time"

    val timeFormat = SimpleDateFormat(pattern)
    var myDate = Date()
    try {
        myDate = timeFormat.parse(dateTime)

    } catch (e: ParseException) {
        e.printStackTrace()
    }

    return myDate
}

fun getDateTime(date:Date):List<String>{
    val datetime:MutableList<String> = mutableListOf()
    val calendar = Calendar.getInstance()
    calendar.time = date
    datetime.add(calendar.get(Calendar.YEAR).toString()
            +"-"+(calendar.get(Calendar.MONTH)+1).toString()
            +"-"+calendar.get(Calendar.DAY_OF_MONTH).toString())
    datetime.add(calendar.get(Calendar.HOUR_OF_DAY).toString()
            +":"+calendar.get(Calendar.MINUTE).toString())

    return datetime
}

fun dateFromUTC(date: Date): Date {
    return Date(date.time + Calendar.getInstance().timeZone.getOffset(date.time))
}

fun dateToUTC(date: Date): Date {
    return Date(date.time - Calendar.getInstance().timeZone.getOffset(date.time))
}




