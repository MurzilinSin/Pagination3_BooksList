package com.example.bookslist.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

class ServerResponse(
    @field:SerializedName("hydra:member")val books: List<BookModel>
    )

@Entity
data class BookModel(
    @field:SerializedName("@context")val context: String? = "",
    @field:SerializedName("@id")@PrimaryKey val id: String = "",
    @field:SerializedName("@type")val type: String = "",
    val isbn: String ,
    val title: String ,
    val description: String ,
    val author: String ,
    val publicationDate: String ,
    val reviews: List<Review>?
)

data class Review(
    val context: String = "",
    val id: String = "",
    val type: String = "",
    val body: String = ""
)
