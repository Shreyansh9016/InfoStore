package com.example.myapplication

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home)

        val name=intent.getStringExtra(SignInActivity.KEY2)
        val mail=intent.getStringExtra(SignInActivity.KEY1)
        val username=intent.getStringExtra(SignInActivity.KEY3)

        val welcomesTex = findViewById<TextView>(R.id.tvWelcome)
        val mailtext = findViewById<TextView>(R.id.tvMail   )
        val id = findViewById<TextView>(R.id.tvID)

        welcomesTex.text = "Welcome  $name"
        mailtext.text = "Mail : $mail"
        id.text = "ID : $username"
    }
    @SuppressLint("MissingSuperCall")
    override fun onBackPressed() {
        val builder1 = AlertDialog.Builder(this)
        builder1.setTitle("Are you sure")
        builder1.setMessage("Do you want to exit")
        builder1.setIcon(R.drawable.baseline_exit_to_app_24)
        builder1.setPositiveButton("Yes",DialogInterface.OnClickListener { dialogInterface, i ->
            finishAffinity() // Closes all activities in the task and exits the app
        })
        builder1.setNegativeButton("No",DialogInterface.OnClickListener { dialogInterface, i ->  })
        builder1.show()
    }

}