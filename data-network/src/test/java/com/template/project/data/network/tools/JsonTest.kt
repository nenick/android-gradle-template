package com.template.project.data.network.tools

import com.google.gson.Gson

abstract class JsonTest {

    fun <T> parse(target: Class<T>, json: String): T {
        return Gson().fromJson(json, target)
    }
}
