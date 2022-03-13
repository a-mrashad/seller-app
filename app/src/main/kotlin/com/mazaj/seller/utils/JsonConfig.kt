package com.mazaj.seller.utils

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration

fun jsonDefaultConfig() = Json(JsonConfiguration.Stable.copy(ignoreUnknownKeys = true, isLenient = true))
