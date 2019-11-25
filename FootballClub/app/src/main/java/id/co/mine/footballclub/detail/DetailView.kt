package id.co.mine.footballclub.detail

import id.co.mine.footballclub.model.Detail

interface DetailView {
    fun showLoading()
    fun hideLoading()
    fun showDetail(data: List<Detail>)
}
