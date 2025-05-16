package com.senturkgurkan.qrscannersdk

import android.app.Activity
import android.content.Intent
import com.google.zxing.BarcodeFormat

object QrCodeSdk {

    private const val REQUEST_CODE = 1024
    private var qrScanCallback: QrScanCallback? = null

    var headerTitle: String? = null
    var subTitle: String? = null
    var footerTitle: String? = null
    internal fun getCallback(): QrScanCallback? = qrScanCallback
    private var scanFilter: ScanFilterType = ScanFilterType.ONLY_QR
    private var isMockMode: Boolean = false
    private var mockResult: QrScanResult? = null
    var dialogTitle: String? = null
    var cameraOptionText: String? = null
    var galleryOptionText: String? = null


    fun setCodeFilter(filter: ScanFilterType) {
        scanFilter = filter
    }

    internal fun getSupportedFormats(): List<BarcodeFormat> { // If you want  different format, use this method.
        return when (scanFilter) {
            ScanFilterType.ONLY_QR -> listOf(BarcodeFormat.QR_CODE)
            ScanFilterType.ONLY_BARCODE -> listOf(
                BarcodeFormat.CODE_128,
                BarcodeFormat.CODE_39,
                BarcodeFormat.EAN_13,
                BarcodeFormat.EAN_8,
                BarcodeFormat.UPC_A,
                BarcodeFormat.UPC_E,
                BarcodeFormat.ITF
            )
            ScanFilterType.ALL -> listOf(
                BarcodeFormat.QR_CODE,
                BarcodeFormat.CODE_128,
                BarcodeFormat.CODE_39,
                BarcodeFormat.EAN_13,
                BarcodeFormat.EAN_8,
                BarcodeFormat.UPC_A,
                BarcodeFormat.UPC_E,
                BarcodeFormat.ITF,
                BarcodeFormat.PDF_417,
                BarcodeFormat.AZTEC,
                BarcodeFormat.DATA_MATRIX
            )
        }
    }
    fun enableMockMode(enable: Boolean) {
        isMockMode = enable
    }

    fun setMockResult(result: QrScanResult) {
        mockResult = result
    }

    fun startScanner(activity: Activity, resultCallback: QrScanCallback) {
        if (isMockMode && mockResult != null) {
            resultCallback.onSuccess(mockResult!!)
            return
        }

        qrScanCallback = resultCallback
        val intent = Intent(activity, QrStarterActivity::class.java).apply {
            putExtra("HEADER_TITLE", headerTitle)
            putExtra("SUB_TITLE", subTitle)
            putExtra("FOOTER_TITLE", footerTitle)
        }
        activity.startActivityForResult(intent, REQUEST_CODE)
    }


}