package com.template.project.data.network.entities

import com.google.common.truth.Truth.assertThat
import com.template.project.data.network.tools.JsonTest
import org.junit.Test

class TodoJsonTest : JsonTest() {

    @Test
    fun `map json to object`() {
        val result = parse(
            TodoJson::class.java, """
        {
            "userId": 10,
            "id": 198,
            "title": "quis eius est sint explicabo",
            "completed": true
        }
        """
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
