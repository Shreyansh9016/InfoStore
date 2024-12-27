package com.example.myapplication

import TaskAdapter
import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.SignInActivity
import com.google.firebase.database.*

class HomeActivity : AppCompatActivity() {

    private val tasks = mutableListOf<String>() // Initialize the mutable list
    private lateinit var adapter: TaskAdapter
    private lateinit var database: FirebaseDatabase
    private lateinit var tasksRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        // Get user details passed from the SignInActivity
        val name = intent.getStringExtra(SignInActivity.KEY2)?: ""
        val username = intent.getStringExtra(SignInActivity.KEY3)

        val welcomesTex = findViewById<TextView>(R.id.tvWelcome)
        val fname = name.split(" ")[0]
        welcomesTex.text = "Welcome $fname,"

        val etTask = findViewById<EditText>(R.id.etTask)
        val btnAddTask = findViewById<Button>(R.id.btnAddTask)
        val rvTasks = findViewById<RecyclerView>(R.id.rvTasks)

        // Initialize Firebase
        database = FirebaseDatabase.getInstance()
        tasksRef = database.getReference("Users").child(username ?: "").child("tasks")
        // Use username as the key for tasks

        // Initialize RecyclerView and Adapter
        adapter = TaskAdapter(tasks)
        rvTasks.layoutManager = LinearLayoutManager(this)
        rvTasks.adapter = adapter

        // Load tasks from Firebase when the activity starts
        loadTasksFromDatabase()

        // Button to add new task
        btnAddTask.setOnClickListener {
            val task = etTask.text.toString()
            if (task.isNotEmpty()) {
                // Add task to the local list and update RecyclerView
                tasks.add(task)
                adapter.notifyItemInserted(tasks.size - 1)

                // Save task to Firebase
                saveTaskToDatabase(task)

                // Clear the EditText field
                etTask.text.clear()
            }
        }
    }

    // Function to load tasks from Firebase
    private fun loadTasksFromDatabase() {
        tasksRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                tasks.clear()
                println("Snapshot contains ${snapshot.childrenCount} tasks.")
                for (taskSnapshot in snapshot.children) {
                    println("Task found: ${taskSnapshot.key} -> ${taskSnapshot.value}")
                    val task = taskSnapshot.getValue(String::class.java)
                    task?.let {
                        tasks.add(it)
                        println("Task added to list: $it")
                    }
                }
                runOnUiThread {
                    adapter.notifyDataSetChanged()
                    println("Adapter notified with tasks: $tasks")
                }
            }

            override fun onCancelled(error: DatabaseError) {
                println("Failed to load tasks: ${error.message}")
            }
        })
    }



    // Function to save task to Firebase
//    private fun saveTaskToDatabase(task: String) {
//        val taskId = tasksRef.push().key
//        if (taskId != null) {
//            tasksRef.child(taskId).setValue(task)
//                .addOnSuccessListener {
//                    Toast.makeText(this, "Task added successfully!", Toast.LENGTH_SHORT).show()
//                    println("Task added: $task")
//                    println("Tasklist: $tasks")
//                }
//                .addOnFailureListener { e ->
//                    Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
//                }
//        } else {
//            Toast.makeText(this, "Failed to generate task ID.", Toast.LENGTH_SHORT).show()
//        }
//    }
    private fun saveTaskToDatabase(task: String) {
        val taskId = tasksRef.push().key // Generate unique key
        if (taskId != null) {
            tasksRef.child(taskId).setValue(task)
                .addOnSuccessListener {
                    println("Task added successfully at path: ${tasksRef.child(taskId).toString()}")
                }
                .addOnFailureListener { e ->
                    println("Error saving task: ${e.message}")
                }
        }
    }



    @SuppressLint("MissingSuperCall")
    override fun onBackPressed() {
        val builder1 = AlertDialog.Builder(this)
        builder1.setTitle("Are you sure")
        builder1.setMessage("Do you want to exit?")
        builder1.setIcon(R.drawable.baseline_exit_to_app_24)
        builder1.setPositiveButton("Yes") { _, _ ->
            finishAffinity() // Closes all activities in the task and exits the app
        }
        builder1.setNegativeButton("No", null)
        builder1.show()
    }
}
