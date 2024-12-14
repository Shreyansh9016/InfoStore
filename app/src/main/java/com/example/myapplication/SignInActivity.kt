package com.example.myapplication

import android.content.DialogInterface
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignInActivity : AppCompatActivity() {

    private lateinit var databaseReference: DatabaseReference

    companion object{
        const val KEY1 ="com.example.myapplication.SignInActivity.mail"
        const val KEY2 ="com.example.myapplication.SignInActivity.name"
        const val KEY3 ="com.example.myapplication.SignInActivity.username"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sign_in)

        val signInButton = findViewById<Button>(R.id.signbtn)
        val username = findViewById<TextInputEditText>(R.id.usernamee)
        val checkbox = findViewById<CheckBox>(R.id.checkbtn)
        val select = findViewById<Button>(R.id.select)
        val selectagain = findViewById<Button>(R.id.selectagain)
        select.setOnClickListener {
            val options= arrayOf("Student","Teacher","Others")
            val builder2=AlertDialog.Builder(this)
            builder2.setTitle("Select Type")
            builder2.setSingleChoiceItems(options,0,DialogInterface.OnClickListener { dialog, which ->
                Toast.makeText(this,"You Clicked on ${options[which]}",Toast.LENGTH_SHORT).show()
            })
            builder2.setPositiveButton("Submit",DialogInterface.OnClickListener { dialogInterface, i ->

            })
            builder2.setNegativeButton("Decline",DialogInterface.OnClickListener { dialogInterface, i ->

            })
            builder2.show()
        }
        selectagain.setOnClickListener {
            val options= arrayOf("Rasmalai","Laddu","Others")
            val builder2=AlertDialog.Builder(this)
            builder2.setTitle("Select Type")
            builder2.setMultiChoiceItems(options,null,DialogInterface.OnMultiChoiceClickListener { dialog, which ,isChecked->
                Toast.makeText(this,"You Clicked on ${options[which]}",Toast.LENGTH_SHORT).show()
            })
            builder2.setPositiveButton("Submit",DialogInterface.OnClickListener { dialogInterface, i ->

            })
            builder2.setNegativeButton("Decline",DialogInterface.OnClickListener { dialogInterface, i ->

            })
            builder2.show()
        }

        signInButton.setOnClickListener {
            val uniqueID = username.text.toString()

            if(uniqueID.isNotEmpty() && checkbox.isChecked)
            {

                readData(uniqueID)

            }
            else if(uniqueID.isEmpty())
            {
                Toast.makeText(this,"Please Enter username",Toast.LENGTH_SHORT).show()
            }
            else
            {
                checkbox.buttonTintList = ColorStateList.valueOf(Color.RED)
                Toast.makeText(this, "Please accept T&C", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun readData(uniqueID: String) {
        databaseReference= FirebaseDatabase.getInstance().getReference("Users")
        val checkbox = findViewById<CheckBox>(R.id.checkbtn)

        databaseReference.child(uniqueID).get().addOnSuccessListener {
            clearFields()
            if(it.exists())
            {
                val email=it.child("email").value
                val name=it.child("name").value
                val userid=it.child("username").value

                val intentwelcome = Intent(this, HomeActivity::class.java)
                intentwelcome.putExtra(KEY1,email.toString())
                intentwelcome.putExtra(KEY2,name.toString())
                intentwelcome.putExtra(KEY3,userid.toString())
                startActivity(intentwelcome)
                checkbox.isChecked = false


            }
            else{
                Toast.makeText(this,"User not Found",Toast.LENGTH_SHORT).show()
            }

        }.addOnFailureListener {
            Toast.makeText(this,"Its not U its us",Toast.LENGTH_SHORT).show()
        }
    }
    private fun clearFields() {
        val username = findViewById<TextInputEditText>(R.id.usernamee)
        username.text?.clear()
    }
}