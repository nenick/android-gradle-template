package com.template.project.tools

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
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

    /** Read and update EditText component. */
    protected fun observeTwoWay(data: MutableLiveData<String>, view: EditText) {
        // update View component
        data.observe(this, Observer { if (view.text.toString() != it /* avoid loops */) view.setText(it) })

        // update LiveData
        view.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) { /* no need */
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (data.value != s.toString() /* avoid loops */) data.value = s.toString()
            }
        })
    }

    protected fun <T> observe(data: MutableLiveData<T>, block: (result: T) -> Unit) {
        data.observe(this, Observer { block(it) })
    }
}