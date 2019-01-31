package com.template.project.views.simplesample

import com.template.project.R
import com.template.project.tools.BaseFragment
import kotlinx.android.synthetic.main.fragment_simple_sample.*
import org.androidannotations.annotations.AfterViews
import org.androidannotations.annotations.Click
import org.androidannotations.annotations.EFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

@EFragment(R.layout.fragment_simple_sample)
class SimpleSampleFragment : BaseFragment() {

    private val model: SimpleSampleViewModel by viewModel()

    @AfterViews
    fun connectViewModel() {
        observeTwoWay(model.textInput, textInput)
        observe(model.textInput) { textView.text = it }
    }

    @Click(R.id.btn_next)
    fun onNext() {
        navigate.toContentSample(this)
    }
}
