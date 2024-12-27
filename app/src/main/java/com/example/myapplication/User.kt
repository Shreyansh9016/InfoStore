package com.example.myapplication

data class User(
    val username: String,
    val name: String,
    val password: String,
    val email: String,
    val tasks: MutableList<String> = mutableListOf() // List to store user tasks
)

