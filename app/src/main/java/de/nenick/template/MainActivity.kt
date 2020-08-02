package de.nenick.template

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val model: MainViewModel by viewModels()

        model.result.observe(this, Observer { textView.text = it })
        button.setOnClickListener {
            textView.text = "doing request"
            model.requestTodo()
        }
    }
}