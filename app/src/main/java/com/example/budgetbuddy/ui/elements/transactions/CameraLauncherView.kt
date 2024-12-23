package com.example.budgetbuddy.ui.elements.transactions

import androidx.camera.core.ImageAnalysis
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.budgetbuddy.R
import com.example.budgetbuddy.ui.theme.BasicMargin
import com.example.budgetbuddy.ui.theme.HalfMargin
import com.example.budgetbuddy.ui.theme.QuarterMargin
import com.example.budgetbuddy.ui.theme.White

@Composable
fun CameraLauncherView(
    paddingValues: PaddingValues,
    receiptAnalyzer: ImageAnalysis.Analyzer,
    overlay: @Composable () -> Unit
) {
    var showCameraView by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .height(if(showCameraView) 470.dp else 120.dp),
        contentAlignment = Alignment.Center
    ) {
        if (!showCameraView) {
            // Icon or placeholder before showing the camera
            Column(
                modifier = Modifier
                    .padding(BasicMargin())
                    .fillMaxWidth()
            ) {
                IconButton(
                    onClick = {
                       showCameraView = true
                    },
                    modifier = Modifier
                        .height(96.dp)
                        .fillMaxWidth()
                        .align(Alignment.CenterHorizontally)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.add_photo),
                            contentDescription = stringResource(id = R.string.edit_add_icon),
                            tint = White,
                            modifier = Modifier
                                .background(color = MaterialTheme.colorScheme.primary, shape = CircleShape)
                                .size(48.dp)
                                .padding(HalfMargin())
                        )
                        Spacer(modifier = Modifier.height(QuarterMargin()))
                        Text(
                            text = stringResource(id = R.string.scan_receipt),
                            color = MaterialTheme.colorScheme.secondary
                        )
                    }
                }
            }
        } else {
            CameraComposeView(
                paddingValues = paddingValues,
                analyzer = receiptAnalyzer,
                overlay = {
                    if (showCameraView) {
                        // Show icon and message when text is detected
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .wrapContentHeight()
                                .align(Alignment.Center)
                                .padding(BasicMargin())
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.add_photo),  // Example icon
                                contentDescription = stringResource(id = R.string.text_detected),
                                modifier = Modifier
                                    .size(48.dp)
                                    .align(Alignment.CenterHorizontally)
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                text = stringResource(id = R.string.text_detected),
                                color = MaterialTheme.colorScheme.secondary,
                                modifier = Modifier.align(Alignment.CenterHorizontally)
                            )
                        }
                    }
                }
            )
        }
    }
}
