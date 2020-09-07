package de.nenick.template

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner

class TemplateAppAndroidJunitRunner : AndroidJUnitRunner() {
    override fun newApplication(cl: ClassLoader, className: String, context: Context): Application {
        // Provides our own application class for a customized test setup.
        return super.newApplication(cl, TemplateTestApplication::class.java.name, context)
    }
}