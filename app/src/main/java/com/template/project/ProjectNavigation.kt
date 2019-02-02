package com.template.project

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.template.project.views.contentsample.ContentDetailsFragment_
import org.androidannotations.api.KotlinOpen

@KotlinOpen
class ProjectNavigation {

    fun toContentSample(root: Fragment) {
        root.findNavController().navigate(R.id.action_to_contentSampleFragment)
    }

    fun toContentDetails(root: Fragment, itemId: Int) {
        val args = ContentDetailsFragment_.builder().itemId(itemId).args()
        root.findNavController().navigate(R.id.action_contentSampleFragment_to_contentDetailsFragment_, args)
    }
}
