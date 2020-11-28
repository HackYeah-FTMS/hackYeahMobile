package com.hackyeah.app.data.remote

import com.hackyeah.app.data.Endpoints
import com.hackyeah.app.data.model.Idea
import com.hackyeah.app.data.model.IdeaDetails
import com.hackyeah.app.data.model.Project
import io.reactivex.Single
import okhttp3.MultipartBody
import retrofit2.http.*

interface ApiService {

    @GET(Endpoints.IDEAS_URL)
    fun getIdeas(): Single<List<Idea>>

    @GET(Endpoints.SINGLE_IDEA_URL)
    fun getIdeaDetails(
        @Path("id") id: Int
    ): Single<IdeaDetails>

    @GET(Endpoints.PROJECTS_URL)
    fun getProjects(): Single<List<Project>>

    @Headers("Content-Type:application/json")
    @Multipart
    @POST(Endpoints.PROJECTS_URL)
    fun uploadProject(
        @Part image: MultipartBody.Part,
        @Part jsonDataToSend: MultipartBody.Part
    ): Single<String>

}