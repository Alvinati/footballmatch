package id.co.mine.footballclub.api

import android.net.Uri
import id.co.mine.footballclub.BuildConfig

object TheSportDBApi {

    fun getData(idParam: String?, path: String?): String {
//        return Uri.parse(BuildConfig.BASE_URL).buildUpon()
//                .appendPath("api")
//                .appendPath("v1")
//                .appendPath("json")
//                .appendPath(BuildConfig.TSDB_API_KEY)
//                .appendPath(path)
//                .appendQueryParameter("id", idParam)
//                .build()
//                .toString()

        //pakai Uri.parse di test gak mau

        return BuildConfig.BASE_URL + "api/v1/json/${BuildConfig.TSDB_API_KEY}/" + path+"?id="+ idParam

    }

    fun getTeam(league: String?): String {
        return Uri.parse(BuildConfig.BASE_URL).buildUpon()
                .appendPath("api")
                .appendPath("v1")
                .appendPath("json")
                .appendPath(BuildConfig.TSDB_API_KEY)
                .appendPath("search_all_teams.php")
                .appendQueryParameter("l", league)
                .build()
                .toString()
    }

    fun getPlayer(team: String?) : String {
        return Uri.parse(BuildConfig.BASE_URL).buildUpon()
                .appendPath("api")
                .appendPath("v1")
                .appendPath("json")
                .appendPath(BuildConfig.TSDB_API_KEY)
                .appendPath("searchplayers.php")
                .appendQueryParameter("t", team)
                .build()
                .toString()
    }


}