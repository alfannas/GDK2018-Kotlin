package com.walukustudio.kotlin.model

import com.google.gson.annotations.SerializedName

data class TeamResponse (
        @SerializedName("teams")
        val teamsFootbal: List<Team>
)