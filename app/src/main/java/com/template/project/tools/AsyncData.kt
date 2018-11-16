package com.template.project.tools

/** Works like LiveData without switching to ui thread. */
class AsyncData<T : Any>(value: T?) {

    var observer: (T) -> Unit = {}

    var value: T? = value
        set(value) {
            field = value
            value?.let { observer.invoke(it) }

        }

    fun observe(listener: (T) -> Unit) {
        observer = listener
    }
}