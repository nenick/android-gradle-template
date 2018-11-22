package com.template.project.data.network.entities

import com.google.common.truth.Truth.assertThat
import com.google.gson.Gson
import org.junit.Test

class TodoJsonTest {

    @Test
    fun `map json to object`() {
        val result = Gson().fromJson(
            """
        {
            "userId": 10,
            "id": 198,
            "title": "quis eius est sint explicabo",
            "completed": true
        }
        """, TodoJson::class.java
        )

        assertThat(result).isEqualTo(
            TodoJson(
                198,
                10,
                "quis eius est sint explicabo",
                true
            )
        )
    }
}