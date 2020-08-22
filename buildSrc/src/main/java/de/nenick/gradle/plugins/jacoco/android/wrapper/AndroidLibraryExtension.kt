package de.nenick.gradle.plugins.jacoco.android.wrapper

import com.android.build.gradle.LibraryExtension
import com.android.build.gradle.api.BaseVariant
import org.gradle.api.DomainObjectSet

class AndroidLibraryExtension(private val extension: LibraryExtension) : AndroidExtension {

    override fun variants() = extension.libraryVariants as DomainObjectSet<BaseVariant>
}