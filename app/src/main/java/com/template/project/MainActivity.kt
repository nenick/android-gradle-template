package com.template.project

import android.annotation.SuppressLint
import android.os.Bundle
import com.template.project._base.BaseActivity
import com.template.project.views.simple_sample.SimpleSampleFragment_
import org.androidannotations.annotations.EActivity

@SuppressLint("Registered")
@EActivity(R.layout.activity_main)
class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if(savedInstanceState == null) {
            val fragmentTransaction = supportFragmentManager.beginTransaction()
            val fragment = SimpleSampleFragment_.builder().build()
            fragmentTransaction.replace(R.id.fragmentContainer, fragment)
            fragmentTransaction.commit()
        }
    }
}
