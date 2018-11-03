package com.template.project

import android.annotation.SuppressLint
import android.widget.TextView
import androidx.lifecycle.Observer
import com.template.project._base.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.androidannotations.annotations.AfterViews
import org.androidannotations.annotations.EActivity
import org.androidannotations.annotations.TextChange

@SuppressLint("Registered")
@EActivity(R.layout.activity_main)
class MainActivity : BaseActivity() {

    private val model: MainViewModel by provider(MainViewModel_::class.java)

    @AfterViews
    fun connectViewModel() {
        model.observeTextInput(this, Observer { text ->
            textView.text = text
        })
    }

    @TextChange(R.id.textInput)
    fun onInputChanged(tv: TextView, text: CharSequence) {
        model.updateTextInput(text.toString())
    }
}
