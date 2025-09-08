package com.example.customcomponents.progressBars

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

@Preview(showBackground = true)
@Composable
fun MovingBoxProgressBar(){
    val grid= Array(3){Array(3){1} }
    grid[2][2]=0
    val emptyPositions=listOf(
        Pair(2,1),Pair(1,1), Pair(1,2),
        Pair(0,2), Pair(0,1), Pair(0,0),
        Pair(1,0), Pair(2,0), Pair(2,1)
    )
    var currentPosition by remember { mutableIntStateOf(0) }
    LaunchedEffect(Unit) {
        while (true){
            delay(300)
            currentPosition=(currentPosition+1) % emptyPositions.size
        }
    }
    val (row,col)=emptyPositions[currentPosition]
    grid[row][col]=0
    LaunchedEffect(key1 = true) {
        while (true){
            delay(300)
            grid.forEachIndexed { rowIndex, rowArray ->
               rowArray.forEachIndexed { colIndex,_->
                    grid[rowIndex][colIndex]=1
               }
            }
            val (newRow,newCol)=emptyPositions[currentPosition]
            grid[newRow][newCol]=0
        }
    }

    Column (
        modifier = Modifier.size(200.dp).padding(top = 50.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        repeat(3) {i->
            Row (
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ){
                repeat(3) {j->
                    val color=if(grid[i][j]==0) Color.Transparent else Color.Magenta.copy(.5f)
                    Box(
                        modifier = Modifier
                            .size(25.dp)
                            .background(
                                color = color,
                                shape = RoundedCornerShape(4.dp)
                            )
                    )
                }
            }
        }
    }
}