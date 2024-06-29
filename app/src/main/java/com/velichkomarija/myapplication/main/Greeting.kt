package com.velichkomarija.myapplication.main

import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.velichkomarija.myapplication.ChromeTabsUtils
import com.velichkomarija.myapplication.R
import com.velichkomarija.myapplication.data.UserData
import com.velichkomarija.myapplication.uicomponents.Paragraph
import com.velichkomarija.myapplication.uicomponents.RoundImage
import com.velichkomarija.myapplication.uicomponents.SquareButton

@Composable
fun Greeting(
    modifier: Modifier = Modifier, userData: UserData
) {
    val context = LocalContext.current
    val backgroundColor = MaterialTheme.colorScheme.background.toArgb()
    val rainbowColorsBrush = remember {
        Brush.sweepGradient(
            listOf(
                Color(0xFF9575CD),
                Color(0xFFBA68C8),
                Color(0xFFE57373),
                Color(0xFFFFB74D),
                Color(0xFFFFF176),
                Color(0xFFAED581),
                Color(0xFF4DD0E1),
                Color(0xFF9575CD)
            )
        )
    }
    Column(
        modifier = Modifier
            .padding(20.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        RoundImage(
            image = R.drawable.personal,
            contentDescription = R.string.photo_description,
            rainbowColorsBrush,
            4.dp
        )

        Spacer(modifier = Modifier.height(8.dp))

        Paragraph(
            text = R.string.personal_name
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier.wrapContentWidth()
        ) {
            SquareButton(
                {
                    ChromeTabsUtils.launchCustomChromeTab(
                        context, Uri.parse(userData.vkUrl), backgroundColor
                    )
                }, R.drawable.vk_logo, R.string.vk_link_description
            )
            SquareButton(
                {
                    ChromeTabsUtils.launchCustomChromeTab(
                        context, Uri.parse(userData.telegramUrl), backgroundColor
                    )
                }, R.drawable.telegram, R.string.telegram_link_description
            )
        }
    }
}