package edu.ucam.myapp.Activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import edu.ucam.myapp.databinding.ActivityProfileBinding

class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtener datos del Intent
        val userName = intent.getStringExtra("USER_NAME")
        val userEmail = intent.getStringExtra("USER_EMAIL")

        // Actualizar UI con los datos del usuario
        binding.userNameText.text = userName
        binding.userEmailText.text = userEmail
    }
}