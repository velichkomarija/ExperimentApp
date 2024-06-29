package com.velichkomarija.myapplication.data

import com.velichkomarija.myapplication.DarkThemeConfig

data class UserData(
    val name: String = "Maria Velichko",
    val vkUrl: String= "https://vk.com/velichkomarija",
    val telegramUrl: String = "https://t.me/velichkomarija",
    val darkThemeConfig: DarkThemeConfig = DarkThemeConfig.FOLLOW_SYSTEM,
)