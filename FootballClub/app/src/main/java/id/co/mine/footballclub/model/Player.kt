package id.co.mine.footballclub.model

import com.google.gson.annotations.SerializedName

  data class Player(

                @SerializedName("idPlayer")
                var playerId: String? = null,

                @SerializedName("strPlayer")
                var playerName: String? = null,

                @SerializedName("strPosition")
                var playerPosition: String? = null,

                @SerializedName("strHeight")
                var playerHeight: String? = null,

                @SerializedName("strWeight")
                var playerWeight: String? = null,

                @SerializedName("strDescriptionEN")
                var playerDescEN: String? = null,

                @SerializedName("strCutout")
                var playerPhoto: String? = null,

                @SerializedName("strFanart1")
                var playerThumb: String? = null

        )

