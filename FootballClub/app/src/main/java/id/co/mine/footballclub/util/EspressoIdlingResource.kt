package id.co.mine.footballclub.util

import android.support.test.espresso.IdlingResource
import android.support.test.espresso.idling.CountingIdlingResource

open class EspressoIdlingResource {
    private val RESOURCE = "GLOBAL"

    private val mCountingIdlingResource = CountingIdlingResource(RESOURCE)

    fun increment() {
        mCountingIdlingResource.increment()
    }

    fun decrement() {
        mCountingIdlingResource.decrement()
    }

    fun getIdlingResource(): IdlingResource {
        return mCountingIdlingResource
    }
}