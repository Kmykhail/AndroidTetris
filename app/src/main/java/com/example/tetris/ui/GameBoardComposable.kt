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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.tetris.model.GameBoard
import com.example.tetris.model.Tetromino

@Composable
fun GameBoardComposable(
    gameBoard: GameBoard,
    tetromino: Tetromino,
    cellSize: Dp = CELL_SIZE_DP,
    padding: Dp = 4.dp
) {
    // Draw the game board
    Canvas(modifier = Modifier
        .size(cellSize * gameBoard.width, cellSize * gameBoard.height)
        .background(Color.White)
    ) {
        val cornerRadius = cellSize.toPx() / 4
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
                strokeWidth = 1.dp.toPx()
            )
        }
        for (i in 1 until gameBoard.height) {
            drawLine(
                color = gridLineColor,
                start = Offset(x = 0f, y = i * cellSize.toPx()),
                end = Offset(x = size.width, y = i * cellSize.toPx()),
                strokeWidth = 1.dp.toPx()
            )
        }

        tetromino.shape.forEachIndexed { rowIndex, rows ->
            rows.forEachIndexed { columnIndex, cell ->
                if (cell == 1) {
                    drawRoundRect(
                        color = tetromino.color,
                        topLeft = Offset(
                            x = (tetromino.xPos + columnIndex) * cellSize.toPx(),
                            y = (tetromino.yPos + rowIndex) * cellSize.toPx()
                        ),
                        size = Size(cellSize.toPx(), cellSize.toPx()),
                        cornerRadius = CornerRadius(cornerRadius, cornerRadius)
                    )
                }
            }
        }
    }
}