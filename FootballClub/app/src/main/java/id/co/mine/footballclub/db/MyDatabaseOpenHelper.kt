package id.co.mine.footballclub.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import org.jetbrains.anko.db.*

class MyDatabaseOpenHelper(ctx: Context) : ManagedSQLiteOpenHelper(ctx, "Favorite.db", null,2) {

    companion object {
        private var instance: MyDatabaseOpenHelper? = null

        @Synchronized
        fun getInstance(ctx: Context): MyDatabaseOpenHelper {
            if(instance == null){
                instance = MyDatabaseOpenHelper(ctx.applicationContext)
            }
            return instance as MyDatabaseOpenHelper
        }
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.createTable(FavoriteEvent.TABLE_FAVORITE, true,
                    FavoriteEvent.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
                    FavoriteEvent.EVENT_ID to TEXT + UNIQUE,
                    FavoriteEvent.HOME_TEAM to TEXT,
                    FavoriteEvent.AWAY_TEAM to TEXT,
                    FavoriteEvent.HOME_SCORE to TEXT,
                    FavoriteEvent.AWAY_SCORE to TEXT,
                    FavoriteEvent.EVENT_DATE to TEXT
                )

        db.createTable(FavoriteTeam.TABLE_FAVORITE_TEAM, true,
                FavoriteTeam.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
                FavoriteTeam.TEAM_ID to TEXT + UNIQUE,
                FavoriteTeam.TEAM_NAME to TEXT,
                FavoriteTeam.TEAM_BADGE to TEXT,
                FavoriteTeam.FORMED_YEAR to TEXT,
                FavoriteTeam.STADIUM to TEXT,
                FavoriteTeam.TEAM_DESC to TEXT
        )
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int)
    {
        db.dropTable(FavoriteEvent.TABLE_FAVORITE, true)
        db.dropTable(FavoriteTeam.TABLE_FAVORITE_TEAM, true)
    }
}
    val Context.database: MyDatabaseOpenHelper
    get() = MyDatabaseOpenHelper.getInstance(applicationContext)

