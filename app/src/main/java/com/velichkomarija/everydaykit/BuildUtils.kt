package com.velichkomarija.everydaykit

@Suppress("KotlinConstantConditions")
object BuildUtils {
    fun isDebug(): Boolean {
        return BuildConfig.BUILD_TYPE == "debug"
    }

    fun isRelease(): Boolean {
        return BuildConfig.BUILD_TYPE == "release"
    }

}