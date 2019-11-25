package id.co.mine.footballclub.main

import com.google.gson.Gson
import id.co.mine.footballclub.api.ApiRepository
import id.co.mine.footballclub.api.TheSportDBApi
import id.co.mine.footballclub.model.Event
import id.co.mine.footballclub.model.EventResponse
import id.co.mine.footballclub.TestContextProvider
import id.co.mine.footballclub.util.EspressoIdlingResource
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class MainPresenterTest {

    @Mock
    private
    lateinit var view: MainView

    @Mock
    private
    lateinit var gson: Gson

    @Mock
    private
    lateinit var apiRepository: ApiRepository

    @Mock
    private var idlingResource: EspressoIdlingResource = EspressoIdlingResource()


    private lateinit var presenter: MainPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = MainPresenter(view, apiRepository, gson, TestContextProvider(), idlingResource)
    }

    @Test
    fun testGetEventList() {
        val events: MutableList<Event> = mutableListOf()
        val response = EventResponse(events)
        val leagueid = "4328"
        val path = "eventsnextleague.php"

        `when`(gson.fromJson(apiRepository
                .doRequest(TheSportDBApi.getData(leagueid, path)),
                EventResponse::class.java
        )).thenReturn(response)

        presenter.getEventList(leagueid, path)

        Mockito.verify(view).showLoading()
        Mockito.verify(view).showEventList(events)
        Mockito.verify(view).hideLoading()

    }
}