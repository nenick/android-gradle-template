package com.template.project.views.contenteditsample

import androidx.lifecycle.Observer
import com.template.project.R
import com.template.project.tools.BaseFragment
import kotlinx.android.synthetic.main.fragment_content_details.*
import org.androidannotations.annotations.AfterViews
import org.androidannotations.annotations.EFragment
import org.androidannotations.annotations.FragmentArg
import org.koin.androidx.viewmodel.ext.android.viewModel

@EFragment(R.layout.fragment_content_details)
class ContentEditSampleFragment : BaseFragment() {

    @FragmentArg
    @JvmField
    final var itemId: Int = 0

    val viewModel: ContentEditSsampleViewModel by viewModel()

    @AfterViews
    fun setup() {
        viewModel.todoItem.observe(this, Observer {
            title.text = it.title
            subtitle.text = it.id.toString()
        })

        show(itemId)
    }

    fun show(itemId: Int) {
        viewModel.load(itemId)
    }
}
