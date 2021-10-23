package com.example.bookslist.db

import androidx.room.TypeConverter
import com.example.bookslist.data.Review
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class TypeConverter {
    @TypeConverter
    fun fromReviewsList(value: List<Review>): String {
        val gson = Gson()
        val type = object : TypeToken<List<Review>>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toReviewsList(value: String): List<Review> {
        val gson = Gson()
        val type = object : TypeToken<List<Review>>() {}.type
        return gson.fromJson(value, type)
    }

}