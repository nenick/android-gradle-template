package com.template.project.tools

import kotlinx.coroutines.CoroutineDispatcher

class ProjectDispatchers (
    val main: CoroutineDispatcher,
    val io: CoroutineDispatcher
)