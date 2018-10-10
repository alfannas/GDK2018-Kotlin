package com.walukustudio.kotlin.model

import com.google.gson.annotations.SerializedName

data class League (
        @SerializedName("idLeague")
        var leagueId: Long? = null,

        @SerializedName("strLeague")
        var leagueName: String? = null,

        @SerializedName("strSport")
        var leagueSport: String? = null
){
}