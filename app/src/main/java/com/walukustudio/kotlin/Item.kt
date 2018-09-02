package com.walukustudio.kotlin

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by alfannas on 1/9/2018.
 */
@Parcelize
data class Item (val name: String?, val image: Int?, val description: String?) : Parcelable