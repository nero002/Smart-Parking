package com.nero.bookparking.helper

data class UserModel(
    var email: String,
    var name: String?,
    var username: String,
    var image_url: String?,
    var bio: String?,
    var token: String
) {
}