package id.co.mine.footballclub.model

import com.google.gson.annotations.SerializedName


data class Detail(
        @SerializedName("idEvent")
        var eventId: String? = null,

        @SerializedName("strHomeTeam")
        var homeTeam: String? = null,

        @SerializedName("strAwayTeam")
        var awayTeam: String? = null,

        @SerializedName("idHomeTeam")
        var idHomeTeam: String? = null,

        @SerializedName("idAwayTeam")
        var idAwayTeam: String? = null,

        @SerializedName("dateEvent")
        var dateEvent: String? = null,

        @SerializedName("intHomeScore")
        var homeScore: String? = null,

        @SerializedName("intAwayScore")
        var awayScore: String? = null,

        @SerializedName("intHomeShots")
        var homeShots: String? = null,

        @SerializedName("intAwayShots")
        var awayShots: String? = null,

        @SerializedName("strHomeGoalDetails")
        var homeGDetails: String? =null,

        @SerializedName("strHomeLineupGoalkeeper")
        var homeLGK: String? = null,

        @SerializedName("strHomeLineupDefense")
        var homeDefense: String? = null,

        @SerializedName("strHomeLineupMidfield")
        var homeMidfield: String? = null,

        @SerializedName("strHomeLineupForward")
        var homeForward: String? = null,

        @SerializedName("strHomeLineupSubtitutes")
        var homeSubtitute: String? = null,

        @SerializedName("strAwayGoalDetails")
        var awayGDetails: String? =null,

        @SerializedName("strAwayLineupGoalkeeper")
        var awayLGK: String? = null,

        @SerializedName("strAwayLineupDefense")
        var awayDefense: String? = null,

        @SerializedName("strAwayLineupMidfield")
        var awayMidfield: String? = null,

        @SerializedName("strAwayLineupForward")
        var awayForward: String? = null,

        @SerializedName("strAwayLineupSubtitutes")
        var awaySubtitute: String? = null
)