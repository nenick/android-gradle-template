package de.nenick.gradle.plugins.basics.extensions

import java.io.File

fun File.withFile(name: String, setup: File.() -> Unit = {}) {
    val file = File(this, name)
    file.createNewFile()
    setup(file)
}