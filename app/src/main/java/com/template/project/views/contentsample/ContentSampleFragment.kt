package com.template.project.views.contentsample

import com.template.project.R
import com.template.project.data.local.entities.Todo
import com.template.project.tools.BaseFragment
import kotlinx.android.synthetic.main.fragment_content_sample.*
import org.androidannotations.annotations.AfterViews
import org.androidannotations.annotations.EFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

@EFragment(R.layout.fragment_content_sample)
class ContentSampleFragment : BaseFragment() {

    private val model: ContentSampleViewModel by viewModel()

    @AfterViews
    fun connectModel() {
        showUpNavigation()
        model.observerTodo(this) {
            list.adapter = ContentAdapter(it.toTypedArray(), ::showDetails)
        }
    }

    fun showDetails(item: Todo) {
        val detailsFragment = childFragmentManager.findFragmentById(R.id.detailsView)
        if (detailsFragment == null) {
            navigate.toContentDetails(this, item.id)
        } else {
            (detailsFragment as ContentDetailsFragment).show(item.id)
        }
    }
}
