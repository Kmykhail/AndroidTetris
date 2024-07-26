package com.example.tetris.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.size
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
    gameBoard: GameBoard,
    nextTetromino: Tetromino,
    cellSize: Dp = 15.dp
) {
    val cellSizePx = with(LocalDensity.current) { cellSize.toPx() }
    val cornerRadius = cellSizePx / 4

    Canvas(modifier = Modifier
        .size(cellSize * 5, cellSize * 4)
        .background(Color.White)
    ) {
        val canvasPxSize = Pair(size.width, size.height)

        val tetrominoWidth = nextTetromino.shape[0].size
        val tetrominoHeight = nextTetromino.shape.size

        val startXOffset = (canvasPxSize.first - (tetrominoWidth * cellSizePx)) / 2
        val startYOffset = (canvasPxSize.second - (tetrominoHeight * cellSizePx)) / 2

        val gridLineColor = Color.Gray.copy(alpha = 0.3f)

        gameBoard.cells.forEachIndexed { rowIndex, rows ->
            rows.forEachIndexed { columnIndex, cell ->
                drawRoundRect(
                    color = cell ?: Color.LightGray,
                    topLeft = Offset(
                        x = columnIndex * cellSize.toPx() ,
                        y = rowIndex * cellSize.toPx()
                    ),
                    size = Size(cellSize.toPx(), cellSize.toPx()),
                    cornerRadius = CornerRadius(cellSize.toPx() / 10, cellSize.toPx() / 10)
                )
            }
        }

        // Draw grid lines
        for (i in 1 until gameBoard.width) {
            drawLine(
                color = gridLineColor,
                start = Offset(x = i * cellSize.toPx(), y = 0f),
                end = Offset(x = i * cellSize.toPx(), y = size.height),
                strokeWidth = 0.5.dp.toPx()
            )
        }
        for (i in 1 until gameBoard.height) {
            drawLine(
                color = gridLineColor,
                start = Offset(x = 0f, y = i * cellSize.toPx()),
                end = Offset(x = size.width, y = i * cellSize.toPx()),
                strokeWidth = 0.5.dp.toPx()
            )
        }

        nextTetromino.shape.forEachIndexed { rowIndex, rows ->
            rows.forEachIndexed { columnIndex, cell ->
                if (cell == 1) {
                    drawRoundRect(
                        color = nextTetromino.color,
                        topLeft = Offset(
                            x = startXOffset + columnIndex * cellSizePx,
                            y = startYOffset + rowIndex * cellSizePx
                        ),
                        size = Size(cellSizePx, cellSizePx),
                        cornerRadius = CornerRadius(cornerRadius, cornerRadius)
                    )
                }
            }
        }
    }
}