package com.template.project.model.usecases.todo

import com.google.common.truth.Truth.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class RefreshTodosUseCaseTest {

    @InjectMocks
    lateinit var useCase: RefreshTodosUseCase

    @Test fun `some thing`() {
        val result = useCase.apply()
        assertThat(result.value!!.state).isEqualTo("Finished")
    }

}