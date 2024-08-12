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
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.tetris.model.GameBoard
import com.example.tetris.model.Tetromino

@Composable
fun GameBoardComposable(
    gameBoard: GameBoard,
    currentTetromino: Tetromino,
    cellSize: Dp = CELL_SIZE_DP
) {

    val cellSizePx = with(LocalDensity.current) { cellSize.toPx() }
    val defaultColor = MaterialTheme.colorScheme.secondaryContainer
    val gridLineColor = MaterialTheme.colorScheme.onSecondary
    val cornerRadius = cellSizePx / 8

    // Draw the game board
    Canvas(modifier = Modifier
        .size(cellSize * gameBoard.width, cellSize * gameBoard.height)
    ) {
        drawGameBoard(gameBoard, cellSizePx, cornerRadius, defaultColor)
        drawTetromino(currentTetromino, cellSizePx, cornerRadius)
        drawLines(gameBoard, cellSizePx, gridLineColor)
    }
}

fun DrawScope.drawLines(
    gameBoard: GameBoard,
    cellSizePx: Float,
    gridLineColor: Color
) {
    // Draw grid lines
    for (i in 1 until gameBoard.width) {
        drawLine(
            color = gridLineColor,
            start = Offset(x = i * cellSizePx, y = 0f),
            end = Offset(x = i * cellSizePx, y = size.height),
            strokeWidth = 1.dp.toPx()
        )
    }
    for (i in 1 until gameBoard.height) {
        drawLine(
            color = gridLineColor,
            start = Offset(x = 0f, y = i * cellSizePx),
            end = Offset(x = size.width, y = i * cellSizePx),
            strokeWidth = 1.dp.toPx()
        )
    }
}

fun DrawScope.drawGameBoard(
    gameBoard: GameBoard,
    cellSizePx: Float,
    cornerRadius: Float,
    defaultColor: Color
) {
    // Draw board
    gameBoard.cells.forEachIndexed { rowIndex, rows ->
        rows.forEachIndexed { columnIndex, cell ->
            drawRoundRect(
                color = cell ?: defaultColor,
                topLeft = Offset(
                    x = columnIndex * cellSizePx ,
                    y = rowIndex * cellSizePx
                ),
                size = Size(cellSizePx, cellSizePx),
                cornerRadius = CornerRadius(cornerRadius, cornerRadius)
            )
        }
    }
}

fun DrawScope.drawTetromino(
    tetromino: Tetromino,
    cellSizePx: Float,
    cornerRadius: Float
) {
    tetromino.matrix.forEachIndexed { rowIndex, rows ->
        rows.forEachIndexed { columnIndex, cell ->
            if (cell == 1) {
                drawRoundRect(
                    color = Color.Black.copy(alpha = 0.3f),
                    topLeft = Offset(
                        x = (tetromino.shadowX+ columnIndex) * cellSizePx,
                        y = (tetromino.shadowY + rowIndex) * cellSizePx
                    ),
                    size = Size(cellSizePx, cellSizePx),
                    cornerRadius = CornerRadius(cornerRadius, cornerRadius)
                )
                drawRoundRect(
                    color = tetromino.color,
                    topLeft = Offset(
                        x = (tetromino.x + columnIndex) * cellSizePx,
                        y = (tetromino.y + rowIndex) * cellSizePx
                    ),
                    size = Size(cellSizePx, cellSizePx),
                    cornerRadius = CornerRadius(cornerRadius, cornerRadius)
                )
            }
        }
    }
}