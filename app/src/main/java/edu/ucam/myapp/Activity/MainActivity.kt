package edu.ucam.myapp.Activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import edu.ucam.myapp.R


class MainActivity : BaseActivity() {

    private var loginButton: Button? = null
    private var registerButton: Button? = null
    private var guestButton: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loginButton = findViewById(R.id.loginButton)
        registerButton = findViewById(R.id.registerButton)
        guestButton = findViewById(R.id.guestButton)

        loginButton?.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)


            startActivity(intent)
        }

        registerButton?.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        guestButton?.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}

