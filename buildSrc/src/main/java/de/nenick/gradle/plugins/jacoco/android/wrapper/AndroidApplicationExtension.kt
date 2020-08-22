package de.nenick.gradle.plugins.jacoco.android.wrapper

import com.android.build.gradle.AppExtension
import com.android.build.gradle.api.BaseVariant
import org.gradle.api.DomainObjectSet

class AndroidApplicationExtension(private val extension: AppExtension) : AndroidExtension {

    override fun variants() = extension.applicationVariants as DomainObjectSet<BaseVariant>
}