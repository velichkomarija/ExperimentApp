package com.velichkomarija.everydaykit.uicomponents

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun RoundImage(
    @DrawableRes image: Int,
    @StringRes contentDescription: Int,
    rainbowColorsBrush: Brush,
    borderWidth: Dp,
) {
    val imageModifier = Modifier
        .size(150.dp)
        .background(Color.Transparent)
        .border(
            BorderStroke(borderWidth, rainbowColorsBrush),
            CircleShape
        )
        .padding(borderWidth)
        .clip(CircleShape)
    Image(
        painter = painterResource(id = image),
        contentDescription = stringResource(contentDescription),
        contentScale = ContentScale.Fit,
        modifier = imageModifier
    )
}
