package edu.ucam.myapp.Activity

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.PackageManagerCompat
import edu.ucam.myapp.databinding.ActivityProfileBinding

class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding
    private var profilePicUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val userName = intent.getStringExtra("USER_NAME")
        val userEmail = intent.getStringExtra("USER_EMAIL")
        val description = intent.getStringExtra("DESCRIPTION")
        profilePicUri = intent.getParcelableExtra("PROFILE_PIC_URI")

        binding.userNameText.text = userName
        binding.userEmailText.text = userEmail
        binding.descriptionTxt.text = Editable.Factory.getInstance().newEditable(description ?: "")
        profilePicUri?.let { binding.profilePic.setImageURI(it) }

        binding.backBtn.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            intent.putExtra("USER_NAME", userName)
            intent.putExtra("USER_EMAIL", userEmail)
            intent.putExtra("DESCRIPTION", description)
            intent.putExtra("PROFILE_PIC_URI", profilePicUri.toString())
            startActivity(intent)
        }

        val cameraLauncher =
            registerForActivityResult(ActivityResultContracts.TakePicturePreview()) { bitmap ->
                binding.profilePic.setImageBitmap(bitmap)
            }

        binding.profilePic.setOnClickListener {
            // println("Abriendo camara")
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.CAMERA
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                cameraLauncher.launch(null)
            } else {
                val CAMERA_PERMISSION_REQUEST_CODE = 0
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.CAMERA),
                    CAMERA_PERMISSION_REQUEST_CODE
                )
            }

        }
    }
}