package com.example.customcomponents.progressBars

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.customcomponents.ui.theme.CompareNowGradient
import com.example.customcomponents.ui.theme.MagicGradient
import com.example.customcomponents.ui.theme.RelaxingRedGradient

@Preview(showBackground = true)
@Composable
fun CircularBarRotation2() {
    val infiniteTransition = rememberInfiniteTransition()
    val rotation = infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(3000),
            repeatMode = RepeatMode.Restart
        )
    )

    Box(
        modifier = Modifier.size(500.dp),
        contentAlignment = Alignment.Center
    ) {
        Box(modifier = Modifier.size(100.dp)) {
            for (i in 0..360 step 30) {
                Box(
                    modifier = Modifier
                        .height(20.dp)
                        .width(100.dp)
                        .rotate(i.toFloat())
                        .padding(start =80.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .rotate(rotation.value)
                            .background(
                                brush = CompareNowGradient,
                                shape = RoundedCornerShape(16.dp)
                            )
                    )
                }
            }
        }
    }
}
