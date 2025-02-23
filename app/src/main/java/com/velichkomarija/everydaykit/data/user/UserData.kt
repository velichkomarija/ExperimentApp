package com.velichkomarija.everydaykit.data.user

import com.velichkomarija.everydaykit.DarkThemeConfig

data class UserData(
    val name: String = "Maria Velichko",
    val vkUrl: String= "https://vk.com/velichkomarija",
    val telegramUrl: String = "https://t.me/velichkomarija",
    val darkThemeConfig: DarkThemeConfig = DarkThemeConfig.FOLLOW_SYSTEM,
)