package id.co.mine.footballclub

import id.co.mine.footballclub.util.CoroutineContextProvider
import kotlinx.coroutines.experimental.Unconfined
import kotlin.coroutines.experimental.CoroutineContext


class TestContextProvider: CoroutineContextProvider() {
    override val main: CoroutineContext = Unconfined
}