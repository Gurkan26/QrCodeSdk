package com.senturkgurkan.qrscannersdk

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.zxing.integration.android.IntentIntegrator
import com.journeyapps.barcodescanner.BarcodeCallback
import com.journeyapps.barcodescanner.BarcodeResult
import com.journeyapps.barcodescanner.DecoratedBarcodeView


class QrScannerActivity : AppCompatActivity() {


    private lateinit var barcodeView: DecoratedBarcodeView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qr_scanner)

        findViewById<TextView>(R.id.tvHeader).text = intent.getStringExtra("HEADER_TITLE") ?: ""
        findViewById<TextView>(R.id.tvSubTitle).text = intent.getStringExtra("SUB_TITLE") ?: ""
        findViewById<TextView>(R.id.tvFooter).text = intent.getStringExtra("FOOTER_TITLE") ?: ""

        barcodeView = findViewById(R.id.barcode_scanner)
        barcodeView.setStatusText("")
        barcodeView.decodeContinuous(object : BarcodeCallback {
            override fun barcodeResult(result: BarcodeResult) {
                val returnIntent = Intent()
                returnIntent.putExtra("qr_result", result.text)
                setResult(Activity.RESULT_OK, returnIntent)
                finish()
            }

            override fun possibleResultPoints(resultPoints: List<com.google.zxing.ResultPoint>) {
            }
        })
    }

    override fun onResume() {
        super.onResume()
        barcodeView.resume()
    }

    override fun onPause() {
        super.onPause()
        barcodeView.pause()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null && result.contents != null) {
            val returnIntent = Intent()
            returnIntent.putExtra("qr_result", result.contents)
            setResult(Activity.RESULT_OK, returnIntent)
            finish()
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }
}