# QrCodeSdk 📷

[![license](https://img.shields.io/badge/license-MIT-green.svg)](https://github.com/Gurkan26/QrCodeSdk/blob/main/LICENSE)
[![platform](https://img.shields.io/badge/platform-android-green.svg)](https://developer.android.com)
[![JitPack](https://img.shields.io/jitpack/v/github/Gurkan26/QrCodeSdk?style=flat-square)](https://jitpack.io/#Gurkan26/QrCodeSdk)

---

## 📌 About the library

**QrCodeSdk** is a lightweight and customizable Android SDK that allows you to:

- Scan QR codes via **Camera**
- Select and decode QR/barcodes from **Gallery images**
- Customize titles, messages, and filters
- Integrate with just a few lines of code

> Ideal for developers who want to add QR functionality in seconds without extra bloat!


---

## 📦 Gradle Setup

### Step 1 – Add JitPack in your **root** `settings.gradle` file:

```groovy
dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        maven { url 'https://jitpack.io' }
 
   }
}
```
Step 2 – Add the dependency in your app module build.gradle file:

```groovy
 dependencies {
    implementation("com.github.Gurkan26:QrCodeSdk:1.1.2")
}
```



🚀 Quick Start



Initialize and Start Scanner

```kotlin
// Optional: Set UI labels
QrCodeSdk.headerTitle = "Welcome"
QrCodeSdk.subTitle = "Align QR Code"
QrCodeSdk.footerTitle = "It will scan automatically"

// Optional: Filter to only QR codes
QrCodeSdk.setCodeFilter(ScanFilterType.ONLY_QR)

// Optional: Customize dialog options
QrCodeSdk.dialogTitle = "Choose Source"
QrCodeSdk.cameraOptionText = "📷 Scan with Camera"
QrCodeSdk.galleryOptionText = "🖼️ Pick from Gallery"

// Start scanner
QrCodeSdk.startScanner(this, object : QrScanCallback {
    override fun onSuccess(result: QrScanResult) {
        Log.d("RESULT", result.content)
    }

    override fun onCancel() {
        // Handle cancel
    }

    override fun onError(error: Throwable) {
        // Handle error
    }
})
```

🛠️ Custom Dialog Texts

You can let users customize the dialog titles like this:
```
Parameter              Default Value
dialogTitle            “Select QR Code Source”
cameraOptionText       “📷 Scan with Camera”
galleryOptionText      “🖼️ Pick from Gallery”
```
Example:

```
QrCodeSdk.dialogTitle = "Choose Input Method"
QrCodeSdk.cameraOptionText = "Use Camera"
QrCodeSdk.galleryOptionText = "Select from Gallery"
```

🧑‍💻 Requirements

	•	Android 5.0 (API 21) or above

	•	Camera permission required

	•	Optional: Storage permission for gallery




👨‍💻 Author

Gürkan Şentürk – @Gurkan26
