package de.nenick.gradle.plugins.jacoco.android.wrapper

import com.android.build.gradle.TestExtension
import com.android.build.gradle.api.BaseVariant
import org.gradle.api.DomainObjectSet

class AndroidTestExtension(private val extension: TestExtension) : AndroidExtension {

    @Suppress("UNCHECKED_CAST")
    override fun variants() = extension.applicationVariants as DomainObjectSet<BaseVariant>
}