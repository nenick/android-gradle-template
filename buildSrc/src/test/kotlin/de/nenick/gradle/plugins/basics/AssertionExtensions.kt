package de.nenick.gradle.plugins.basics

import java.io.File
import org.gradle.api.Task
import strikt.api.Assertion

fun Assertion.Builder<Task?>.hasName(expected: String): Assertion.Builder<Task?> =
    assert("has name %s", expected) {
        when (it?.name) {
            null -> fail(actual = null)
            expected -> pass()
            else -> fail(actual = it.name)
        }
    }

fun Assertion.Builder<Task?>.hasGroup(expected: String): Assertion.Builder<Task?> =
    assert("has group %s", expected) {
        when (it?.group) {
            null -> fail(actual = null)
            expected -> pass()
            else -> fail(actual = it.group)
        }
    }

fun Assertion.Builder<File>.doesNotExists(): Assertion.Builder<File> =
    assert("does exists is %s", false) {
        when (it.exists()) {
            false -> pass()
            else -> fail(actual = it.exists())
        }
    }