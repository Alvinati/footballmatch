package id.co.mine.footballclub.teams

import com.google.gson.Gson
import id.co.mine.footballclub.api.ApiRepository
import id.co.mine.footballclub.api.TheSportDBApi
import id.co.mine.footballclub.model.EventResponse
import id.co.mine.footballclub.util.CoroutineContextProvider
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg
import android.support.test.espresso.idling.CountingIdlingResource
import id.co.mine.footballclub.model.TeamResponse
import id.co.mine.footballclub.util.EspressoIdlingResource


class TeamPresenter(private val view: TeamView,
                    private val apiRepository: ApiRepository,
                    private val gson: Gson,
                    private val context: CoroutineContextProvider = CoroutineContextProvider(),
                    private val idlingResource: EspressoIdlingResource = EspressoIdlingResource()
                    ) {

    fun getTeamList(league: String?) {
        view.showLoading()
        idlingResource.increment()
        async(context.main) {
            val data = bg {
                gson.fromJson(apiRepository
                        .doRequest(TheSportDBApi.getTeam(league)),
                        TeamResponse::class.java
                )
            }
            view.showTeamList(data.await().teams)
            view.hideLoading()
        }
        idlingResource.decrement()
    }

}
