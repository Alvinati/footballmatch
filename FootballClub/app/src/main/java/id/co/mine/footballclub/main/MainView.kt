package id.co.mine.footballclub.main

import id.co.mine.footballclub.model.Event

interface MainView {
    fun showLoading()
    fun hideLoading()
    fun showEventList(data: List<Event>)
}

