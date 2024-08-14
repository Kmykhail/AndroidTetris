package com.example.tetris.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.tetris.model.Tetromino

@Composable
fun NextTetromino(
    nextTetromino: Tetromino,
    cellSize: Dp
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(horizontal = 4.dp)
    ) {
        Text(
            text = "Next",
            style = MaterialTheme.typography.titleMedium
        )
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .background(Color.LightGray)
                .width(70.dp)
                .height(40.dp)
            ,
            contentAlignment = Alignment.Center
        ) {
            val cellSizePx = with(LocalDensity.current) { cellSize.toPx() }
            val tetrominoWidth = nextTetromino.matrix[0].size
            val tetrominoHeight = nextTetromino.matrix.size

            Canvas(modifier = Modifier
                .size(cellSize * 4, cellSize * 4)
            ) {
                val startXOffset = (size.width - (tetrominoWidth * cellSizePx)) / 2
                val startYOffset = (size.height - (tetrominoHeight * cellSizePx)) / 2
                nextTetromino.matrix.forEachIndexed { rowIndex, rows ->
                    rows.forEachIndexed { columnIndex, cell ->
                        if (cell == 1) {
                            drawRect(
                                color = nextTetromino.color,
                                topLeft = Offset(
                                    x = startXOffset + columnIndex * cellSizePx,
                                    y = startYOffset + rowIndex * cellSizePx
                                ),
                                size = Size(cellSizePx, cellSizePx)
                            )
                        }
                    }
                }
            }
        }
    }
}