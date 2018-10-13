package com.walukustudio.kotlin.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Team (
        @SerializedName("idTeam")
        var teamId: String? = null,

        @SerializedName("strTeam")
        var teamName: String? = null,

        @SerializedName("intFormedYear")
        var teamEstablished: String? = null,

        @SerializedName("strStadium")
        var teamStadium: String? = null,

        @SerializedName("strDescriptionEN")
        var teamDescription: String? = null,

        @SerializedName("strTeamBadge")
        var teamBadge: String? = null
) : Parcelable