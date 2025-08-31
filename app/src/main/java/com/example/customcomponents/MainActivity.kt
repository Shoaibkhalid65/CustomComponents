package com.example.customcomponents

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.rotate

import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.customcomponents.components.SpeedTestScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SpeedTestScreen()
        }
    }
}
@Composable
@Preview(showBackground = true)
fun Sample1(){
    Canvas(modifier = Modifier.size(300.dp).padding(24.dp)) {
        for(angle in 0..180 step 6){
            rotate(angle.toFloat()) {
                drawLine(
                    color = Color.Blue,
                    start = Offset(if(angle%15==0) size.width / 10 else size.width/20, size.height / 2),
                    end = Offset(0f, size.height / 2),
                    strokeWidth = 15f,
                    cap = StrokeCap.Round
                )
            }
        }
    }
}
