package com.hackyeah.app.data.remote

import com.hackyeah.app.data.Endpoints
import com.hackyeah.app.data.model.Project
import io.reactivex.Single
import retrofit2.http.GET

interface ApiService {

    @GET(Endpoints.IDEAS_URL)
    fun getIdeas(): Single<Unit>

    @GET(Endpoints.PROJECTS_URL)
    fun getProjects(): Single<List<Project>>

}