package de.nenick.core.data.network.mock.stubdata

import de.nenick.template.coredata.network.models.TodoJson

object TodoTestData {
    val task1NotCompleted = TodoJson(1, 11, "My Task 1", false)
    val task2NotCompleted = TodoJson(2, 11, "My Task 2", false)
    val task3Completed = TodoJson(3, 11, "My Task 3", true)
    val task4Completed = TodoJson(4, 11, "My Task 4", true)
}