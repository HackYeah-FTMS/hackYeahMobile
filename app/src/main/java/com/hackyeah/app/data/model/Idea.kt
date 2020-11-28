package com.hackyeah.app.data.model

data class Idea(
    var id: Int,
    var description: String,
    var image: String,
    var points: Int,
    var creationTimestamp: Long,
    var owner: Owner
)