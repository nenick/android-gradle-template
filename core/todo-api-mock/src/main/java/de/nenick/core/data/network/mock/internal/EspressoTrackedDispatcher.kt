package de.nenick.core.data.network.mock.internal

import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.idling.CountingIdlingResource
import kotlinx.coroutines.CoroutineDispatcher
import kotlin.coroutines.CoroutineContext

/**
 * Let's espresso automatically wait when wrapped coroutine process is ongoing.
 *
 * See also
 * - original idea https://github.com/Kotlin/kotlinx.coroutines/issues/242#issuecomment-561503344
 */
class EspressoTrackedDispatcher<T : CoroutineDispatcher>(
    idlingResourceName: String,
    private val coroutineDispatcher: T
) : CoroutineDispatcher() {

    private val counter = CountingIdlingResource(idlingResourceName)

    init {
        IdlingRegistry.getInstance().register(counter)
    }

    override fun dispatch(context: CoroutineContext, block: Runnable) {
        coroutineDispatcher.dispatch(context, kotlinx.coroutines.Runnable {
            counter.increment()
            try {
                block.run()
            } finally {
                counter.decrement()
            }
        })
    }
}