package com.template.project._base

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.androidannotations.annotations.EBean
import org.androidannotations.annotations.RootContext
import java.lang.reflect.InvocationTargetException

@EBean(scope = EBean.Scope.Singleton)
class AndroidAnnotationViewModelFactory : ViewModelProvider.NewInstanceFactory() {

    @RootContext
    protected lateinit var applicationContext: Context

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        try {
            @Suppress("UNCHECKED_CAST")
            return modelClass.getMethod("getInstance_", Context::class.java).invoke(null, applicationContext) as T
        } catch (e: NoSuchMethodException) {
            throw RuntimeException("Cannot create an instance of $modelClass", e)
        } catch (e: IllegalAccessException) {
            throw RuntimeException("Cannot create an instance of $modelClass", e)
        } catch (e: InstantiationException) {
            throw RuntimeException("Cannot create an instance of $modelClass", e)
        } catch (e: InvocationTargetException) {
            throw RuntimeException("Cannot create an instance of $modelClass", e)
        }
    }
}
