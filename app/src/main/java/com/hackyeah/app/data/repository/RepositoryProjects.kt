package com.hackyeah.app.data.repository

import com.hackyeah.app.data.remote.ApiService
import javax.inject.Inject

class RepositoryProjects @Inject constructor(
    var apiService: ApiService
) {

    fun getProjects() = apiService.getProjects()

}