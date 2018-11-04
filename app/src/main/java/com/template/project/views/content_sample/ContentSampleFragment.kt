package com.template.project.views.content_sample

import androidx.recyclerview.widget.LinearLayoutManager
import com.template.project.R
import com.template.project.tools.BaseFragment
import kotlinx.android.synthetic.main.fragment_content_sample.*
import org.androidannotations.annotations.AfterViews
import org.androidannotations.annotations.EFragment

@EFragment(R.layout.fragment_content_sample)
class ContentSampleFragment : BaseFragment() {

    @AfterViews
    fun setupViews() {
        list.layoutManager = LinearLayoutManager(context)
        list.adapter = ContentAdapter(arrayOf("first", "second", "any", "time", "it", "happen"))
    }
}
