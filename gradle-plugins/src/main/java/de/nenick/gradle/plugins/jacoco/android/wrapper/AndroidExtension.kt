package de.nenick.gradle.plugins.jacoco.android.wrapper

import com.android.build.gradle.AppExtension
import com.android.build.gradle.LibraryExtension
import com.android.build.gradle.TestExtension
import com.android.build.gradle.api.BaseVariant
import org.gradle.api.DomainObjectSet
import org.gradle.api.plugins.ExtensionContainer

interface AndroidExtension {
    companion object {
        fun wrap(extensions: ExtensionContainer): AndroidExtension {
            extensions.findByType(AppExtension::class.java)?.let { return AndroidApplicationExtension(it) }
            extensions.findByType(LibraryExtension::class.java)?.let { return AndroidLibraryExtension(it) }
            extensions.findByType(TestExtension::class.java)?.let { return AndroidTestExtension(it) }
            throw IllegalStateException("No android extension found. Does that module have android applied?")
        }
    }

    fun variants(): DomainObjectSet<BaseVariant>
}