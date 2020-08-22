package de.nenick.gradle.plugins.jacoco.android.wrapper

import com.android.build.gradle.AppExtension
import com.android.build.gradle.LibraryExtension
import com.android.build.gradle.api.BaseVariant
import org.gradle.api.DomainObjectSet
import org.gradle.api.internal.plugins.ExtensionContainerInternal
import java.lang.IllegalStateException

interface AndroidExtension {
    companion object {
        fun wrap(extensions: ExtensionContainerInternal): AndroidExtension {
            extensions.findByType(AppExtension::class.java)?.let { return AndroidApplicationExtension(it) }
            extensions.findByType(LibraryExtension::class.java)?.let { return AndroidLibraryExtension(it) }
            throw IllegalStateException()
        }
    }

    fun variants(): DomainObjectSet<BaseVariant>
    fun testBuildType(): String
}