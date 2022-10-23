package com.example.myapplication

import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.databinding.ActivityMain3Binding
import com.example.myapplication.databinding.ActivityMainBinding

class Activity3 : AppCompatActivity() {

    private lateinit var binding: ActivityMain3Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMain3Binding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.text.text = "Activity3"
        binding.button.text = "Crash"
        binding.button2.text = "Crash async"
        binding.button3.text = "Crash service"

        binding.button.setOnClickListener { view ->
            throw RuntimeException()
        }

        binding.button2.setOnClickListener { view ->
            object : AsyncTask<Unit, Unit, Unit>() {
                override fun doInBackground(vararg p0: Unit?) {
                    throw RuntimeException()
                }
            }.execute()
        }

        binding.button3.setOnClickListener { view ->
            startService(Intent(applicationContext, CrashService::class.java))
        }
    }
}
