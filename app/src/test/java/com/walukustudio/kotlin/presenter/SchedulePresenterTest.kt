package com.walukustudio.kotlin.presenter

import com.google.gson.Gson
import com.walukustudio.kotlin.BuildConfig
import com.walukustudio.kotlin.TestContextProvider
import com.walukustudio.kotlin.model.League
import com.walukustudio.kotlin.model.LeagueResponse
import com.walukustudio.kotlin.model.Schedule
import com.walukustudio.kotlin.model.ScheduleResponse
import com.walukustudio.kotlin.network.ApiRepository
import com.walukustudio.kotlin.network.TheSportDBApi
import com.walukustudio.kotlin.view.ScheduleView
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(manifest= Config.NONE)
class SchedulePresenterTest {
    @Mock
    private
    lateinit var view: ScheduleView

    @Mock
    private
    lateinit var gson: Gson

    @Mock
    private
    lateinit var apiRepository: ApiRepository

    private lateinit var presenter: SchedulePresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = SchedulePresenter(view,apiRepository,gson,TestContextProvider())
    }


    @Test
    fun getPastScheduleList() {
        val schedules: MutableList<Schedule> = mutableListOf()
        val leagues: MutableList<League> = mutableListOf()
        val response = ScheduleResponse(schedules)
        val responseLeague = LeagueResponse(leagues)
        val id = "4328"

        `when`(gson.fromJson(apiRepository.doRequest(TheSportDBApi.getPastSchedule(id)),
                ScheduleResponse::class.java)).thenReturn(response)

        `when`(gson.fromJson(apiRepository.doRequest(TheSportDBApi.getLeagues()),
                LeagueResponse::class.java)).thenReturn(responseLeague)

        presenter.getScheduleList(id,BuildConfig.PAST)

        verify(view).showLoading()
        verify(view).showLeagueList(leagues)
        verify(view).showScheduleList(schedules)
        verify(view).hideLoading()
    }
}