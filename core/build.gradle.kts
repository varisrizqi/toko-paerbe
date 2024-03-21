plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp")
    id("kotlin-parcelize")
}

android {
    namespace = "com.tipiz.core"
    compileSdk = 34

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")


        buildConfigField("String", "BASE_URL", "\"http://192.168.1.10:5000\"")
        buildConfigField("String", "API_KEY", "\"6f8856ed-9189-488f-9011-0ff4b6c08edc\"")
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        buildConfig = true
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    //Retrofit
    api("com.squareup.retrofit2:retrofit:2.9.0")
    api("com.squareup.retrofit2:converter-gson:2.9.0")
    api("com.squareup.okhttp3:okhttp:4.12.0")
    api("com.squareup.okhttp3:logging-interceptor:4.12.0")

    //Koin
    api("io.insert-koin:koin-android:3.5.0")
    api("io.insert-koin:koin-core:3.5.0")

    //coroutine support
    api("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
    api("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")

    // ktx lifecycle
    api("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")

    // chucker
    debugImplementation("com.github.chuckerteam.chucker:library:4.0.0")
    releaseImplementation("com.github.chuckerteam.chucker:library-no-op:4.0.0")

    //room
    api("androidx.room:room-runtime:2.6.1")
    api("androidx.room:room-ktx:2.6.1")
    api("androidx.room:room-paging:2.6.1")
    ksp("androidx.room:room-compiler:2.6.1")

    //paging
    api("androidx.paging:paging-runtime-ktx:3.2.1")

    //Datastore
    api("androidx.datastore:datastore-preferences:1.0.0")




}