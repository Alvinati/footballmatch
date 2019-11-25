package id.co.mine.footballclub.teams

import id.co.mine.footballclub.model.Event
import id.co.mine.footballclub.model.Team

interface TeamView {
    fun showLoading()
    fun hideLoading()
    fun showTeamList(data: List<Team>)
}

