package com.example.ui

import androidx.annotation.ColorInt
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.core.graphics.ColorUtils
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import kotlin.math.absoluteValue

@Composable
fun UserHead(
    id: String,
    name: String,
    /*avatarUrl: String,*/
    modifier: Modifier = Modifier,
    size: Dp = 40.dp,
    textStyle: TextStyle = MaterialTheme.typography.bodyMedium,
) {
    val color = remember(id, name) {
        Color("$id / $name".toHslColor())
    }
    val initials = name.take(1).uppercase()
    Box(
        modifier = modifier
            .size(size)
            .background(color, CircleShape),
        contentAlignment = Alignment.Center
    ) {
//        if (avatarUrl.isNullOrEmpty()) {
//            Text(text = initials, style = textStyle, color = Color.White)
//        } else {
//            val painter: Painter = rememberImagePainter(
//                data = avatarUrl,
//                builder = {
//                    transformations(CircleCropTransformation())
//                }
//            )
//            Image(
//                painter = painter,
//                contentDescription = null,
//                modifier = Modifier.fillMaxSize()
//            )
//        }
    }
}

@ColorInt
fun String.toHslColor(saturation: Float = 0.5f, lightness: Float = 0.4f): Int {
    val hue = fold(0) { acc, char -> char.code + acc * 37 } % 360
    return ColorUtils.HSLToColor(floatArrayOf(hue.absoluteValue.toFloat(), saturation, lightness))
}