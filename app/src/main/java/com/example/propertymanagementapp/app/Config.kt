package com.example.propertymanagementapp.app

import com.example.propertymanagementapp.helpers.TokenManager

class Config {
    companion object{
        const val BASE_URL = "https://apolis-property-management.herokuapp.com/api/"
        val ID = TokenManager().getUserId()
    }
}