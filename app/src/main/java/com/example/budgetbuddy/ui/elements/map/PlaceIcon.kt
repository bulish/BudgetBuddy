package com.example.budgetbuddy.ui.elements.map

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.budgetbuddy.R
import com.example.budgetbuddy.ui.theme.BasicMargin
import java.io.File
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun PlaceIcon(
    filesDir: File,
    iconName: String? = null,
    onClickHandler: (() -> Unit)? = null,
) {
    if (iconName == null) return
    var imageFile = if (iconName != null) File(filesDir, iconName) else R.drawable.storefront_24px

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = BasicMargin()),
        contentAlignment = Alignment.Center,
    ) {
        Box(modifier = Modifier.fillMaxWidth()
        ){
            GlideImage(
                imageModel = imageFile,
                modifier = Modifier.fillMaxWidth().heightIn(max = 200.dp),
                loading = {
                    CircularProgressIndicator()
                }
            )

            if (onClickHandler != null) {
                IconButton(
                    onClick = { onClickHandler() },
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(top = BasicMargin())
                        .padding(end = BasicMargin())
                        .background(Color.White, shape = CircleShape)
                ) {
                    Icon(
                        imageVector = androidx.compose.material.icons.Icons.Default.Delete,
                        contentDescription = stringResource(id = R.string.edit_delete_icon),
                        tint = Color.Red
                    )
                }
            }
        }
    }
}
