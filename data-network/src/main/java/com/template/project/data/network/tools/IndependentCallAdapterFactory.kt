package com.template.project.data.network.tools

import retrofit2.SuspendCallAdapter
import retrofit2.SuspendCallAdapter.Factory
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

class IndependentCallAdapterFactory : Factory() {

    override fun get(returnType: Type, annotations: Array<Annotation>, retrofit: Retrofit): SuspendCallAdapter<*, *>? {

        if (Factory.getRawType(returnType) != ApiResponse::class.java) {
            return null
        }

        if (returnType !is ParameterizedType) {
            throw IllegalArgumentException("resource must be parameterized")
        }

        val bodyType = Factory.getParameterUpperBound(0, returnType)
        return IndependentCallAdapter<Any>(bodyType)
    }
}
