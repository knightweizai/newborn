import org.jetbrains.kotlin.cli.jvm.main

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
//    id("org.greenrobot.greendao")
}

android {
    namespace = "com.zaozhuang.newborn"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.zaozhuang.newborn"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

        // abiFilters "armeabi", "armeabi-v7a", "arm64-v8a", "x86","x86_64"
        ndk {
            abiFilters.addAll(setOf("armeabi"))
            abiFilters.addAll(setOf("x86"))
            abiFilters.addAll(setOf("x86_64"))
            abiFilters.addAll(setOf("arm64-v8a"))
            abiFilters.addAll(setOf("armeabi-v7a"))
        }
    }

    buildTypes {
        debug {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        release {
            isMinifyEnabled = true//todo wei 这个是干什么用的
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
        compose = true
        buildConfig = true
        aidl = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    sourceSets["main"].apply {
        java.srcDir("src/main/java")
        java.srcDir("src/main/aidl")
    }
//    sourceSets {
//
//
//        main {
//            kotlin.srcDirs("src/main/kotlin")
//            resources.srcDirs("src/main/resources")
//        }
//    }

//    sourceSets {
//        main {
//            manifest.srcFile 'src/main/AndroidManifest.xml'
//            java.srcDirs = ['src/main/java', 'src/main/aidl']
//            resources.srcDirs = ['src/main/java', 'src/main/aidl']
//            aidl.srcDirs = ['src/main/aidl']
//            res.srcDirs = ['src/main/res']
//            assets.srcDirs = ['src/main/assets']
//        }
//    }

//    greendao {
//        schemaVersion 1
//        daoPackage 'com.ctb.opencar.db.greendao'
//        targetGenDir 'src/main/java'
//    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.1")
    implementation("androidx.activity:activity-compose:1.7.0")
    implementation(platform("androidx.compose:compose-bom:2023.03.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")

    implementation(project(":app_base"))
    implementation("androidx.fragment:fragment:1.6.1")
    implementation("androidx.appcompat:appcompat:1.3.1")
    implementation("androidx.recyclerview:recyclerview:1.2.1")
    implementation("androidx.swiperefreshlayout:swiperefreshlayout:1.1.0")
    //图片加载类
    implementation("com.github.bumptech.glide:glide:4.12.0")
    //图表
    implementation("com.github.PhilJay:MPAndroidChart:v3.1.0")
    //viewpager 指示器
    implementation("com.github.hackware1993:MagicIndicator:1.7.0")
    //数据库
    implementation(files("libs/ormlite-android-6.1.jar"))
    //弹窗
    implementation("com.github.li-xiaojun:XPopup:2.5.15")
    implementation("com.github.li-xiaojun:XPopupExt:1.0.0")
    //相册
    implementation("com.github.HuanTanSheng:EasyPhotos:3.1.5")
    //生日、地址、日期等选择器
    implementation("com.github.gzu-liyujiang.AndroidPicker:Common:4.1.6")
//    implementation("com.github.gzu-liyujiang.AndroidPicker:AddressPicker:4.1.6")
    implementation("com.github.gzu-liyujiang.AndroidPicker:WheelPicker:4.1.6")
//    implementation("com.github.gzu-liyujiang.AndroidPicker:ImagePicker:4.1.6")
    implementation("cn.jzvd:jiaozivideoplayer:7.7.0")


    implementation("com.android.volley:volley:1.1.1")

    implementation("com.github.CarGuo.GSYVideoPlayer:gsyVideoPlayer-java:v8.4.0-release-jitpack")

    //是否需要ExoPlayer模式
    implementation("com.github.CarGuo.GSYVideoPlayer:GSYVideoPlayer-exo2:v8.4.0-release-jitpack")

    //是否需要AliPlayer模式
//    implementation("com.github.CarGuo.GSYVideoPlayer:GSYVideoPlayer-aliplay:v8.4.0-release-jitpack")

    //根据你的需求ijk模式的so
    implementation("com.github.CarGuo.GSYVideoPlayer:gsyVideoPlayer-arm64:v8.4.0-release-jitpack")
    implementation("com.github.CarGuo.GSYVideoPlayer:gsyVideoPlayer-armv7a:v8.4.0-release-jitpack")
//    implementation("com.github.CarGuo.GSYVideoPlayer:gsyVideoPlayer-armv5:v8.4.0-release-jitpack")
//    implementation("com.github.CarGuo.GSYVideoPlayer:gsyVideoPlayer-x86:v8.4.0-release-jitpack")
//    implementation("com.github.CarGuo.GSYVideoPlayer:gsyVideoPlayer-x64:v8.4.0-release-jitpack")
    //手环

    implementation(files("libs/libble-0.5.aar"))
    implementation(files("libs/libcomx-0.5.jar"))
    implementation(files("libs/libdfu-1.5.aar"))
    implementation(files("libs/libfastdfu-0.5.aar"))
    implementation(files("libs/vpbluetooth-release.aar"))
    implementation(files("libs/vpprotocol-2.2.20.15.aar"))
    implementation(files("libs/okhttp-2.6.0.jar"))


    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.03.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")


//    implementation("org.greenrobot:greendao:3.3.0")
//    annotationProcessor("org.greenrobot:greendao-generator:3.3.0")
//    implementation("com.jakewharton:butterknife:10.0.0")
//    annotationProcessor("com.jakewharton:butterknife-compiler:10.0.0")










}