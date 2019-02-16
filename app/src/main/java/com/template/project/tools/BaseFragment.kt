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

    private val textChangeListener = mutableMapOf<EditText, NewValueTextWatcher>()

    protected val navigate: ProjectNavigation by inject()

    /** Activate the up navigation button at the Toolbar. */
    protected fun showUpNavigation() {
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    /** Convenience method for two way data binding with LiveData and EditText. */
    protected fun observeTwoWay(data: MutableLiveData<String>, view: EditText) {
        // update View from LiveData changes
        observe(data) { if (view.text.toString() != it /* avoid loops */) view.setText(it) }

        // update LiveData from View changes
        observeTextChanges(view) { data.value = it }
    }

    /** Convenience method for observing LiveData. */
    protected fun <T> observe(data: MutableLiveData<T>, block: (result: T) -> Unit) {
        data.observe(this, Observer { block(it) })
    }

    /**
     * Convenience method for observing EditText.
     *
     * Avoids having multiple TextWatcher. Only the last receiver will be called.
     * So it's secure to call it multiple times for the same EditText.
     *
     * The receiver will only be called when the value has changed to avoid loops.
     */
    protected fun observeTextChanges(view: EditText, receiver: (String) -> Unit) {
        // Just replace receiver callback when we have added a TextWatcher already.
        textChangeListener[view]?.let { it.receiver = receiver }
        // Else create and register a new TextWatcher.
            ?: NewValueTextWatcher(receiver).also {
                view.addTextChangedListener(it)
                textChangeListener[view] = it
            }
    }

    /**
     * Convenience method for observing EditText.
     *
     * Also set the text at the EditText.
     */
    protected fun observeTextChanges(view: EditText, text: String, receiver: (String) -> Unit) {
        observeTextChanges(view, receiver)

        if (view.text.toString() != text /* avoid loops */) view.setText(text)
    }

    /**
     * TextWatcher which only notify at text changes.
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
