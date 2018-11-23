package com.template.project.tools

import android.os.AsyncTask
import com.template.project.ProjectNavigation
import com.template.project.model.repositories.TodoRepository
import com.template.project.views.contentsample.ContentDetailsViewModel
import com.template.project.views.contentsample.ContentSampleViewModel
import com.template.project.views.simplesample.SimpleSampleViewModel
import kotlinx.coroutines.asCoroutineDispatcher
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val appModule = module {
    viewModel { SimpleSampleViewModel() }
    viewModel { ContentSampleViewModel() }
    viewModel { ContentDetailsViewModel() }
    single { ProjectNavigation() }
    single { TodoRepository() }
    // Espresso waits automatically until all AsyncTask.THREAD_POOL_EXECUTOR threads are idle.
    single { AsyncTask.THREAD_POOL_EXECUTOR.asCoroutineDispatcher() }
}