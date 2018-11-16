package com.template.project

import android.app.Application
import com.template.project.data.network.tools.dataNetworkModule
import com.template.project.model.repositories.TodoRepository
import com.template.project.views.contentsample.ContentDetailsViewModel
import com.template.project.views.contentsample.ContentViewModel
import com.template.project.views.simplesample.SimpleSampleViewModel
import org.koin.android.ext.android.startKoin
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

open class ProjectApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin(this, modulesDependencies())
    }

    open fun modulesDependencies() = listOf(appModule, dataNetworkModule)

    private val appModule = module {
        viewModel { SimpleSampleViewModel() }
        viewModel { ContentViewModel() }
        viewModel { ContentDetailsViewModel() }
        single { ProjectNavigation() }
        single { TodoRepository() }
    }

}