package com.mr0xf00.easycrop.sample.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.mandatorySystemGestures
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.unit.dp
import com.mr0xf00.easycrop.CropState
import com.mr0xf00.easycrop.CropperLoading
import com.mr0xf00.easycrop.sample.ui.theme.EasyCropTheme
import com.mr0xf00.easycrop.ui.CropperPreview
import com.mr0xf00.easycrop.ui.ImageCropperDialog

@Composable
fun DemoContent(
    cropState: CropState?,
    loadingStatus: CropperLoading?,
    selectedImage: ImageBitmap?,
    onPick: () -> Unit,
    modifier: Modifier = Modifier,
    noDialog: Boolean = false,
) {
    if (cropState != null) {
        EasyCropTheme(darkTheme = true) {
            if (noDialog) {
                CropperPreview(
                    state = cropState,
                    extraPadding = WindowInsets.mandatorySystemGestures.asPaddingValues(),
                )
            } else {
                ImageCropperDialog(state = cropState)
            }
        }
    }
    if (cropState == null && loadingStatus != null) {
        LoadingDialog(status = loadingStatus)
    }
    if (cropState == null) {
        Column(
            modifier = modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (selectedImage != null) Image(
                bitmap = selectedImage, contentDescription = null,
                modifier = Modifier.weight(1f)
            ) else Box(contentAlignment = Alignment.Center, modifier = Modifier.weight(1f)) {
                Text("No image selected !")
            }
            Button(onClick = onPick) { Text("Choose Image") }
        }
    }
}
