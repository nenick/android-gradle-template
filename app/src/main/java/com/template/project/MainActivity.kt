package com.template.project

import android.annotation.SuppressLint
import android.text.Editable
import android.text.TextWatcher
import androidx.lifecycle.Observer
import com.template.project._base.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.androidannotations.annotations.AfterViews
import org.androidannotations.annotations.Bean
import org.androidannotations.annotations.EActivity

@SuppressLint("Registered")
@EActivity(R.layout.activity_main)
class MainActivity : BaseActivity() {

    private val model: MainViewModel by provider(MainViewModel_::class.java)

    @Bean
    protected lateinit var anything: AnyUsefulDependency

    @AfterViews
    protected fun setup() {
        model.observeTextInput(this, Observer { text ->
            textView.text = text
        })

        textInput.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(text: Editable) {
                model.updateTextInput(text.toString())
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        })
    }
}
