package com.hackyeah.app.data

class NetworkState constructor(
    var status: Status? = null,
    var error: Throwable? = null
)

enum class Status {
    LOADING,
    SUCCESS,
    FAILED,
    RESET
}