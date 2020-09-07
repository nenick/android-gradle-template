package de.nenick.coroutines.test

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.nenick.coroutines.test.MainCoroutinesDispatcherRule.Companion.coAnswersDelayed
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.launch
import org.junit.Rule
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

class MainCoroutinesDispatcherRuleTest {

    @get:Rule
    var mainDispatcherRule = MainCoroutinesDispatcherRule()

    private val runner = RunnerStub(
        dependency = mockk {
            coEvery { executeFirstDependencyFunction() } coAnswersDelayed { "first" }
            coEvery { executeSecondDependencyFunctionB() } coAnswersDelayed { "second" }
        }
    )

    @Test
    fun viewModelScopeStepByStep() {
        expectThat(runner.state).isEqualTo("initial")

        runner.execute()
        expectThat(runner.state).isEqualTo("started")

        mainDispatcherRule.deliverNextDelayedAnswer()
        expectThat(runner.state).isEqualTo("first")

        mainDispatcherRule.deliverNextDelayedAnswer()
        expectThat(runner.state).isEqualTo("second")
    }

    @Test
    fun viewModelScopeAllByOne() {
        expectThat(runner.state).isEqualTo("initial")

        runner.execute()
        mainDispatcherRule.deliverAllDelayedAnswers()
        expectThat(runner.state).isEqualTo("second")
    }

    class RunnerStub(private val dependency: DependencyStub) : ViewModel() {

        interface DependencyStub {
            suspend fun executeFirstDependencyFunction(): String
            suspend fun executeSecondDependencyFunctionB(): String
        }

        var state = "initial"

        fun execute() = viewModelScope.launch {
            state = "started"
            state = dependency.executeFirstDependencyFunction()
            state = dependency.executeSecondDependencyFunctionB()
        }
    }
}