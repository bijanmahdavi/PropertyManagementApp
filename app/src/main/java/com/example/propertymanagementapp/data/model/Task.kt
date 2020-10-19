package com.example.propertymanagementapp.data.model

data class Task(
    var title: String? = null,
    var description: String? = null,
    var status: Boolean? = false
){
    companion object{
        const val COLLECTION_NAME = "tasks"
    }
}