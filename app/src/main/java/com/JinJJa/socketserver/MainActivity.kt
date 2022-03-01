 package com.JinJJa.socketserver

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.JinJJa.socketserver.databinding.ActivityMainBinding
import com.JinJJa.socketserver.service.ServerService

 class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    var serverServiceIntent: Intent?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.button2.setOnClickListener {
            serverServiceIntent= Intent(this, ServerService::class.java)
            startService(serverServiceIntent)
        }


        binding.button.setOnClickListener {
            stopService(serverServiceIntent)
        }

    }
}