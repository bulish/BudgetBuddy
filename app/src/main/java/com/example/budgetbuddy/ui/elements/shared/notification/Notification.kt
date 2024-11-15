package com.example.budgetbuddy.ui.elements.shared.notification

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.example.budgetbuddy.model.NotificationData
import com.example.budgetbuddy.ui.theme.Black
import kotlinx.coroutines.launch

@Composable
fun Notification(data: NotificationData?) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 96.dp),
        contentAlignment = Alignment.BottomCenter
    ) {
        if (data != null && data.show) {
            val type = if (data.isSuccess) NotificationType.SUCCESS else NotificationType.ERROR

            val snackState = remember { SnackbarHostState() }
            val snackScope = rememberCoroutineScope()
            val message = stringResource(id = data.message)

            SnackbarHost(
                modifier = Modifier,
                hostState = snackState
            ) {
                Snackbar(
                    snackbarData = it,
                    containerColor = type.backgroundColor,
                    contentColor = Black,
                    actionColor = type.borderColor,
                    actionContentColor = type.borderColor,
                )
            }

            LaunchedEffect(Unit) {
                snackScope.launch { snackState.showSnackbar(message) }
            }
        }
    }
}
