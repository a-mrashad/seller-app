package com.mazaj.seller.utils

import kotlinx.serialization.json.Json

fun jsonDefaultConfig() = Json { ignoreUnknownKeys = true }
