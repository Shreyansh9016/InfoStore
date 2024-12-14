package com.example.myapplication

import android.content.DialogInterface
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.databinding.ActivityMainBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btn.setOnClickListener {
            val name = binding.name.text.toString()
            val username = binding.username.text.toString()
            val password = binding.password.text.toString()
            val email = binding.email.text.toString()
            val check = binding.checkbtn


            if (name.isNotEmpty() && username.isNotEmpty() && password.isNotEmpty() && email.isNotEmpty()) {
                if (check.isChecked) {
                    val builder1 = AlertDialog.Builder(this)
                    builder1.setTitle("Are you sure")
                    builder1.setMessage("Do you want to Continue")
                    builder1.setIcon(R.drawable.baseline_double_arrow_24)
                    builder1.setPositiveButton("Yes",DialogInterface.OnClickListener { dialogInterface, i ->

                    val user = Users(username, name, password, email)
                    database = FirebaseDatabase.getInstance().getReference("Users")
                    database.child(username).setValue(user).addOnSuccessListener {
                        clearFields()
                        binding.checkbtn.isChecked = false
                        Toast.makeText(this, "User Registered", Toast.LENGTH_SHORT).show()
                    }.addOnFailureListener {
                        Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()
                    }})
                    builder1.setNegativeButton("No",DialogInterface.OnClickListener { dialogInterface, i ->  })
                    builder1.show()
                } else {
                    binding.checkbtn.buttonTintList = ColorStateList.valueOf(Color.RED)
                    Toast.makeText(this, "Please accept T&C", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Enter all the details required", Toast.LENGTH_SHORT).show()
            }
        }

        binding.signbtn.setOnClickListener {
            val opensignin = Intent(this, SignInActivity::class.java)
            startActivity(opensignin)
        }
    }

    private fun clearFields() {
        binding.name.text?.clear()
        binding.username.text?.clear()
        binding.password.text?.clear()
        binding.email.text?.clear()
    }
}
