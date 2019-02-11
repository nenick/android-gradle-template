package com.template.project.data.local

import android.database.sqlite.SQLiteConstraintException
import com.google.common.truth.Truth.assertThat
import com.template.project.data.local.entities.Todo
import com.template.project.data.local.tools.DaoTestDefaults
import org.junit.Test
import org.koin.standalone.inject


class TodoDaoTest : DaoTestDefaults() {

    private val todoDao: TodoDao by inject()
    private val testData1 = Todo(1, 1, "Clean Up", "list", false)
    private val testData2 = Todo(2, 1, "Homework", "tasks", true)
    private val testData3 = Todo(3, 1, "Relax", "location", false)

    /**
     * This test has no real value, because it's mostly testing the Room framework functionality.
     * This is more an example how this code will work and could be skipped.
     */
    @Test
    fun `get all`() = ignoreMainThread {
        todoDao.insert(testData1)
        todoDao.insert(testData2)

        val storedData = todoDao.getAll()
        assertThat(storedData).containsExactly(testData1, testData2)
    }

    /**
     * This test has no real value, because it's mostly testing the Room framework functionality.
     * This is more an example how this code will work and could be skipped.
     */
    @Test
    fun `get all - empty list`() = ignoreMainThread {
        val storedData = todoDao.getAll()
        assertThat(storedData).hasSize(0)
    }

    /**
     * This test has no real value, because it's mostly testing the Room framework functionality.
     * This is more an example how this code will work and could be skipped.
     */
    @Test
    fun `get all - observable`() = ignoreMainThread {
        val storedData = todoDao.getAllLive()
        storedData.observeForever {  }
        assertThat(storedData.value).hasSize(0)

        todoDao.insert(testData1)

        assertThat(storedData.value).containsExactly(testData1)
    }

    /**
     * This test has no real value, because it's mostly testing the Room framework functionality.
     * This is more an example how this code will work and could be skipped.
     */
    @Test
    fun `insert list`() = ignoreMainThread {
        todoDao.insert(testData1)

        todoDao.insertList(listOf(testData2, testData3))

        val storedData = todoDao.getAll()
        assertThat(storedData).containsExactly(testData1, testData2, testData3)
    }

    /**
     * This test has no real value, because it's mostly testing the Room framework functionality.
     * This is more an example how this code will work and could be skipped.
     *
     * The value will increase if you want to enforce specific behavior on duplication.
     */
    @Test(expected = SQLiteConstraintException::class)
    fun `insert list - on duplicate error`() = ignoreMainThread {
        todoDao.insert(testData1)

        todoDao.insertList(listOf(testData1, testData2, testData3))

        val storedData = todoDao.getAll()
        assertThat(storedData).containsExactly(testData1, testData2, testData3)
    }

    /**
     * This test has no real value, because it's mostly testing the Room framework functionality.
     * This is more an example how this code will work and could be skipped.
     */
    @Test
    fun `delete all`() = ignoreMainThread {
        todoDao.insert(testData1)
        todoDao.insert(testData2)

        todoDao.deleteAll()

        val storedData = todoDao.getAll()
        assertThat(storedData).hasSize(0)
    }

    /**
     * This test has no real value, because it's mostly testing the Room framework functionality.
     * This is more an example how this code will work and could be skipped.
     */
    @Test
    fun `update all`() = ignoreMainThread {
        todoDao.updateAll(listOf(testData1, testData2))

        val storedData = todoDao.getAll()
        assertThat(storedData).containsExactly(testData1, testData2)
    }

    /**
     * This test has no real value, because it's mostly testing the Room framework functionality.
     * This is more an example how this code will work and could be skipped.
     */
    @Test
    fun `update all - override existing`() = ignoreMainThread {
        todoDao.insert(testData1)

        todoDao.updateAll(listOf(testData1, testData2))

        val storedData = todoDao.getAll()
        assertThat(storedData).containsExactly(testData1, testData2)
    }

    /**
     * Check that our update strategy eliminates obsolete entries.
     */
    @Test
    fun `update all - clean obsolete entries`() = ignoreMainThread {
        todoDao.insert(testData3)

        todoDao.updateAll(listOf(testData1, testData2))

        val storedData = todoDao.getAll()
        assertThat(storedData).doesNotContain(testData3)
    }
}
