package com.example.sharedreferencenotif

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.sharedreferencenotif.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var prefManager: PrefManager
    private var usernameAccount = "ahmadLuthfi"
    private var passwordAccount = "123456"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)

        enableEdgeToEdge()
        setContentView(binding.root)

        prefManager = PrefManager.getInstance(this)

        with(binding){
            btnLogin.setOnClickListener{
                val usernameInput = edtUsername.text.toString()
                val passwordInput = edtPassword.text.toString()

                if (usernameInput == usernameAccount && passwordInput == passwordAccount){
                    prefManager.saveUsername(usernameInput)
                    checkLoginStatus()
                }else{
                    Toast.makeText(this@LoginActivity, "Username atau password salah", Toast.LENGTH_SHORT).show()
                }
            }
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
    private fun checkLoginStatus() {
        val username = prefManager.getUsername()
        if (username.isNotEmpty()) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
}