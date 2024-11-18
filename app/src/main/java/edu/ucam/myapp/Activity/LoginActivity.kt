package edu.ucam.myapp.Activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import edu.ucam.myapp.Helper.FirebaseManager
import edu.ucam.myapp.R

class LoginActivity : BaseActivity() {

    private lateinit var firebaseManager: FirebaseManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        firebaseManager = FirebaseManager()

        /*
        * DEFAULT USER
        * email: pablo@gmail.com
        * password: pablo123
        * */

        val loginButton: Button = findViewById(R.id.loginButton)
        val emailField: EditText = findViewById(R.id.txtUsername)
        val passwordField: EditText = findViewById(R.id.txtPassword)

        loginButton.setOnClickListener {
            val email = emailField.text.toString()
            val password = passwordField.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                signInUser(email, password)
            } else {
                Toast.makeText(this, "Por favor, rellena todos los campos", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun signInUser(email: String, password: String) {
        firebaseManager.signInUser(email, password) { success, message, userEmail, userName ->
            if (success) {
                val intent = Intent(this, HomeActivity::class.java)
                intent.putExtra("USER_NAME", userName)
                intent.putExtra("USER_EMAIL", userEmail)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(baseContext, "Error al iniciar sesión: $message", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }
}