package com.template.project.views.contentsample

import android.widget.Toast
import com.template.project.R
import com.template.project.data.local.entities.Todo
import com.template.project.tools.BaseFragment
import com.template.project.views.contenteditsample.ContentEditSampleFragment
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
        swipe_container.setOnRefreshListener(::requestRefresh)

        observe(model.errorChannel, ::showError)
        observe(model.todoListIsLoading, ::updateProgressIndicator)
        observe(model.todoList, ::updateListView)
    }

    private fun requestRefresh() {
        model.refreshTodo()
    }

    private fun showError(error: String) {
        Toast.makeText(context, error, Toast.LENGTH_LONG).show()
    }

    private fun updateProgressIndicator(isLoading: Boolean) {
        swipe_container.isRefreshing = isLoading
    }

    private fun updateListView(todo: List<Todo>) {
        list.adapter = ContentSampleAdapter(todo, ::showDetailsOnItemClick)
    }

    fun showDetailsOnItemClick(item: Todo) {
        val detailsFragment = childFragmentManager.findFragmentById(R.id.detailsView)
        if (detailsFragment == null) {
            navigate.toContentDetails(this, item.id)
        } else {
            (detailsFragment as ContentEditSampleFragment).show(item.id)
        }
    }
}
