package com.template.project.tools

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.template.project.ProjectNavigation
import org.androidannotations.annotations.EFragment
import org.koin.android.ext.android.inject

/** Provides some basic feature for working with Fragments. */
@EFragment
abstract class BaseFragment : Fragment() {

    protected val navigate: ProjectNavigation by inject()

    /** Activate the up navigation button on the Toolbar */
    protected fun showUpNavigation() {
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }
}