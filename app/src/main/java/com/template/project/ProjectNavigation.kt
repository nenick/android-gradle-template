package com.template.project

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import org.androidannotations.api.KotlinOpen

@KotlinOpen
class ProjectNavigation {

    fun toContentSample(root: Fragment) {
        navigate(root, R.id.action_to_contentSampleFragment)
    }

    private fun navigate(root: Fragment, target: Int) {
        root.findNavController().navigate(target)
    }
}