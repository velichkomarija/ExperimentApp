package com.velichkomarija.myapplication.uicomponents

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.velichkomarija.myapplication.ui.theme.MyApplicationTheme
import com.velichkomarija.myapplication.ui.theme.Typography

@Composable
fun ItemButton(
    title: String,
    description: String,
    onClick: () -> Unit,
) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier.padding(start = 8.dp, end = 8.dp, top = 8.dp)
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = title,
                style = Typography.bodyLarge,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                modifier = Modifier.padding(top = 4.dp),
                text = description,
                style = Typography.bodySmall,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
            )
        }
    }
}


@Preview
@Composable
private fun ImageButtonPreview() {
    MyApplicationTheme {
        Surface {
            ItemButton(
                title = "Пример",
                description = "Очень очень очень очень очень очень очень " +
                        "очень очень очень очень очень очень длинное описание",
                onClick = { }
            )
        }
    }
}