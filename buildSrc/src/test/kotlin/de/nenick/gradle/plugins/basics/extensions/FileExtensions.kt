package de.nenick.gradle.plugins.basics.extensions

import java.io.File

fun File.withDirectory(name: String, setup: File.() -> Unit = {}) {
    val file = File(this, name)
    file.mkdirs()
    setup(file)
}

fun File.withFile(name: String, setup: File.() -> Unit = {}) {
    val file = File(this, name)
    file.createNewFile()
    setup(file)
}