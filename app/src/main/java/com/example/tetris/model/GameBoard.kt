package com.example.tetris.model

import android.util.Log
import androidx.compose.ui.graphics.Color

typealias CellMatrix = Array<Array<Color?>>
data class GameBoard(
    val width: Int,
    val height: Int,
    val cells: CellMatrix = Array(height) { arrayOfNulls<Color?>(width) }
)

fun GameBoard.placeTetromino(tetromino: Tetromino): Boolean {
    for (row in tetromino.shape.indices) {
        for (column in tetromino.shape[row].indices) {
            if (tetromino.shape[row][column] == 1) {
                val boardX = tetromino.xPos + column
                val boardY = tetromino.yPos + row
                if (boardX in 0 until width && boardY in 0 until height && cells[boardY][boardX] == null) {
                    cells[boardY][boardX] = tetromino.color
                } else {
                    return false
                }
            }
        }
    }
    return true
}