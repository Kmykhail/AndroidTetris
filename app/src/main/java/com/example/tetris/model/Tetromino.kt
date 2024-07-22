package com.example.tetris.model

import androidx.compose.ui.graphics.Color
import com.example.tetris.BOARD_WIDTH

typealias Matrix = Array<IntArray>
data class Tetromino(
    val shape: Matrix,
    val color: Color,
    var xPos: Int = (BOARD_WIDTH - shape[0].size) / 2,
    var yPos: Int = 0
)

fun Tetromino.tryMove(gameBoard: GameBoard): Boolean {
    for (row in shape.indices) {
        for (column in shape[row].indices) {
            val boardCellX = xPos + column
            val boardCellY = yPos + row

            if (shape[row][column] > 0) {
                if (boardCellX !in 0 until gameBoard.width ||
                    boardCellY !in 0 until gameBoard.height ||
                    gameBoard.cells[boardCellY][boardCellX] != null) {
                    return false
                }
            }
        }
    }

    return true
}

val tetrominoShapes = listOf(
    Tetromino(
        shape = arrayOf(
            intArrayOf(1, 1, 1, 1)  // I-shape
        ),
        color = Color.Cyan
    ),
    Tetromino(
        shape = arrayOf(
            intArrayOf(1, 1),
            intArrayOf(1, 1)       // O-shape
        ),
        color = Color.Yellow
    ),
    Tetromino(
        shape = arrayOf(
            intArrayOf(0, 1, 0),
            intArrayOf(1, 1, 1)    // T-shape
        ),
        color = Color.Magenta
    ),
    Tetromino(
        shape = arrayOf(
            intArrayOf(1, 0, 0),
            intArrayOf(1, 1, 1)    // L-shape
        ),
        color = Color(0XFFA500)
    ),
    Tetromino(
        shape = arrayOf(
            intArrayOf(0, 0, 1),
            intArrayOf(1, 1, 1)    // J-shape
        ),
        color = Color.Blue
    ),
    Tetromino(
        shape = arrayOf(
            intArrayOf(0, 1, 1),
            intArrayOf(1, 1, 0)    // S-shape
        ),
        color = Color.Green
    ),
    Tetromino(
        shape = arrayOf(
            intArrayOf(1, 1, 0),
            intArrayOf(0, 1, 1)    // Z-shape
        ),
        color = Color.Red
    )
)