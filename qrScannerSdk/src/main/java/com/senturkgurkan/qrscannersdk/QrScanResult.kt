package com.senturkgurkan.qrscannersdk

data class QrScanResult(
    val content: String,
    val format: String,
    val time: Long
)