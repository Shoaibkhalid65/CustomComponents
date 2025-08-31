package com.example.customcomponents.components


import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.keyframes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Person3
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.Speed
import androidx.compose.material.icons.outlined.Wifi
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.customcomponents.ui.theme.CustomComponentsTheme
import com.example.customcomponents.ui.theme.DarkColor
import com.example.customcomponents.ui.theme.DarkGradient
import com.example.customcomponents.ui.theme.Green200
import com.example.customcomponents.ui.theme.Green500
import com.example.customcomponents.ui.theme.GreenGradient
import com.example.customcomponents.ui.theme.LightColor
import com.example.customcomponents.ui.theme.Pink
import kotlinx.coroutines.launch
import kotlin.math.floor
import kotlin.math.max
import kotlin.math.roundToInt

data class UIState(
    val speed: String="",
    val ping: String="-",
    val maxSpeed: String="-",
    val arcValue: Float=0f,
    val inProgress: Boolean=false
)

suspend fun startAnimation(animation: Animatable<Float, AnimationVector1D>){
    animation.animateTo(
        targetValue = 0.84f,
        animationSpec = keyframes {
            durationMillis=9000
            0f at 0 using CubicBezierEasing(0f,1.5f,0.8f,1f)
            0.72f at 1000 using CubicBezierEasing(0.2f, -1.5f, 0f, 1f)
            0.76f at 2000 using CubicBezierEasing(0.2f, -2f, 0f, 1f)
            0.78f at 3000 using CubicBezierEasing(0.2f, -1.5f, 0f, 1f)
            0.82f at 4000 using CubicBezierEasing(0.2f, -2f, 0f, 1f)
            0.85f at 5000 using CubicBezierEasing(0.2f, -2f, 0f, 1f)
            0.89f at 6000 using CubicBezierEasing(0.2f, -1.2f, 0f, 1f)
            0.82f at 7500 using LinearOutSlowInEasing
        }
    )
}

fun Animatable<Float, AnimationVector1D>.toUiState(maxSpeed: Float): UIState{
    return UIState(
        arcValue = value,
        speed = "%.1f".format(value*100),
        ping = if(value>0.2f) "${(value*15).roundToInt()}" else "-",
        maxSpeed = if(maxSpeed>0f) "%.1f mbps".format(maxSpeed) else "-",
        inProgress = isRunning
    )
}

@Composable
fun SpeedTestScreen(){
    val coroutineScope= rememberCoroutineScope()
    val animation = remember { Animatable(0f) }
    var maxSpeed by remember { mutableFloatStateOf(0f) }
    maxSpeed= max(maxSpeed,animation.value*100f)
    SpeedTestScreen(animation.toUiState(maxSpeed)) {
        coroutineScope.launch {
            maxSpeed=0f
            startAnimation(animation)
        }
    }

}
@Composable
fun SpeedTestScreen(uiState: UIState,onClick:()-> Unit){
    CustomComponentsTheme {
        Scaffold(
            bottomBar = {
                BottomNavigationView()
            }
        ) {innerPadding->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(DarkGradient)
                    .padding(innerPadding),
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Header()
                SpeedIndicator(uiState = uiState) {
                    onClick()
                }
                AdditionalInfo(ping = uiState.ping, maxSpeed = uiState.maxSpeed)

            }
        }

    }
}


