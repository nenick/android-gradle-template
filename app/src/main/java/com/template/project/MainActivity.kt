package com.template.project

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import org.androidannotations.annotations.EActivity

@SuppressLint("Registered")
@EActivity(R.layout.activity_main)
class MainActivity : AppCompatActivity() {

    override fun onSupportNavigateUp() = findNavController(R.id.nav_host_fragment).navigateUp()
}
