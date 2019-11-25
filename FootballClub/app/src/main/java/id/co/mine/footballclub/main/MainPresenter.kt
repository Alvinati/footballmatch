package id.co.mine.footballclub.main

import com.google.gson.Gson
import id.co.mine.footballclub.api.ApiRepository
import id.co.mine.footballclub.api.TheSportDBApi
import id.co.mine.footballclub.model.EventResponse
import id.co.mine.footballclub.util.CoroutineContextProvider
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg
import android.support.test.espresso.idling.CountingIdlingResource
import android.util.Log
import id.co.mine.footballclub.util.EspressoIdlingResource


class MainPresenter(private val view: MainView,
                    private val apiRepository: ApiRepository,
                    private val gson: Gson,
                    private val context: CoroutineContextProvider = CoroutineContextProvider(),
                    private val idlingResource: EspressoIdlingResource = EspressoIdlingResource()
                    ) {

    fun getEventList(leagueid: String?, path: String?) {
        view.showLoading()
        idlingResource.increment()
        async(context.main) {
            val data = bg {
                gson.fromJson(apiRepository
                        .doRequest(TheSportDBApi.getData(leagueid, path)),
                        EventResponse::class.java
                )
            }

            view.showEventList(data.await().events)
            view.hideLoading()
        }
        idlingResource.decrement()
    }

}
