package com.template.project.tools

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner

/** Support for customized application class (e.g. clear dependency injection configuration). */
@Suppress("unused") // it's used at build.gradle as testInstrumentationRunner
class AndroidJUnitRunnerCustomApplication : AndroidJUnitRunner() {

    override fun newApplication(cl: ClassLoader, className: String, context: Context): Application {
        return super.newApplication(cl, ProjectTestApplication::class.java.name, context)
    }
}