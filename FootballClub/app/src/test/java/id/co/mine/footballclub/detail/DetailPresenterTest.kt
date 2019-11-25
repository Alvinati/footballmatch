package id.co.mine.footballclub.main

import com.google.gson.Gson
import id.co.mine.footballclub.api.ApiRepository
import id.co.mine.footballclub.api.TheSportDBApi
import id.co.mine.footballclub.TestContextProvider
import id.co.mine.footballclub.detail.DetailPresenter
import id.co.mine.footballclub.detail.DetailView
import id.co.mine.footballclub.model.Detail
import id.co.mine.footballclub.model.DetailResponse
import id.co.mine.footballclub.util.EspressoIdlingResource
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class DetailPresenterTest {

    @Mock
    private
    lateinit var view: DetailView

    @Mock
    private
    lateinit var gson: Gson

    @Mock
    private
    lateinit var apiRepository: ApiRepository

    @Mock
    private var idlingResource: EspressoIdlingResource = EspressoIdlingResource()

    private lateinit var presenter: DetailPresenter


    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = DetailPresenter(view, apiRepository, gson, TestContextProvider(), idlingResource)
    }

    @Test
    fun testGetEventList() {
        val detail: MutableList<Detail> = mutableListOf()
        val response = DetailResponse(detail)
        val leagueid = "133604"
        val path = "lookupteam.php"

        `when`(gson.fromJson(apiRepository
                .doRequest(TheSportDBApi.getData(leagueid, path)),
                DetailResponse::class.java
        )).thenReturn(response)


        presenter.getDetail(leagueid, path)

        Mockito.verify(view).showLoading()
        Mockito.verify(view).showDetail(detail)
        Mockito.verify(view).hideLoading()

    }
}