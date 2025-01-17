package com.mr0xf00.easycrop.sample.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.platform.LocalContext
import com.mr0xf00.easycrop.CropError
import com.mr0xf00.easycrop.CropResult
import com.mr0xf00.easycrop.crop
import com.mr0xf00.easycrop.rememberImageCropper
import com.mr0xf00.easycrop.rememberImagePicker
import kotlinx.coroutines.launch

@Composable
fun SimpleDemo(modifier: Modifier = Modifier) {
    val imageCropper = rememberImageCropper()
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    var selectedImage by remember { mutableStateOf<ImageBitmap?>(null) }
    var error by remember { mutableStateOf<CropError?>(null) }
    val imagePicker = rememberImagePicker(onImage = { uri ->
        scope.launch {
            when (val result = imageCropper.crop(uri, context)) {
                CropResult.Cancelled -> {}
                is CropError -> error = result
                is CropResult.Success -> {
                    selectedImage = result.bitmap
                }
            }
        }
    })
    DemoContent(
        cropState = imageCropper.cropState,
        loadingStatus = imageCropper.loadingStatus,
        selectedImage = selectedImage,
        onPick = { imagePicker.pick() },
        modifier = modifier
    )
    error?.let { CropErrorDialog(it, onDismiss = { error = null }) }
}