@Composable
fun SpeedIndicator(uiState: UIState,onClick: () -> Unit){
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f),
        contentAlignment = Alignment.Center,
    ){
        CircularSpeedIndicator(uiState.arcValue, angle = 240f)
        StartButton(isEnabled = !uiState.inProgress, modifier = Modifier.align(Alignment.BottomCenter)) { onClick() }
        SpeedValue(value = uiState.speed)
    }
}
@Composable
fun SpeedValue(value: String){
    Column (
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text(
            text = "DOWNLOAD",
            style = MaterialTheme.typography.bodySmall
        )
        Text(
            text = value,
            fontSize = 45.sp,
            color = Color.White,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "mbps",
            style = MaterialTheme.typography.bodySmall
        )
    }
}
@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun StartButton(isEnabled: Boolean,modifier: Modifier= Modifier,onClick: () -> Unit){
    OutlinedButton(
        onClick=onClick,
        modifier = modifier.padding(bottom = 24.dp),
        enabled = isEnabled,
        shapes = ButtonDefaults.shapes(),
        border = BorderStroke(width = 2.dp, color = MaterialTheme.colorScheme.onSurface)
    ){
        Text(
            text = "START",
            modifier = Modifier.padding(horizontal = 24.dp, vertical = 4.dp)
        )
    }
}
@Composable
fun CircularSpeedIndicator(value: Float,angle: Float){
    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .padding(40.dp)
    ) {
        drawLines(progress = value, maxValue = angle)
        drawArcs(progress = value, maxValue = angle)

    }
}
fun DrawScope.drawArcs(progress: Float,maxValue: Float){
    val startAngle=270- maxValue/2
    val sweepAngle=maxValue*progress
    val topLeft= Offset(50f,50f)
    val size=Size(size.width-100f,size.height-100f)

    fun drawBlur(){
        for(i in 0..20){
            drawArc(
                color = Green200.copy(alpha = i/900f),
                startAngle=startAngle,
                sweepAngle=sweepAngle,
                useCenter = false,
                topLeft=topLeft,
                size=size,
                style = Stroke(
                    width = 80f+(20-i)*20,
                    cap = StrokeCap.Round
                )
            )
        }
    }

    fun drawStroke(){
        drawArc(
            color = Green500,
            startAngle = startAngle,
            sweepAngle=sweepAngle,
            useCenter = false,
            topLeft=topLeft,
            size=size,
            style = Stroke(
                width = 86f,
                cap = StrokeCap.Round
            )
        )
    }

    fun drawGradient(){
        drawArc(
            brush = GreenGradient,
            startAngle = startAngle,
            sweepAngle=sweepAngle,
            useCenter = false,
            topLeft=topLeft,
            size=size,
            style = Stroke(
                width = 80f,
                cap = StrokeCap.Round
            )
        )
    }

    drawBlur()
    drawStroke()
    drawGradient()
}

fun DrawScope.drawLines(progress: Float,maxValue: Float,numberOfLines: Int=40){
    val oneRotation = maxValue / numberOfLines
    val startValue=if(progress==0f) 0 else floor(progress*numberOfLines).toInt() + 1
    for (i in startValue..numberOfLines){
        rotate(i*oneRotation + (180-maxValue)/2){
            drawLine(
                color = LightColor,
                start = Offset(if(i%5==0) 80f else 30f,size.height/2),
                end = Offset(0f,size.height/2),
                strokeWidth = 8f,
                cap = StrokeCap.Round
            )
        }
    }


}

@Composable
fun AdditionalInfo(ping: String,maxSpeed: String){
    @Composable
    fun RowScope.InfoColumn(title: String,value: String){
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = title,
            )
            Text(
                text = value,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min),
    ) {
        InfoColumn(title = "PING", value = ping)
        VerticalDivider(
            modifier = Modifier
                .fillMaxHeight(),
            thickness = 1.dp,
            color = MaterialTheme.colorScheme.onPrimary
        )
        InfoColumn(title = "MAX SPEED", value = maxSpeed)
    }
}
@Composable
fun Header(){
    Text(
        text = "SPEEDTEST",
        modifier = Modifier.padding(top = 52.dp, bottom = 16.dp),
        style = MaterialTheme.typography.headlineSmall
    )
}


@Composable
fun BottomNavigationView(){
    var selectedItem by remember { mutableIntStateOf(0) }
    val items=listOf(
        Icons.Outlined.Wifi,
        Icons.Outlined.Person3,
        Icons.Outlined.Speed,
        Icons.Outlined.Settings
    )
    NavigationBar(
        containerColor = DarkColor
    ) {
       items.forEachIndexed { index, vector ->
           NavigationBarItem(
               selected = selectedItem==index,
               onClick = {
                   selectedItem=index
               },
               icon = {
                   Icon(
                       imageVector = vector,
                       contentDescription = vector.name
                   )
               },
               colors = NavigationBarItemDefaults.colors(
                   selectedIconColor = Pink,
                   unselectedIconColor = MaterialTheme.colorScheme.onSurface,
                   indicatorColor = Color.Transparent
               )
           )
       }
    }

}
@Preview(showBackground = true)
@Composable
fun DefaultPreview(){
//    SpeedTestScreen(
//        uiState = UIState(
//            speed = "120.5",
//            ping = "5 ms",
//            maxSpeed = "150.0 mbps",
//            arcValue  =0.3f
//        ),
//        onClick = {}
//    )
    SpeedTestScreen()
}