package com.hackyeah.app.data.model

data class Project(
    var id: Int,
    var ownerId: Int,
    var title: String,
    var ideaImage: String,
    var solutionImage: String,
    var description: String,
    var tags: List<String>,
    var points: Int,
    var creationTimestamp: Long,
)