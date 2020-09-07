package de.nenick.coroutines.test

import androidx.test.internal.runner.junit4.statement.UiThreadStatement
import io.mockk.Call
import io.mockk.MockKAnswerScope
import io.mockk.MockKStubScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.setMain
import org.junit.rules.TestWatcher
import org.junit.runner.Description

class MainCoroutinesDispatcherRule : TestWatcher() {

    companion object {
        private const val coAnswersPausedDelay = 1L

        /**
         * MockK coroutines answer extension does pause before returning values in combination with [TestCoroutineDispatcher].
         *
         * Use [TestCoroutineDispatcher.advanceTimeBy] [coAnswersPausedDelay] to step forward
         * or [TestCoroutineDispatcher.advanceUntilIdle] to ignore further pause points.
         */
        infix fun <T, B> MockKStubScope<T, B>.coAnswersDelayed(answer: suspend MockKAnswerScope<T, B>.(Call) -> T) = coAnswers {
            // TestCoroutineDispatcher will stop execution at this point until time gets advanced by delay value.
            delay(coAnswersPausedDelay)
            answer(it)
        }
    }

    private val dispatcher = TestCoroutineDispatcher()

    override fun starting(description: Description) {
        super.starting(description)
        Dispatchers.setMain(dispatcher)
    }

    fun deliverNextDelayedAnswer() {
        UiThreadStatement.runOnUiThread { dispatcher.advanceTimeBy(coAnswersPausedDelay) }
    }

    fun deliverAllDelayedAnswers() {
        UiThreadStatement.runOnUiThread { dispatcher.advanceUntilIdle() }
    }
}