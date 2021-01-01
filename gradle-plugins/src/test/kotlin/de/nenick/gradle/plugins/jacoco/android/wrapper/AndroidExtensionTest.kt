package de.nenick.gradle.plugins.jacoco.android.wrapper

import de.nenick.gradle.test.tools.project.RawProject
import org.junit.jupiter.api.Test
import strikt.api.expectThrows
import strikt.assertions.isEqualTo
import strikt.assertions.message

class AndroidExtensionTest {

    @Test
    fun `exception when no android extension available`() {
        expectThrows<IllegalStateException> { AndroidExtension.wrap(RawProject().extensions) }
            .message.isEqualTo("No android extension found. Does that module have android applied?")
    }
}