package com.example.customcomponents.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.customcomponents.ui.theme.Purple700
import com.example.customcomponents.ui.theme.Teal200
import kotlinx.coroutines.delay
import java.util.Calendar
import java.util.Date
import kotlin.math.cos
import kotlin.math.sin

@Composable
@Preview(showBackground = true)
fun AnalogClockScreen(){
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        var currentTimeInMs by remember { mutableLongStateOf(System.currentTimeMillis()) }
        LaunchedEffect(Unit) {
            while (true){
                delay(1000)
                currentTimeInMs= System.currentTimeMillis()
            }
        }
        val date= Date(currentTimeInMs)
        val calendar= Calendar.getInstance()
        calendar.time=date
        val hours=calendar.get(Calendar.HOUR)
        val minutes=calendar.get(Calendar.MINUTE)
        val seconds=calendar.get(Calendar.SECOND)
        ClockShape(400.dp,seconds*6f,(minutes+seconds/60f)*6,(60 * hours + minutes) * 30f / 60f)
    }
}
@Composable
fun ClockShape(sizeDp: Dp,secondAngle: Float,minuteAngle: Float,hourAngle: Float){
    val textMeasurer= rememberTextMeasurer()
    Canvas(
        modifier = Modifier.size(sizeDp).padding(24.dp)
    ) {
        drawCircle(
            color = Teal200,
            radius = size.minDimension/2
        )
        for (angle in 0..360 step 6) {
            rotate(angle.toFloat()) {
                val isHourLine = angle % 5 == 0
                drawLine(
                    color = if (isHourLine) Color.Yellow else Color.DarkGray,
                    start = Offset(30f, size.height / 2),
                    end = Offset(0f, size.height / 2),
                    strokeWidth = if (isHourLine) 10f else 5f
                )

            }
        }
        for (i in 1..12) {
            val angle = Math.toRadians((i * 30 - 90).toDouble())
            val radius = size.minDimension / 2.5f
            val x = (center.x + cos(angle) * radius).toFloat()
            val y = (center.y + sin(angle) * radius).toFloat()
            val textLayoutResult = textMeasurer.measure(
                text = i.toString(),
                style = TextStyle.Default.copy(fontSize = 20.sp, color = Color.Black),

            )
            val textWidth = textLayoutResult.size.width
            val textHeight = textLayoutResult.size.height

            drawText(
                textLayoutResult,
                topLeft = Offset(x - textWidth / 2, y - textHeight / 2)
            )
        }

        for(i in 0..50){
            drawCircle(
                color = Color.White.copy(alpha = i/1200f),
                radius =i*8f
            )
        }
        rotate(hourAngle) {
            drawLine(
                color = Color.Black,
                start = center,
                end = Offset(size.width/2, 160f),
                strokeWidth = 25f,
                cap = StrokeCap.Round
            )
        }
        rotate(minuteAngle) {
            drawLine(
                color = Color.Black,
                start = center,
                end = Offset(size.width/2, 100f),
                strokeWidth = 15f,
                cap = StrokeCap.Round
            )
        }
        rotate(secondAngle) {
            drawLine(
                color = Color.Red,
                start = center,
                end = Offset(size.width/2, 80f),
                strokeWidth = 8f,
                cap = StrokeCap.Round
            )
        }
        drawCircle(
            color = Purple700,
            radius = 30f
        )
    }
}