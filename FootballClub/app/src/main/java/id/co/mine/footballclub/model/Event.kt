package id.co.mine.footballclub.model

import com.google.gson.annotations.SerializedName

  data class Event(
                @SerializedName("idEvent")
                var eventId: String? = null,

                @SerializedName("strHomeTeam")
                var homeTeam: String? = null,

                @SerializedName("strAwayTeam")
                var awayTeam: String? = null,

                @SerializedName("dateEvent")
                var dateEvent: String? = null,

                @SerializedName("intHomeScore")
                var homeScore: String? = null,

                @SerializedName("intAwayScore")
                var awayScore: String? = null
        )

