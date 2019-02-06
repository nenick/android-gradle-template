package com.template.project.tools

import kotlin.coroutines.CoroutineContext

/**
 * Imitate the Dispatchers class to support replaceable contexts for testing.
 *
 * @see kotlinx.coroutines.Dispatchers
 */
class ProjectDispatchers (
    val main: CoroutineContext,
    val io: CoroutineContext
)
