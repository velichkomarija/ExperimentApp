package com.velichkomarija.everydaykit.uicomponents

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.automirrored.outlined.List
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.velichkomarija.everydaykit.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoTopAppBar(
    onBack: () -> Unit,
    onFilterAllTasks: () -> Unit,
    onFilterActiveTasks: () -> Unit,
    onFilterCompletedTasks: () -> Unit,
) {
    TopAppBar(
        title = {
            Text(text = stringResource(id = R.string.todo_list_app))
        },
        navigationIcon = {
            IconButton(onClick = onBack) {
                Icon(Icons.AutoMirrored.Outlined.ArrowBack, stringResource(id = R.string.menu_back))
            }
        },
        actions = {
            FilterTaskMenu(onFilterAllTasks, onFilterActiveTasks, onFilterCompletedTasks)
        },
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
private fun FilterTaskMenu(
    onFilterAllTasks: () -> Unit,
    onFilterActiveTasks: () -> Unit,
    onFilterCompletedTasks: () -> Unit
) {
    TopAppDropdownMenu(iconContent = {
        Icon(
            Icons.AutoMirrored.Outlined.List,
            stringResource(id = R.string.list_filter)
        )
    }) { closeMenu ->
        DropdownMenuItem(
            text = { Text(text = stringResource(id = R.string.nav_all)) },
            onClick = { onFilterAllTasks(); closeMenu() })
        DropdownMenuItem(
            text = { Text(text = stringResource(id = R.string.nav_active)) },
            onClick = { onFilterActiveTasks(); closeMenu() })
        DropdownMenuItem(
            text = { Text(text = stringResource(id = R.string.nav_completed)) },
            onClick = { onFilterCompletedTasks(); closeMenu() })
    }
}

@Composable
private fun TopAppDropdownMenu(
    iconContent: @Composable () -> Unit,
    content: @Composable ColumnScope.(() -> Unit) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box(modifier = Modifier.wrapContentSize(Alignment.TopEnd)) {
        IconButton(onClick = { expanded = !expanded }) {
            iconContent()
        }
        DropdownMenu(
            expanded = expanded, onDismissRequest = { expanded = false },
            modifier = Modifier.wrapContentSize(Alignment.TopEnd)
        ) {
            content { expanded = !expanded }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddOrUpdateTopAppBar(@StringRes title: Int?, onBack: () -> Unit) {
    TopAppBar(
        title = {
            if (title != null) {
                Text(text = stringResource(title))
            } else {
                Text(text = "")
            }
        },
        navigationIcon = {
            IconButton(onClick = onBack) {
                Icon(Icons.AutoMirrored.Outlined.ArrowBack, stringResource(id = R.string.menu_back))
            }
        },
        modifier = Modifier.fillMaxWidth()
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskDetailTopAppBar(onBack: () -> Unit, onDelete: () -> Unit) {
    TopAppBar(
        title = {
            Text(text = stringResource(id = R.string.task_details))
        },
        navigationIcon = {
            IconButton(onClick = onBack) {
                Icon(Icons.AutoMirrored.Outlined.ArrowBack, stringResource(id = R.string.menu_back))
            }
        },
        actions = {
            IconButton(onClick = onDelete) {
                Icon(Icons.Outlined.Delete, stringResource(id = R.string.menu_delete_task))
            }
        },
        modifier = Modifier.fillMaxWidth()
    )
}