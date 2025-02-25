package com.velichkomarija.everydaykit

import android.content.Context
import android.net.Uri
import androidx.annotation.ColorInt
import androidx.browser.customtabs.CustomTabColorSchemeParams
import androidx.browser.customtabs.CustomTabsIntent

object ChromeTabsUtils {
    // todo проверка на наличие chrome tabs
    fun launchCustomChromeTab(context: Context, uri: Uri, @ColorInt toolbarColor: Int) {
        val customTabBarColor = CustomTabColorSchemeParams.Builder()
            .setToolbarColor(toolbarColor).build()
        val customTabsIntent = CustomTabsIntent.Builder()
            .setDefaultColorSchemeParams(customTabBarColor)
            .build()

        customTabsIntent.launchUrl(context, uri)
    }
}