package com.example.customcomponents.progressBars

import android.animation.TypeConverter
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.TwoWayConverter
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateValue
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times

@Preview(showBackground = true)
@Composable
fun ExpandedBarProgressBar(){
    val infiniteTransition= rememberInfiniteTransition()
    val duration=600
    val delays= listOf(0,100,200,300,400)
    val heights= delays.map { delay->
        val dpToConverter: TwoWayConverter<Dp, AnimationVector1D> = TwoWayConverter(
            convertToVector = { dp-> AnimationVector1D(dp.value) },
            convertFromVector = { vectorD -> vectorD.value.dp }
        )
        infiniteTransition.animateValue(
            initialValue = 20.dp,
            targetValue = 100.dp,
            typeConverter = dpToConverter,
            animationSpec = infiniteRepeatable(
                animation = tween(
                    durationMillis = duration,
                    delayMillis = delay,
                    easing = FastOutSlowInEasing
                ),
                repeatMode = RepeatMode.Reverse
            )
        )
    }
    Row(
        modifier = Modifier
            .size(200.dp)
            .padding(start = 24.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        heights.forEach { height->

            Box(
                modifier = Modifier
                    .width(20.dp)
                    .height(height = height.value)
                    .background(Color.Green.copy(.8f),
                shape = RoundedCornerShape(8.dp))
            )
        }
    }
}