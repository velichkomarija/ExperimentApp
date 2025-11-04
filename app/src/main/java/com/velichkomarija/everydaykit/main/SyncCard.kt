package com.velichkomarija.everydaykit.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.velichkomarija.everydaykit.R
import com.velichkomarija.everydaykit.ui.theme.Dimens
import com.velichkomarija.everydaykit.ui.theme.LocalExtendedColors
import java.text.DateFormat
import java.util.Date

@Composable
fun SyncStatusCard(
    isLinked: Boolean,
    email: String?,
    isBusy: Boolean,
    lastSync: Long?,
    error: String?,
    onSignIn: () -> Unit,
    onSync: () -> Unit,
    onUnlink: () -> Unit
) {
    Surface(
        tonalElevation = 2.dp,
        shape = RoundedCornerShape(Dimens.LargeSize),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            Modifier.padding(Dimens.LargePadding),
            verticalArrangement = Arrangement.spacedBy(Dimens.StandartPadding)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                StatusDot(
                    colorType = when {
                        isBusy -> StatusDotColor.Pending
                        isLinked -> StatusDotColor.On
                        else -> StatusDotColor.Off
                    }
                )
                Spacer(Modifier.width(Dimens.MediumSize))
                Text(
                    text = when {
                        isBusy -> stringResource(id = R.string.sync_busy)
                        isLinked -> stringResource(id = R.string.sync_on)
                        else -> stringResource(id = R.string.sync_off)
                    },
                    style = MaterialTheme.typography.titleMedium
                )
            }

            val subtitle = when {
                isBusy -> stringResource(id = R.string.sync_wait)
                isLinked && lastSync != null -> {
                    stringResource(id = R.string.account) + ":${email ?: "—"}" +
                            stringResource(id = R.string.last_sync) + ": " +
                            DateFormat.getDateTimeInstance().format(Date(lastSync))
                }

                isLinked -> stringResource(id = R.string.account) + ": ${email ?: "—"}"
                else -> stringResource(id = R.string.sync_apply)
            }
            Text(subtitle, style = MaterialTheme.typography.bodyMedium)

            if (error != null) {
                Text(
                    stringResource(id = R.string.error) + ": $error",
                    color = MaterialTheme.colorScheme.error
                )
            }

            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                if (!isLinked) {
                    Button(
                        onClick = onSignIn,
                        enabled = !isBusy,
                        modifier = Modifier.weight(1f)
                    ) {
                        if (isBusy) {
                            Text(stringResource(id = R.string.entering))
                        } else {
                            Text(stringResource(id = R.string.singin_google))
                        }
                    }
                } else {
                    OutlinedButton(
                        onClick = onSync,
                        enabled = !isBusy,
                        modifier = Modifier.weight(1f)
                    ) {
                        if (isBusy) {
                            Text(stringResource(id = R.string.sync))
                        } else {
                            Text(stringResource(id = R.string.do_sync))
                        }
                    }

                    TextButton(
                        onClick = onUnlink,
                        enabled = !isBusy
                    ) {    Text(stringResource(id = R.string.do_sync_off)) }
                }
            }
        }
    }
}

private enum class StatusDotColor { On, Off, Pending }

@Composable
private fun StatusDot(colorType: StatusDotColor, size: Dp = Dimens.StandartSize) {
    val color = when (colorType) {
        StatusDotColor.On -> LocalExtendedColors.current.success
        StatusDotColor.Off -> LocalExtendedColors.current.warning
        StatusDotColor.Pending -> LocalExtendedColors.current.info
    }
    Box(
        modifier = Modifier
            .size(size)
            .clip(CircleShape)
            .background(color)
    )
}
