package com.addnw.catalog

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class MainActivity : AppCompatActivity() {
    companion object {
        const val LOG_KEY = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(LOG_KEY, "Created")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}