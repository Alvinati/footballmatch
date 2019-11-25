package id.co.mine.footballclub.player

import android.util.Log
import com.google.gson.Gson
import id.co.mine.footballclub.api.ApiRepository
import id.co.mine.footballclub.api.TheSportDBApi
import id.co.mine.footballclub.util.CoroutineContextProvider
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg
import id.co.mine.footballclub.model.PlayerResponse
import id.co.mine.footballclub.util.EspressoIdlingResource


class PlayerPresenter(private val view: PlayerView,
                      private val apiRepository: ApiRepository,
                      private val gson: Gson,
                      private val context: CoroutineContextProvider = CoroutineContextProvider(),
                      private val idlingResource: EspressoIdlingResource = EspressoIdlingResource()
                    ) {

    fun getPlayerList(team: String?) {
        view.showLoading()
        idlingResource.increment()

        async(context.main) {
            val data = bg {
                gson.fromJson(apiRepository
                        .doRequest(TheSportDBApi.getPlayer(team)),
                        PlayerResponse::class.java
                )
            }
            Log.e("GMSP", data.toString())
            view.showPlayerList(data.await().player)
            view.hideLoading()
        }
        idlingResource.decrement()
    }

}
