package com.template.project

import android.app.Application
import com.template.project.model.repositories.TodoRepository
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

    open fun modulesDependencies() = listOf(appModule)

    private val appModule = module {
        viewModel { SimpleSampleViewModel() }
        viewModel { ContentViewModel() }
        single { ProjectNavigation() }
        single { TodoRepository() }
    }

}