package com.example.budgetbuddy.ui.elements.transactions

import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import java.util.concurrent.Executors

@Composable
fun CameraComposeView(
    paddingValues: PaddingValues,
    analyzer: ImageAnalysis.Analyzer,
    overlay: @Composable () -> Unit
) {

    val context = LocalContext.current
    val processCameraProvider = remember {
        ProcessCameraProvider.getInstance(context)
    }

    val mainExecutor = ContextCompat.getMainExecutor(context)
    val cameraExecutor = remember {
        Executors.newSingleThreadExecutor()
    }

    val lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current

    var preview by remember {
        mutableStateOf<Preview?>(null)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {

        AndroidView(
            modifier = Modifier.fillMaxSize(),
            factory = { context ->
                val previewView = PreviewView(context)

                processCameraProvider.addListener(
                    {
                        val cameraSelector = CameraSelector.Builder()
                            .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                            .build()

                        val cameraProviderInstance = processCameraProvider.get()

                        val analyzerBuilder = ImageAnalysis.Builder()
                            .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                            .build()

                        analyzerBuilder.setAnalyzer(cameraExecutor, analyzer)

                        cameraProviderInstance.bindToLifecycle(
                            lifecycleOwner = lifecycleOwner,
                            cameraSelector = cameraSelector,
                            preview,
                            analyzerBuilder
                        )
                    },
                    mainExecutor
                )


                preview = Preview.Builder().build().also {
                    it.surfaceProvider = previewView.surfaceProvider
                }

                previewView
            }
        )

        Box(modifier = Modifier.fillMaxSize()) {
            overlay()
        }
    }
}
