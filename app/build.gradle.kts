apply {
  plugin("com.novoda.android-command")
  plugin("com.getkeepsafe.dexcount")
  plugin("com.vanniktech.android.apk.size")
  plugin("org.jlleitschuh.gradle.ktlint")
}

plugins {
  id("com.android.application")
  kotlin("android")
  kotlin("android.extensions")
  kotlin("kapt")
}

android {
  compileSdkVersion(rootProject.extra["compileSdkVersion"] as Int)

  defaultConfig {
    applicationId = "com.burrowsapps.example.gif"
    versionCode = 1
    versionName = "1.0"
    minSdkVersion(rootProject.extra["minSdkVersion"] as Int)
    targetSdkVersion(rootProject.extra["targetSdkVersion"] as Int)

    testApplicationId = "burrows.apps.example.gif.test"
    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    testInstrumentationRunnerArguments = mapOf(
      "disableAnalytics" to "true",
      "clearPackageData" to "true"
    )

    resConfigs("en")
    vectorDrawables.useSupportLibrary = true
  }

  compileOptions {
    setSourceCompatibility(rootProject.extra["javaVersion"])
    setTargetCompatibility(rootProject.extra["javaVersion"])
  }

  dexOptions.preDexLibraries = !(rootProject.extra["ci"] as Boolean)

  sourceSets {
    val commonTest = "src/commonTest/java"
    getByName("androidTest").java.srcDirs(commonTest)
    getByName("test").java.srcDirs(commonTest)
  }

  lintOptions {
    textReport = true
    textOutput("stdout")
    isCheckAllWarnings = true
    isWarningsAsErrors = true
    lintConfig = file("../config/lint/lint.xml")
    isCheckReleaseBuilds = false
    isCheckTestSources = true
  }

  signingConfigs {
    getByName("debug") {
      storeFile = file("../config/signing/debug.keystore")
      storePassword = rootProject.extra["debugKeystorePass"] as String
      keyAlias = rootProject.extra["debugKeystoreUser"] as String
      keyPassword = rootProject.extra["debugKeystorePass"] as String
    }
  }

  buildTypes {
    getByName("debug") {
      applicationIdSuffix = ".debug"

      buildConfigField("String", "BASE_URL", if (rootProject.extra["ci"] as Boolean) "\"http://localhost:8080\"" else "\"https://api.riffsy.com\"")
    }

    // Apply fake signing config to release to test "assembleRelease" locally
    getByName("release") {
      isMinifyEnabled = true
      isShrinkResources = true
      proguardFile(getDefaultProguardFile("proguard-android-optimize.txt"))
      proguardFile(file("config/proguard/proguard-rules.txt"))
      signingConfig = signingConfigs.getByName("debug")

      buildConfigField("String", "BASE_URL", "\"https://api.riffsy.com\"")
    }
  }

  testOptions {
    animationsDisabled = true
    unitTests.apply {
      isReturnDefaultValues = true
      isIncludeAndroidResources = true
    }
    execution = "ANDROIDX_TEST_ORCHESTRATOR"
  }

  // Optimize APK size - remove excess files in the manifest and APK
  packagingOptions {
    exclude("**/*.kotlin_module")
    exclude("**/*.version")
    exclude("**/kotlin/**")
    exclude("**/*.txt")
    exclude("**/*.xml")
    exclude("**/*.properties")
  }
}

dependencies {
  implementation(rootProject.extra["material"] as String)
  implementation(rootProject.extra["constraintLayout"] as String)
  implementation(rootProject.extra["kotlinStdlib"] as String)
  implementation(rootProject.extra["okio"] as String)
  implementation(rootProject.extra["okhttp"] as String)
  implementation(rootProject.extra["loggingInterceptor"] as String)
  implementation(rootProject.extra["adapterRxjava2"] as String)
  implementation(rootProject.extra["moshi"] as String)
  implementation(rootProject.extra["converterMoshi"] as String)
  implementation(rootProject.extra["moshiAdapters"] as String)
  implementation(rootProject.extra["retrofit"] as String)
  implementation(rootProject.extra["rxAndroid"] as String)
  implementation(rootProject.extra["rxJava"] as String)
  implementation(rootProject.extra["glide"] as String)
  implementation(rootProject.extra["okhttp3Integration"] as String)
  implementation(rootProject.extra["dagger"] as String)
  implementation(rootProject.extra["daggerAndroid"] as String)
  implementation(rootProject.extra["daggerAndroidSupport"] as String)

  kapt(rootProject.extra["daggerCompiler"] as String)
  kapt(rootProject.extra["daggerAndroidProcessor"] as String)
  kapt(rootProject.extra["glideCompiler"] as String)

  debugImplementation(rootProject.extra["leakcanaryAndroid"] as String)

  androidTestImplementation(rootProject.extra["junit"] as String)
  androidTestImplementation(rootProject.extra["androidXCore"] as String)
  androidTestImplementation(rootProject.extra["androidXJunit"] as String)
  androidTestImplementation(rootProject.extra["truth"] as String) { exclude(module = "checker-qual") }
  androidTestImplementation(rootProject.extra["runner"] as String)
  androidTestImplementation(rootProject.extra["espressoCore"] as String)
  androidTestImplementation(rootProject.extra["espressoIntents"] as String)
  androidTestImplementation(rootProject.extra["espressoContrib"] as String) { exclude(group = "com.android.support") }
  androidTestImplementation(rootProject.extra["mockwebserver"] as String)

  androidTestUtil(rootProject.extra["orchestrator"] as String)

  testImplementation(rootProject.extra["junit"] as String)
  testImplementation(rootProject.extra["androidXCore"] as String)
  testImplementation(rootProject.extra["androidXJunit"] as String)
  testImplementation(rootProject.extra["truth"] as String) { exclude(module = "checker-qual") }
  testImplementation(rootProject.extra["mockitoKotlin"] as String)
  testImplementation(rootProject.extra["mockitoInline"] as String)
  testImplementation(rootProject.extra["mockwebserver"] as String)
  testImplementation(rootProject.extra["reflections"] as String)
  testImplementation(rootProject.extra["robolectric"] as String)
}

kapt {
  correctErrorTypes = true
  mapDiagnosticLocations = true

  arguments {
    arg("dagger.formatGeneratedSource", "disabled")
    arg("dagger.fastInit", "enabled")
  }

  javacOptions {
    option("-source", "8")
    option("-target", "8")
  }
}
