package id.co.mine.footballclub.db


data class FavoriteEvent(val id: Long?, val eventId: String?,
                    val homeTeam: String?, val awayTeam: String?, val homeScore: String?,
                    val awayScore: String?, val eventDate: String?  ) {

    companion object {
        const val TABLE_FAVORITE: String = "TABLE_FAVORITE"
        const val ID: String = "ID_"
        const val EVENT_ID: String = "EVENT_ID"
        const val HOME_TEAM: String = "HOME_TEAM"
        const val AWAY_TEAM: String = "AWAY_TEAM"
        const val EVENT_DATE: String = "EVENT_DATE"
        const val HOME_SCORE: String = "HOME_SCORE"
        const val AWAY_SCORE: String = "AWAY_SCORE"
    }
}