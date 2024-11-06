package com.example.budgetbuddy.ui.elements.shared.form


import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import com.canhub.cropper.CropImageContract
import com.canhub.cropper.CropImageContractOptions
import com.canhub.cropper.CropImageOptions
import com.example.budgetbuddy.R
import com.example.budgetbuddy.ui.theme.BasicMargin
import com.example.budgetbuddy.ui.theme.Green
import com.example.budgetbuddy.ui.theme.HalfMargin
import com.example.budgetbuddy.ui.theme.QuarterMargin
import com.example.budgetbuddy.ui.theme.White
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

@Composable
fun ImageInput(
    onChangeHandler: (String) -> Unit,
    context: Context,
    selectedImageUri: Uri?,
    changeSelectedImageUri: (Uri?) -> Unit
) {
    var bitmap: Bitmap? by remember { mutableStateOf(null) }

    val imageCropLauncher =
        rememberLauncherForActivityResult(contract = CropImageContract()) { result ->
            if (result.isSuccessful) {
                result.uriContent?.let {

                    bitmap = if (Build.VERSION.SDK_INT < 28) {
                        MediaStore.Images
                            .Media.getBitmap(context.contentResolver, it)
                    } else {
                        val source = ImageDecoder
                            .createSource(context.contentResolver, it)
                        ImageDecoder.decodeBitmap(source)
                    }

                    bitmap?.let { b ->
                        bitmapToUri(
                            b,
                            context,
                            changeSelectedImageUri,
                            onChangeHandler
                        )
                    }
                }

            } else {
                println("ImageCropping error: ${result.error}")
            }
        }

    Column(
        modifier = Modifier
            .padding(BasicMargin())
            .fillMaxWidth()
    ) {
        IconButton(
            onClick = {
                val cropOptions = CropImageContractOptions(
                    null,
                    CropImageOptions(imageSourceIncludeCamera = false)
                )
                imageCropLauncher.launch(cropOptions)
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
                        .background(color = Green, shape = CircleShape)
                        .size(48.dp)
                        .padding(HalfMargin())
                )
                Spacer(modifier = Modifier.height(QuarterMargin()))
                Text(
                    text = stringResource(id = if (selectedImageUri != null) R.string.edit_change_icon else R.string.edit_add_icon),
                    color = MaterialTheme.colorScheme.secondary
                )
            }
        }
    }
}

fun bitmapToUri(
    bitmap: Bitmap,
    context: Context,
    changeSelectedImageUri: (Uri?) -> Unit,
    onChangeHandler: (String) -> Unit
) {
    val imageFileName = "${System.currentTimeMillis()}.jpg"
    val file = File(context.filesDir, imageFileName)

    var uri: Uri? = null

    try {
        val outputStream = FileOutputStream(file)
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
        outputStream.flush()
        outputStream.close()

        uri = FileProvider.getUriForFile(context, "${context.packageName}.fileprovider", file)
        changeSelectedImageUri(uri)
        onChangeHandler(imageFileName)
    } catch (e: IOException) {
        e.printStackTrace()
    }
}
