package com.template.project.views.contenteditsample

import com.template.project.R
import com.template.project.data.local.entities.DescriptionValidation
import com.template.project.data.local.entities.TitleValidation
import com.template.project.data.local.entities.Todo
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

    private val model: ContentEditSampleViewModel by viewModel()

    fun load(itemId: Int) {
        model.load(itemId)
    }

    @AfterViews
    fun setup() {
        load(itemId)
        observe(model.todoItem) {
            bindItemViews(it)
            updateValidationHints(it)
        }
    }

    private fun updateValidationHints(item: Todo) {
        title_layout.error = item.titleValidation().let {
            when (it) {
                is TitleValidation.Valid -> ""
                is TitleValidation.Empty -> "A task needs a title."
                is TitleValidation.ToLong -> "Maximal ${it.maxCharacters} characters."
            }
        }

        subtitle_layout.error = item.descriptionValidation().let {
            when (it) {
                is DescriptionValidation.Valid -> ""
                is DescriptionValidation.ToLong -> "Maximal ${it.maxCharacters} characters."
            }
        }
    }

    private fun bindItemViews(item: Todo) {
        observeTextChanges(title, item.title) { item.copy(title = it) }
        observeTextChanges(subtitle, item.description) { item.copy(description = it) }
    }
}
