package com.template.project.views.simplesample


import android.widget.TextView
import androidx.lifecycle.Observer
import com.template.project.R
import com.template.project.tools.BaseFragment
import kotlinx.android.synthetic.main.fragment_simple_sample.*
import org.androidannotations.annotations.AfterViews
import org.androidannotations.annotations.Click
import org.androidannotations.annotations.EFragment
import org.androidannotations.annotations.TextChange
import org.koin.androidx.viewmodel.ext.android.viewModel

@EFragment(R.layout.fragment_simple_sample)
class SimpleSampleFragment : BaseFragment() {

    private val model: SimpleSampleViewModel by viewModel()

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

    @Click(R.id.btn_next)
    fun onNext() {
        navigate(R.id.action_to_contentSampleFragment)
    }
}
