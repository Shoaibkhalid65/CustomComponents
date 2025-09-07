package com.example.customcomponents.progressBars

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.customcomponents.ui.theme.sunGradient

@Preview(showBackground = true)
@Composable
fun CircularBarRotation1(){
    val infiniteTransition= rememberInfiniteTransition()
    val radius=infiniteTransition.animateFloat(
        initialValue = 10f,
        targetValue = 200f,
        animationSpec = infiniteRepeatable(
            animation = tween(3000),
            repeatMode = RepeatMode.Reverse
        )
    )
    Box(
        modifier = Modifier.size(500.dp),
        contentAlignment = Alignment.Center
    ){
        Box(modifier = Modifier.size(100.dp).rotate(radius.value)) {
            repeat(3) {i->
                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .rotate(i.toFloat()*120)
                        .background(
                            brush = sunGradient(radius.value),
                            shape = RoundedCornerShape(8.dp)
                        )
                )
            }
        }
    }
}