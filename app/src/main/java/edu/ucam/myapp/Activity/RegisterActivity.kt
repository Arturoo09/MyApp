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
        val usernameField: EditText = findViewById(R.id.txtUsername)
        val passwordField: EditText = findViewById(R.id.txtPassword)
        val emailField: EditText = findViewById(R.id.txtEmail)

        registerButton.setOnClickListener {
            val email = emailField.text.toString()
            val username = usernameField.text.toString()
            val password = passwordField.text.toString()

            if (email.isNotEmpty() && username.isNotEmpty() && password.isNotEmpty()) {
                registerUser(email, password)
            } else {
                Toast.makeText(this, "Por favor, rellena todos los campos", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun registerUser(email: String, password: String){
        firebaseManager.registerUser(email, password) { success, message ->
            if (success) {
                Toast.makeText(baseContext, "Registro Exitoso", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(baseContext, "Error al registrarse: $message", Toast.LENGTH_SHORT).show()
            }
        }
    }
}