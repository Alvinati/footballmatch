package id.co.mine.footballclub.detail

import com.google.gson.Gson
import id.co.mine.footballclub.api.ApiRepository
import id.co.mine.footballclub.api.TheSportDBApi
import id.co.mine.footballclub.model.DetailResponse
import id.co.mine.footballclub.util.CoroutineContextProvider
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg
import id.co.mine.footballclub.util.EspressoIdlingResource


class DetailPresenter(private val view: DetailView,
                    private val apiRepository: ApiRepository,
                    private val gson: Gson, private val context: CoroutineContextProvider
                      = CoroutineContextProvider(),
                      private val idlingResource: EspressoIdlingResource = EspressoIdlingResource()
                      ) {


    fun getDetail(eventid: String?, path: String?) {
        view.showLoading()
        idlingResource.increment()
        async(context.main) {
            val data = bg {
                gson.fromJson(apiRepository
                        .doRequest(TheSportDBApi.getData(eventid, path)),
                        DetailResponse::class.java
                )
            }

            view.showDetail(data.await().events)
            view.hideLoading()
        }
        idlingResource.decrement()
    }
}