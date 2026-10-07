package com.example.smarthub

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        val button = Button(this).apply {
            text = "Enable Smart Hub Overlay"
            setOnClickListener { checkOverlayPermission() }
        }
        setContentView(button)
    }

    private fun checkOverlayPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(this)) {
            val intent = Intent(
                Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                Uri.parse("package:$packageName")
            )
            startActivityForResult(intent, 1234)
        } else {
            startFloatingService()
        }
    }

    private fun startFloatingService() {
        val intent = Intent(this, FloatingHubService::class.java)
        startService(intent)
        Toast.makeText(this, "Smart Hub Dock Active!", Toast.LENGTH_SHORT).show()
        finish()
    }
}
