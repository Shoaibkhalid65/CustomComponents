package com.example.customcomponents.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.keyframes
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import kotlin.math.cos
import kotlin.math.sin

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
@Preview(showBackground = true)
fun SpeedMeter2Screen(){
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        val coroutineScope= rememberCoroutineScope()
        val animation = remember { Animatable(0f)}
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            CircularSpeedMeter2(400.dp, animation.value.coerceIn(0f,100f))
            OutlinedButton(
                onClick = {
                    coroutineScope.launch {
                        startAnim(animation)
                    }
                },
                shapes = ButtonDefaults.shapes()
            ) {
                Text(
                    "Start"
                )
            }
        }
    }
}

@Composable
fun CircularSpeedMeter2(sizeDp: Dp,progress: Float){
    val textMeasurer= rememberTextMeasurer()
    Canvas(
        modifier = Modifier.size(sizeDp).padding(30.dp)
    ) {
//        background arc
        drawArc(
            color = Color.LightGray,
            startAngle = 150f,
            sweepAngle = 240f,
            useCenter = false,
            style = Stroke(
                width = 15f
            )
        )
//        tick marks
        for (i in (0..240) step 2){
            rotate(i.toFloat()-30f){
                drawLine(
                    color = Color.Gray,
                    start = Offset(if(i%20==0) 70f else 50f,size.height/2),
                    end = Offset(25f,size.height/2),
                    strokeWidth = when {
                        i % 20 == 0 -> 12f
                        i % 10 == 0 -> 8f
                        else -> 4f
                    }
                )
            }
        }
        val color=when(progress){
            in 1f..33f -> Color.Green
            in 34f..66f-> Color.Yellow
            else -> Color.Red
        }
//        progress arc
        drawArc(
            color = color,
            startAngle = 150f,
            sweepAngle = progress*2.4f,
            useCenter = false,
            style = Stroke(
                width = 15f
            )
        )
//        center circle
        drawCircle(
            color = Color.DarkGray,
            radius = 50f
        )
//         Needle
        val path= Path().apply {
            moveTo(center.x,center.y-20f)
            lineTo(100f,size.height/2)
            lineTo(center.x,center.y+20f)
            close()
        }
        rotate(progress*2.4f-30f){
            drawPath(
                path=path,
                color=Color.Red
            )
        }
//        Speed value
        val textLayoutResult=textMeasurer.measure(
            text = progress.toInt().toString(),
            style = TextStyle(
                fontWeight = FontWeight.SemiBold,
                fontSize = 24.sp,
                fontStyle = FontStyle.Italic
            )
        )
        val textWidth=textLayoutResult.size.width
        drawText(
            textLayoutResult=textLayoutResult,
            topLeft = Offset(center.x-textWidth/2,center.y+100f)
        )
//          text of numbers around arc
        for (i in 0..240 step 20){
            val angle= Math.toRadians(i.toDouble()+150).toFloat()
            val radius=size.minDimension/2.5f-20
            val textLayoutResult=textMeasurer.measure(
                text = i.toString(),
            )
            val x=(center.x+cos(angle)*radius)
            val y=(center.y+sin(angle)*radius)
            val txtWidth=textLayoutResult.size.width
            val txtHeight=textLayoutResult.size.height
            drawText(
                textLayoutResult=textLayoutResult,
                topLeft = Offset(x-txtWidth/2,y-txtHeight/2)
            )

        }
    }
}
//  animation for progress
suspend fun startAnim(animation: Animatable<Float, AnimationVector1D>){
    animation.animateTo(
        targetValue = 99f,
        animationSpec = keyframes {
            durationMillis=9000
            0f at 0 using CubicBezierEasing(0f,1.5f,0.8f,1f)
            34f at 1500 using CubicBezierEasing(0.2f, -1.5f, 0f, 1f)
            58f at 2000 using CubicBezierEasing(0.2f, -2f, 0f, 1f)
            21f at 3000 using CubicBezierEasing(0.2f, -1.5f, 0f, 1f)
            92f at 4000 using CubicBezierEasing(0.2f, -2f, 0f, 1f)
            45f at 5000 using CubicBezierEasing(0.2f, -2f, 0f, 1f)
            54f at 6000 using CubicBezierEasing(0.2f, -1.2f, 0f, 1f)
            84f at 7500 using LinearOutSlowInEasing
        }
    )
}
