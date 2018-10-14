package com.walukustudio.kotlin.model

data class FavoriteMatch (
        val id: Long?,
        val eventId: String?,
        val eventType: String?,
        val eventDate: String?,
        val eventTime: String?,
        val teamHomeId: String?,
        val teamAwayId: String?,
        val teamHomeName: String?,
        val teamAwayName: String?,
        val teamHomeScore: String?,
        val teamAwayScore: String?){
    companion object {
        const val TABLE_FAVORITE_MATCH: String = "TABLE_FAVORITE_MATCH"
        const val ID:String = "ID_"
        const val MATCH_ID: String = "MATCH_ID"
        const val MATCH_TYPE: String = "MATCH_TYPE"
        const val MATCH_DATE: String = "MATCH_DATE"
        const val MATCH_TIME: String = "MATCH_TIME"
        const val TEAM_HOME_ID: String = "TEAM_HOME_ID"
        const val TEAM_AWAY_ID: String = "TEAM_AWAY_ID"
        const val TEAM_HOME_NAME: String = "TEAM_HOME_NAME"
        const val TEAM_AWAY_NAME: String = "TEAM_AWAY_NAME"
        const val TEAM_HOME_SCORE: String = "TEAM_HOME_SCORE"
        const val TEAM_AWAY_SCORE: String = "TEAM_AWAY_SCORE"
    }
}