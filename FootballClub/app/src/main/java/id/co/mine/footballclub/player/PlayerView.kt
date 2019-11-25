package id.co.mine.footballclub.player

import id.co.mine.footballclub.model.Player

interface PlayerView {
    fun showLoading()
    fun hideLoading()
    fun showPlayerList(data: List<Player>)
}