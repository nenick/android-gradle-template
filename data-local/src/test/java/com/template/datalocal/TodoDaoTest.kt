package com.template.datalocal

import com.google.common.truth.Truth.assertThat
import com.template.datalocal.entities.Todo
import com.template.datalocal.tools.DaoTest
import org.junit.Test


class TodoDaoTest : DaoTest() {

    private val todoDao = database.todo()
    private val testData1 = Todo(1, 1, "Clean Up", false)
    private val testData2 = Todo(2, 1, "Homework", true)

    @Test
    fun `get all`() = ignoreMainThread {
        todoDao.insert(testData1)
        todoDao.insert(testData2)

        val storedData = todoDao.getAll()
        assertThat(storedData).containsExactly(testData1, testData2)
    }

    @Test
    fun `get all - empty list`() = ignoreMainThread {
        val storedData = todoDao.getAll()
        assertThat(storedData).hasSize(0)
    }

    @Test
    fun `get all live`() = ignoreMainThread {
        val storedData = todoDao.getAllLive()
        storedData.observeForever {  }
        assertThat(storedData.value).hasSize(0)

        todoDao.insert(testData1)

        assertThat(storedData.value).containsExactly(testData1)
    }

    @Test
    fun `delete all`() = ignoreMainThread {
        todoDao.insert(testData1)
        todoDao.insert(testData2)

        todoDao.deleteAll()

        val storedData = todoDao.getAll()
        assertThat(storedData).hasSize(0)
    }

}