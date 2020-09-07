package de.nenick.template

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupLoadTodoDetails()
    }

    private fun setupLoadTodoDetails() {
        observe(viewModel.result) { message -> textView.text = message }
        button.setOnClickListener {
            viewModel.loadTodo(10)
        }
    }
}

fun <T> AppCompatActivity.observe(observable: LiveData<T>, observer: (result: T) -> Unit) {
    observable.observe(this, Observer { observer(it) })
}