package com.example.sharedreferencenotif

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.sharedreferencenotif.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var prefManager: PrefManager

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        prefManager = PrefManager.getInstance(this)
        val username = prefManager.getUsername()
        checkLoginStatus()

        with(binding){
            txtUsername.text = username
            btnLogout.setOnClickListener{
                prefManager.saveUsername("")
                checkLoginStatus()
            }
            btnClear.setOnClickListener{
                prefManager.clear()
                checkLoginStatus()
                Log.d("MainActivity", "Username: $username")
            }

            btnNotif.setOnClickListener{
                startActivity(Intent(this@MainActivity, NotifActivity::class.java))
            }
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun checkLoginStatus() {
        if (prefManager.getUsername().isEmpty()) {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
}