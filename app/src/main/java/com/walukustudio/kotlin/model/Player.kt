package com.walukustudio.kotlin.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Player (
        @SerializedName("idPlayer")
        var playerId: String? = null,

        @SerializedName("strPlayer")
        var playerName: String? = null,

        @SerializedName("strWeight")
        var playerWeight: String? = null,

        @SerializedName("strHeight")
        var playerHeight: String? = null,

        @SerializedName("strPosition")
        var playerPosition: String? = null,

        @SerializedName("strSport")
        var playerSport: String? = null,

        @SerializedName("strDescriptionEN")
        var playerDescription: String? = null,

        @SerializedName("strCutout")
        var playerPic: String? = null,

        @SerializedName("strThumb")
        var playerThumb: String? = null
) : Parcelable