package com.walukustudio.kotlin.utils

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import org.jetbrains.anko.db.*

class DBHelper(ctx: Context) : ManagedSQLiteOpenHelper(ctx, "FavoriteTeam.db",null,1) {
    companion object {
        private var instance: DBHelper? = null

        @Synchronized
        fun getInstance(ctx: Context): DBHelper {
            if (instance == null) {
                instance = DBHelper(ctx.applicationContext)
            }
            return instance!!
        }
    }

    override fun onCreate(db: SQLiteDatabase) {
        // Cretae Table
        db.createTable("TABLE_FAVORITE",true,
                "ID_" to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
                "MATCH_ID" to TEXT,
                "MATCH_TYPE" to TEXT,
                "MATCH_DATE" to TEXT,
                "TEAM_HOME_ID" to TEXT,
                "TEAM_AWAY_ID" to TEXT,
                "TEAM_HOME_NAME" to TEXT,
                "TEAM_AWAY_NAME" to TEXT,
                "TEAM_HOME_SCORE" to TEXT,
                "TEAM_AWAY_SCORE" to TEXT)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.dropTable("TABLE_FAVORITE",true)
    }
}

// Access property for Context
val Context.database: DBHelper
get() = DBHelper.getInstance(applicationContext)