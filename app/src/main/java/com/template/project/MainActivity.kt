package com.template.project

import android.annotation.SuppressLint
import android.support.v7.app.AppCompatActivity
import org.androidannotations.annotations.Bean
import org.androidannotations.annotations.EActivity

@SuppressLint("Registered")
@EActivity(R.layout.activity_main)
class MainActivity : AppCompatActivity() {

    @Bean
    protected lateinit var anything: AnyUsefulDependency

}
