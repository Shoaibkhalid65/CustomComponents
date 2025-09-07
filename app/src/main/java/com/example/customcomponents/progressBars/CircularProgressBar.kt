package com.example.customcomponents.progressBars

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun CircularProgressBar(){
    val infiniteTransition= rememberInfiniteTransition()
    val rotation = infiniteTransition.animateFloat(
        initialValue = 360f,
        targetValue = 0f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 2500, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
    )
    val numberOfBars=12
    Canvas(
        modifier = Modifier.size(100.dp).padding(16.dp)
    ) {
        val canvasWidth=size.width
        val canvasHeight=size.height
        val angleStep=360f/numberOfBars
        val radius=canvasWidth/2.2f
        val barWidth= 6.dp.toPx()
        for (i in 0 until numberOfBars){
            val angle=i*angleStep+rotation.value
            val alpha=1f-(i.toFloat()/numberOfBars)
            rotate(degrees = angle){
                drawRoundRect(
                    color = Color.Blue.copy(alpha=alpha),
                    topLeft = Offset(canvasWidth/2-barWidth/2,canvasHeight/2-radius),
                    size= Size(barWidth,radius/2),
                    cornerRadius = CornerRadius(barWidth/2,barWidth/2)
                )
            }
        }

    }
}
@Composable
@Preview(showBackground = true)
fun CircularProgressBar2() {
    val infiniteTransition = rememberInfiniteTransition()
    val rotation = infiniteTransition.animateFloat(
        initialValue = 360f,
        targetValue = 0f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 2500, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
    )

    val numberOfBars = 12
    val angleStep = 360 / numberOfBars
    val radius = 40.dp

    Box(
        modifier = Modifier.size(100.dp).rotate(rotation.value),
        contentAlignment = Alignment.Center
    ) {
        for (i in 0 until numberOfBars) {
            val angle = i * angleStep
            val alpha = 1f - (i.toFloat() / numberOfBars)

            Box(
                modifier = Modifier
                    .offset {
                        val rad = Math.toRadians(angle.toDouble())
                        val x = (radius.toPx() * cos(rad)).toInt()
                        val y = (radius.toPx() * sin(rad)).toInt()
                        IntOffset(x, y)
                    }
                    .height(4.dp)
                    .width(20.dp)
                    .rotate(angle.toFloat())
                    .alpha(alpha)
                    .background(
                        color = Color.Blue,
                        shape = RoundedCornerShape(2.dp)
                    )
            )
        }
    }
}
