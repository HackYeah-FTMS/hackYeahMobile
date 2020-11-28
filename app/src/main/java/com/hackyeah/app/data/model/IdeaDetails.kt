package com.hackyeah.app.data.model

data class IdeaDetails(
    var id: Int,
    var owner: Owner,
    var description: String,
    var image: String,
    var comments: List<Comment>,
    var tags: List<String>,
    var points: Int,
    var creationTimestamp: Long,
)