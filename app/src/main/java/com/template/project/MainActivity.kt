package com.template.project

import android.annotation.SuppressLint
import androidx.navigation.findNavController
import com.template.project._base.BaseActivity
import org.androidannotations.annotations.EActivity

@SuppressLint("Registered")
@EActivity(R.layout.activity_main)
class MainActivity : BaseActivity() {

    override fun onSupportNavigateUp() = findNavController(R.id.nav_host_fragment).navigateUp()
}
