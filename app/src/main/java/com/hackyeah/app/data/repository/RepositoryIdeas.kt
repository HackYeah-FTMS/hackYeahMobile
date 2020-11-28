package com.hackyeah.app.data.repository

import com.hackyeah.app.data.remote.ApiService
import javax.inject.Inject

class RepositoryIdeas @Inject constructor(
    var apiService: ApiService
) {

    fun getIdeas() = apiService.getIdeas()

    fun getIdeaDetails(ideaId:Int) = apiService.getIdeaDetails(ideaId)

}