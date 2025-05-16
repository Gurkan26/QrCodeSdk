package com.senturkgurkan.qrscannersdk

interface QrScanCallback {
    fun onSuccess(result: QrScanResult)
    fun onCancel()
    fun onError(error: Throwable)
}