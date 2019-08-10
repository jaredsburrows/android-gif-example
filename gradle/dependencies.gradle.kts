// common
val androidGradleVersion            = "3.5.0-rc03"
val kotlinVersion                   = "1.3.41"
val daggerVersion                   = "2.24"
val okHttpVersion                   = "4.0.1"
val moshiVersion                    = "1.8.0"
val retrofitVersion                 = "2.6.1"
val espressoVersion                 = "3.2.0"
val glideVersion                    = "4.9.0"

// android plugin
extra["minSdkVersion"]              = 19
extra["targetSdkVersion"]           = 28
extra["compileSdkVersion"]          = 28
extra["javaVersion"]                = "1.8"
extra["debugKeystoreUser"]          = "androiddebugkey"
extra["debugKeystorePass"]          = "android"

// classpath
extra["gradle"]                     = "com.android.tools.build:gradle:$androidGradleVersion"
extra["kotlinGradlePlugin"]         = "org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion"
extra["gradleAndroidCommandPlugin"] = "com.novoda:gradle-android-command-plugin:2.1.0"
extra["buildScanPlugin"]            = "com.gradle:build-scan-plugin:2.4"
extra["dexcountGradlePlugin"]       = "com.getkeepsafe.dexcount:dexcount-gradle-plugin:0.8.6"
extra["gradleAndroidApkSizePlugin"] = "com.vanniktech:gradle-android-apk-size-plugin:0.4.0"
extra["gradleVersionsPlugin"]       = "com.github.ben-manes:gradle-versions-plugin:0.22.0"
extra["ktlintGradle"]               = "org.jlleitschuh.gradle:ktlint-gradle:8.2.0"
extra["ktlint"]                     = "com.pinterest:ktlint:0.33.0"

// implementation
extra["material"]                   = "com.google.android.material:material:1.0.0"
extra["constraintLayout"]           = "androidx.constraintlayout:constraintlayout:1.1.3"
extra["kotlinStdlib"]               = "org.jetbrains.kotlin:kotlin-stdlib:$kotlinVersion"
extra["kotlinReflect"]              = "org.jetbrains.kotlin:kotlin-reflect:$kotlinVersion"
extra["rxAndroid"]                  = "io.reactivex.rxjava2:rxandroid:2.1.1"
extra["rxJava"]                     = "io.reactivex.rxjava2:rxjava:2.2.4"
extra["dagger"]                     = "com.google.dagger:dagger:$daggerVersion"
extra["daggerAndroid"]              = "com.google.dagger:dagger-android:$daggerVersion"
extra["daggerAndroidSupport"]       = "com.google.dagger:dagger-android-support:$daggerVersion"
extra["okio"]                       = "com.squareup.okio:okio:2.1.0"
extra["okhttp"]                     = "com.squareup.okhttp3:okhttp:$okHttpVersion"
extra["loggingInterceptor"]         = "com.squareup.okhttp3:logging-interceptor:$okHttpVersion"
extra["retrofit"]                   = "com.squareup.retrofit2:retrofit:$retrofitVersion"
extra["converterMoshi"]             = "com.squareup.retrofit2:converter-moshi:$retrofitVersion"
extra["moshi"]                      = "com.squareup.moshi:moshi:$moshiVersion"
extra["moshiAdapters"]              = "com.squareup.moshi:moshi-adapters:$moshiVersion"
extra["adapterRxjava2"]             = "com.squareup.retrofit2:adapter-rxjava2:$retrofitVersion"
extra["glide"]                      = "com.github.bumptech.glide:glide:$glideVersion"
extra["okhttp3Integration"]         = "com.github.bumptech.glide:okhttp3-integration:$glideVersion@aar"

// debugImplementation
extra["leakcanaryAndroid"]          = "com.squareup.leakcanary:leakcanary-android:2.0-beta-2"

// kapt
extra["daggerCompiler"]             = "com.google.dagger:dagger-compiler:$daggerVersion"
extra["daggerAndroidProcessor"]     = "com.google.dagger:dagger-android-processor:$daggerVersion"
extra["glideCompiler"]              = "com.github.bumptech.glide:compiler:$glideVersion"

// androidTestImplementation
extra["androidXCore"]               = "androidx.test:core:1.2.0"
extra["androidXJunit"]              = "androidx.test.ext:junit:1.1.1"
extra["espressoCore"]               = "androidx.test.espresso:espresso-core:$espressoVersion"
extra["espressoIntents"]            = "androidx.test.espresso:espresso-intents:$espressoVersion"
extra["espressoContrib"]            = "androidx.test.espresso:espresso-contrib:$espressoVersion"
extra["runner"]                     = "androidx.test:runner:1.2.0"

// androidTestUtil
extra["orchestrator"]               = "androidx.test:orchestrator:1.2.0"

// testImplementation
extra["junit"]                      = "junit:junit:4.13-beta-3"
extra["mockitoInline"]              = "org.mockito:mockito-inline:3.0.0"
extra["mockitoKotlin"]              = "com.nhaarman.mockitokotlin2:mockito-kotlin:2.1.0"
extra["truth"]                      = "com.google.truth:truth:1.0"
extra["mockwebserver"]              = "com.squareup.okhttp3:mockwebserver:$okHttpVersion"
extra["reflections"]                = "org.reflections:reflections:0.9.11"
extra["robolectric"]                = "org.robolectric:robolectric:4.3"
