package com.mazaj.seller.repository.networking.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginBody(
    val username: String,
    val password: String,
    @SerialName("device_id")
    val deviceId: String
)

@Serializable
data class ForgetPasswordBody(
    val email: String
)

@Serializable
data class ResetPasswordBody(
    val token: String,
    val email: String,
    val password: String,
    @SerialName("password_confirmation")
    val passwordConfirmation: String
)
