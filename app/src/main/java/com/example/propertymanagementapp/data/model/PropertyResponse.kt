package com.example.propertymanagementapp.data.model

data class PropertyResponse(
    val count: Int?,
    val data: ArrayList<Property>,
    val error: Boolean
)

data class Property(
    val __v: Int,
    val _id: String,
    val address: String,
    val city: String,
    val country: String,
    val image: String,
    val latitude: String,
    val longitude: String,
    val mortageInfo: Boolean,
    val propertyStatus: Boolean,
    val purchasePrice: String,
    val state: String,
    val userId: String
)