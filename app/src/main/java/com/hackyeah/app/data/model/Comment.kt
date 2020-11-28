package com.hackyeah.app.data.model

data class Comment(
    var id: Int,
    var owner: Owner,
    var image: String,
    var content: String,
)