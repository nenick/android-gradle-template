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
        observeTextChanges(view) { data.value = it }
    }

    protected fun <T> observe(data: MutableLiveData<T>, block: (result: T) -> Unit) {
        data.observe(this, Observer { block(it) })
    }

    private val textChangeListener = mutableMapOf<EditText, NewValueTextWatcher>()

    /**
     * Convenience method for observing EditText.
     *
     * Avoid adding multiple listener for each EditText.
     * The receiver will only be called when the value has changed to avoid loops.
     */
    protected fun observeTextChanges(view: EditText, receiver: (String) -> Unit) {
        // Only update receiver logic when we have added a TextWatcher already.
        textChangeListener[view]?.apply { this.receiver = receiver }
        // Else create and register a new TextWatcher.
            ?: NewValueTextWatcher(receiver).also {
                view.addTextChangedListener(it)
                textChangeListener[view] = it
            }
    }

    /**
     * TextWatcher only notify on text changes.
     *
     * New values are compared with previous value and if not match it gets reported to receiver.
     */
    class NewValueTextWatcher(var receiver: (String) -> Unit) : TextWatcher {

        private var current: String = ""

        override fun afterTextChanged(editable: Editable?) {
            val new = editable.toString()
            if (current != new /* avoid loops */) {
                current = new
                receiver(current)
            }
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            /* no need for this event */
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            /* no need for this event */
        }

    }
}
