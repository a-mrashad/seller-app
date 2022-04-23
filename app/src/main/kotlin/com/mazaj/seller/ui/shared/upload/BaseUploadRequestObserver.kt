package com.mazaj.seller.ui.shared.upload

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.mazaj.seller.R
import com.mazaj.seller.extensions.throwIf
import com.mazaj.seller.repository.networking.models.ErrorBody
import com.mazaj.seller.utils.jsonDefaultConfig
import java.io.IOException
import kotlinx.serialization.SerializationException
import net.gotev.uploadservice.data.UploadInfo
import net.gotev.uploadservice.exceptions.UploadError
import net.gotev.uploadservice.exceptions.UserCancelledUploadException
import net.gotev.uploadservice.network.ServerResponse
import net.gotev.uploadservice.observer.request.RequestObserverDelegate

interface BaseUploadRequestObserver : RequestObserverDelegate {
    val onUploadFailed: (errorBody: ErrorBody?, messageResId: Int?, errorCode: Int) -> (Unit)
    val progressLiveData: MutableLiveData<Int>

    override fun onCompleted(context: Context, uploadInfo: UploadInfo) {
    }

    override fun onCompletedWhileNotObserving() {
    }

    override fun onProgress(context: Context, uploadInfo: UploadInfo) {
        progressLiveData.value = uploadInfo.progressPercent
    }

    override fun onSuccess(context: Context, uploadInfo: UploadInfo, serverResponse: ServerResponse) {
    }

    override fun onError(context: Context, uploadInfo: UploadInfo, exception: Throwable) {
        if (exception is UserCancelledUploadException) {
            onUploadFailed(null, null, -1)
            return
        }
        exception.throwIf { exception !is UploadError && exception !is IOException }

        val error = (exception as? UploadError)?.serverResponse?.bodyString
        val errorBody: ErrorBody? = try {
            error?.let { jsonDefaultConfig().decodeFromString(ErrorBody.serializer(), it) }
        } catch (serializationException: SerializationException) {
            ErrorBody()
        }

        var messageResId: Int? = null

        when (exception) {
            is IOException -> messageResId = R.string.network_error
        }

        onUploadFailed(errorBody, messageResId, (exception as? UploadError)?.serverResponse?.code ?: -1)
    }
}
