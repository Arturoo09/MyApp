package edu.ucam.myapp.Activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import edu.ucam.myapp.Helper.FirebaseManager
import edu.ucam.myapp.R

class RegisterActivity : BaseActivity() {

    private lateinit var firebaseManager: FirebaseManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        firebaseManager = FirebaseManager()

        val registerButton: Button = findViewById(R.id.btnRegister)
        val nameField: EditText = findViewById(R.id.txtUsername)
        val emailField: EditText = findViewById(R.id.txtEmail)
        val passwordField: EditText = findViewById(R.id.txtPassword)

        registerButton.setOnClickListener {
            val name = nameField.text.toString()
            val email = emailField.text.toString()
            val password = passwordField.text.toString()

            if (name.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()) {
                registerUser(name, email, password)
            } else {
                Toast.makeText(this, "Por favor, rellena todos los campos", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun registerUser(name: String, email: String, password: String) {
        firebaseManager.registerUser(name, email, password) { success, message, userEmail, userName ->
            if (success) {
                val intent = Intent(this, HomeActivity::class.java)
                intent.putExtra("USER_NAME", userName)
                intent.putExtra("USER_EMAIL", userEmail)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(baseContext, "Error al registrar usuario: $message", Toast.LENGTH_SHORT).show()
            }
        }
    }
}