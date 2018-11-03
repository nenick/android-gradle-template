package com.template.project.views.simple_sample


import android.widget.TextView
import androidx.lifecycle.Observer
import com.template.project.R
import com.template.project._base.BaseFragment
import kotlinx.android.synthetic.main.fragment_simple_sample.*
import org.androidannotations.annotations.AfterViews
import org.androidannotations.annotations.EFragment
import org.androidannotations.annotations.TextChange

@EFragment(R.layout.fragment_simple_sample)
class SimpleSampleFragment : BaseFragment() {

    private val model: SimpleSampleViewModel by provider(SimpleSampleViewModel_::class.java)

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
