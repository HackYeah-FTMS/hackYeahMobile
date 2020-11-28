package com.hackyeah.app.data.repository

import com.google.gson.Gson
import com.hackyeah.app.data.model.CreateProjectRequest
import com.hackyeah.app.data.remote.ApiService
import io.reactivex.Single
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import javax.inject.Inject


class RepositoryProjects @Inject constructor(
    var apiService: ApiService
) {

    fun getProjects() = apiService.getProjects()

    fun addProjects(
        title: String,
        description: String,
        ideaSolutionFile: File,
        ideaImageFile: File,
        tags: List<String>? = null,
    ): Single<String> {

        val ideaImageMultiPartBody = prepareImageMultipart(
            ideaSolutionFile,
            "ideaImage"
        )

        val ideaSolutionMultiPartBody = prepareImageMultipart(
            ideaImageFile,
            "solutionImage"
        )

        val createProjectRequest = CreateProjectRequest(
            title,
            description,
            tags ?: listOf()
        )

        return apiService
            .uploadProject(
                10000L,
                Gson().toJson(createProjectRequest),
                ideaImageMultiPartBody,
                ideaSolutionMultiPartBody
            )
    }

    private fun prepareImageMultipart(
        imageFile: File,
        multipartName: String
    ): MultipartBody.Part {
        val ideaImageRequestBody: RequestBody = RequestBody.create(
            MediaType.parse("multipart/form-data"),
            imageFile
        )

        return MultipartBody.Part.createFormData(
            multipartName,
            imageFile.name,
            ideaImageRequestBody
        )
    }


}