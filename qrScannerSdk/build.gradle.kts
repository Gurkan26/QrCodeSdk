plugins {
    id("com.android.library") version "8.2.2" // Android plugin'in doğru versiyonunu belirt
    id("org.jetbrains.kotlin.android") version "1.9.22" // Kotlin plugin versiyonu
    id("maven-publish")
}


publishing {
    publications {
        create<MavenPublication>("release") {
            groupId = "com.senturkgurkan"
            artifactId = "qrScannerSdk"
            version = "1.0.8"

            afterEvaluate {
                from(components["release"])  
            }
        }
    }
}

android {
    namespace = "com.senturkgurkan.qrscannersdk"
    compileSdk = 34

    defaultConfig {
        minSdk = 24
        targetSdk = 34
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }
    publishing {
        singleVariant("release")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = "11"
    }

    lint {
        checkReleaseBuilds = false
        abortOnError = false
        xmlReport = false
        htmlReport = false
    }
}

dependencies {
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    implementation("com.journeyapps:zxing-android-embedded:4.3.0")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}

