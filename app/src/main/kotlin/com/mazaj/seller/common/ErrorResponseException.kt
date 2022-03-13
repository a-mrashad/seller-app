package com.mazaj.seller.common

import java.io.IOException
import okhttp3.Response

class ErrorResponseException(response: Response, parseErrorResponse: (String?) -> (Any)) : IOException() {
    val errorCode = response.code
    val errorMessage = parseErrorResponse(response.body?.string())
}
