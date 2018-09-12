package com.walukustudio.kotlin.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Schedule (
        @SerializedName("idEvent")
        var idEvent: String? = null,

        @SerializedName("idHomeTeam")
        var idHomeTeam: String? = null,
        @SerializedName("idAwayTeam")
        var idAwayTeam: String? = null,

        @SerializedName("strDate")
        var dateEvent: String? = null,

        @SerializedName("strHomeTeam")
        var homeTeam: String? = null,
        @SerializedName("strAwayTeam")
        var awayTeam: String? = null,

        @SerializedName("intHomeScore")
        var homeScore: String? = null,
        @SerializedName("intAwayScore")
        var awayScore: String? = null,

        @SerializedName("strHomeGoalDetails")
        var homeGoalDetails: String? = null,
        @SerializedName("strAwayGoalDetails")
        var awayGoalDetails: String? = null,

        @SerializedName("intHomeShots")
        var homeShots: String? = null,
        @SerializedName("intAwayShots")
        var awayShots: String? = null,

        @SerializedName("strHomeLineupGoalkeeper")
        var homeKeeper: String? = null,
        @SerializedName("strAwayLineupGoalkeeper")
        var awayKeeper: String? = null,

        @SerializedName("strHomeLineupDefense")
        var homeDefense: String? = null,
        @SerializedName("strAwayLineupDefense")
        var awayDefense: String? = null,

        @SerializedName("strHomeLineupMidfield")
        var homeMidfield: String? = null,
        @SerializedName("strAwayLineupMidfield")
        var awayMidfield: String? = null,

        @SerializedName("strHomeLineupForward")
        var homeForward: String? = null,
        @SerializedName("strAwayLineupForward")
        var awayForward: String? = null,

        @SerializedName("strHomeLineupSubstitutes")
        var homeSubstitutes: String? = null,
        @SerializedName("strAwayLineupSubstitutes")
        var awaySubstitutes: String? = null


) : Parcelable