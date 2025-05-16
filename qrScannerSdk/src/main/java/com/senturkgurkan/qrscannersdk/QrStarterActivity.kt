package com.senturkgurkan.qrscannersdk

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.zxing.BinaryBitmap
import com.google.zxing.DecodeHintType
import com.google.zxing.MultiFormatReader
import com.google.zxing.RGBLuminanceSource
import com.google.zxing.common.HybridBinarizer

class QrStarterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        showOptionDialog()
    }

    private fun showOptionDialog() {
        val title = QrCodeSdk.dialogTitle ?: "Choose Input"
        val cameraText = QrCodeSdk.cameraOptionText ?: "📷 Use Camera"
        val galleryText = QrCodeSdk.galleryOptionText ?: "🖼️ Choose Image"

        AlertDialog.Builder(this)
            .setTitle("QR Kod Kaynağını Seçin")
            .setItems(arrayOf("📷 Kamera ile Tara", "🖼️ Galeriden Seç")) { _, which ->
                when (which) {
                    0 -> openCameraScanner()
                    1 -> openGalleryWithPermissionCheck()
                }
            }
            .setCancelable(true)
            .show()
    }

    private fun openCameraScanner() {
        val intent = Intent(this, QrScannerActivity::class.java).apply {

            putExtra("HEADER_TITLE", intent.getStringExtra("HEADER_TITLE"))
            putExtra("SUB_TITLE", intent.getStringExtra("SUB_TITLE"))
            putExtra("FOOTER_TITLE", intent.getStringExtra("FOOTER_TITLE"))
        }
        startActivityForResult(intent, 1001)
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI).apply {
            type = "image/*"
            putExtra(Intent.EXTRA_LOCAL_ONLY, true)
        }
        startActivityForResult(intent, 1002)
    }
    private fun openGalleryWithPermissionCheck() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_IMAGES)
                != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.READ_MEDIA_IMAGES),
                    2001
                )
                return
            }
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                    2001
                )
                return
            }
        }

        openGallery()
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 1001 && resultCode == RESULT_OK) {
            val result = data?.getStringExtra("qr_result")
            result?.let {
                QrCodeSdk.getCallback()?.onSuccess(
                    QrScanResult(
                        content = it,
                        format = "QR_CODE",
                        time = System.currentTimeMillis()
                    )
                )
            }
            finish()
        }

        if (requestCode == 1002 && resultCode == RESULT_OK) {
            val uri = data?.data
            if (uri == null) {
                QrCodeSdk.getCallback()?.onError(Throwable("QR code not found"))
                finish()
                return
            }
            val bitmap = try {
                val inputStream = contentResolver.openInputStream(uri)
                BitmapFactory.decodeStream(inputStream).also {
                    inputStream?.close()
                }
            } catch (e: Exception) {
                e.printStackTrace()
                QrCodeSdk.getCallback()?.onError(Throwable("QR code not found"))
                finish()
                return
            }

            val result = scanQrFromBitmap(bitmap)

            if (result != null) {
                QrCodeSdk.getCallback()?.onSuccess(
                    QrScanResult(
                        content = result,
                        format = "QR_CODE",
                        time = System.currentTimeMillis()
                    )
                )
            } else {
                QrCodeSdk.getCallback()?.onError(Throwable("QR code not found"))
            }
            finish()
        }

        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun scanQrFromBitmap(bitmap: Bitmap): String? {
        val intArray = IntArray(bitmap.width * bitmap.height)
        bitmap.getPixels(intArray, 0, bitmap.width, 0, 0, bitmap.width, bitmap.height)

        val source = RGBLuminanceSource(bitmap.width, bitmap.height, intArray)
        val binaryBitmap = BinaryBitmap(HybridBinarizer(source))
        val reader = MultiFormatReader().apply {
            setHints(mapOf(
                DecodeHintType.TRY_HARDER to true,
                DecodeHintType.POSSIBLE_FORMATS to QrCodeSdk.getSupportedFormats()
            ))
        }

        return try {
            reader.decode(binaryBitmap).text
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}