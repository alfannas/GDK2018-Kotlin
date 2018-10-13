package com.walukustudio.kotlin.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FavoriteTeam (
        val id: Long?,
        val teamId: String?,
        val teamName: String?,
        val teamEstablished: String?,
        val teamStadium: String?,
        val teamDescription: String?,
        val teamBadge: String?) : Parcelable {
    companion object {
        const val TABLE_FAVORITE_TEAM: String = "TABLE_FAVORITE_TEAM"
        const val ID:String = "ID_"
        const val TEAM_ID: String = "TEAM_ID"
        const val TEAM_NAME: String = "TEAM_NAME"
        const val TEAM_ESTABLISHED: String = "TEAM_ESTABLISHED"
        const val TEAM_STADIUM: String = "TEAM_STADIUM"
        const val TEAM_DESCRIPTION: String = "TEAM_DESCRIPTION"
        const val TEAM_BADGE: String = "TEAM_BADGE"
    }
}