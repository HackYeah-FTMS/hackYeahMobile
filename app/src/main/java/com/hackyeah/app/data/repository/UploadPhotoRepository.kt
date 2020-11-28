package com.hackyeah.app.data.repository

import com.google.gson.Gson
import com.hackyeah.app.data.remote.ApiService
import io.reactivex.Single
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import javax.inject.Inject

class UploadPhotoRepository @Inject constructor(
    var apiService: ApiService
) {
//    fun uploadFile(uri: String): Single<String>? {
//
//        val fileToUpload = File(uri)
//        val requestFile: RequestBody = RequestBody.create(
//            MediaType.parse("multipart/form-data"),
//            fileToUpload
//        )
//        val fileBody = MultipartBody.Part.createFormData(
//            "upload",
//            fileToUpload.name,
//            requestFile
//        )
//
//        val jsonDataToSend = JsonDataToSend(
//            Data(
//                name = fileToUpload.name
//            )
//        )
//
//        val jsonRequestBody = MultipartBody.Part.createFormData(
//            "data",
//            Gson().toJson(jsonDataToSend)
//        )
//
//        return apiService
//            .uploadFile(
//            fileBody,
//            jsonRequestBody
//        )
//    }
}