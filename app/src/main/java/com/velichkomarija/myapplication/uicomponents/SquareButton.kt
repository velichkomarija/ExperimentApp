package com.velichkomarija.myapplication.uicomponents

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.velichkomarija.myapplication.R

@Composable
fun SquareButton(
    onClick: () -> Unit,
    @DrawableRes icon: Int,
    @StringRes description: Int
) {
    Box(modifier = Modifier.size(56.dp).padding(4.dp)) {
        Button(
            onClick = { onClick() },
            contentPadding = PaddingValues(0.dp),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.background(Color.Unspecified),
        ) {
            Icon(
                painter = painterResource(id = icon),
                contentDescription = stringResource(description),
                modifier = Modifier.size(48.dp).background(Color.Unspecified),
                tint = Color.Unspecified
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SquareButtonPreview() {
    SquareButton(
        onClick = { /*todo*/ },
        icon = R.drawable.vk_logo,
        description = R.string.vk_link_description
    )
}