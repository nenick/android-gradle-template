package com.template.project

import android.app.Application
import android.os.AsyncTask
import com.template.project.data.local.tools.dataLocalModule
import com.template.project.data.network.tools.dataNetworkModule
import com.template.project.model.repositories.TodoRepository
import com.template.project.views.contentsample.ContentDetailsViewModel
import com.template.project.views.contentsample.ContentSampleViewModel
import com.template.project.views.simplesample.SimpleSampleViewModel
import kotlinx.coroutines.asCoroutineDispatcher
import org.koin.android.ext.android.startKoin
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

open class ProjectApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin(this, modulesDependencies(), loadPropertiesFromFile = true)
    }

    open fun modulesDependencies() = listOf(appModule, dataLocalModule, dataNetworkModule)

    private val appModule = module {
        viewModel { SimpleSampleViewModel() }
        viewModel { ContentSampleViewModel() }
        viewModel { ContentDetailsViewModel() }
        single { ProjectNavigation() }
        single { TodoRepository() }
        // Espresso waits automatically until all AsyncTask.THREAD_POOL_EXECUTOR threads are idle.
        single { AsyncTask.THREAD_POOL_EXECUTOR.asCoroutineDispatcher() }
    }

}