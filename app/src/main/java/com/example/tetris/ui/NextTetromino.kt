package com.example.tetris.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.tetris.model.GameBoard
import com.example.tetris.model.Tetromino

@Composable
fun NextTetromino(
    nextTetromino: Tetromino,
    cellSize: Dp = 20.dp
) {
    val cellSizePx = with(LocalDensity.current) { cellSize.toPx() }
    val tetrominoWidth = nextTetromino.shape[0].size
    val tetrominoHeight = nextTetromino.shape.size

    Canvas(modifier = Modifier
        .size(cellSize * 4, cellSize * 4)
    ) {
        val startXOffset = (size.width - (tetrominoWidth * cellSizePx)) / 2
        val startYOffset = (size.height - (tetrominoHeight * cellSizePx)) / 2
        nextTetromino.shape.forEachIndexed { rowIndex, rows ->
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