package com.walukustudio.kotlin.presenter

import com.google.gson.Gson
import com.walukustudio.kotlin.TestContextProvider
import com.walukustudio.kotlin.model.Schedule
import com.walukustudio.kotlin.model.ScheduleResponse
import com.walukustudio.kotlin.network.ApiRepository
import com.walukustudio.kotlin.network.TheSportDBApi
import com.walukustudio.kotlin.view.MatchDetailView
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(manifest= Config.NONE)
class MatchPresenterTest {

    @Mock
    private
    lateinit var view: MatchDetailView

    @Mock
    private
    lateinit var gson: Gson

    @Mock
    private
    lateinit var apiRepository: ApiRepository

    private lateinit var presenter: MatchPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = MatchPresenter(view,apiRepository,gson, TestContextProvider())
    }

    @Test
    fun getMatchDetail() {
        val match: MutableList<Schedule> = mutableListOf()
        val response = ScheduleResponse(match)
        val eventId = "441613"

        `when`(gson.fromJson(apiRepository.doRequest(TheSportDBApi.getScheduleDetail(eventId)),
                ScheduleResponse::class.java)).thenReturn(response)

        presenter.getMatchDetail(eventId)

        verify(view).showLoading()
        verify(view).showMatchDetail(match)
        verify(view).hideLoading()
    }
}