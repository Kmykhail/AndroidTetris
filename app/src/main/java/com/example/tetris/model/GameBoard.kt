package com.example.tetris.model

import android.util.Log
import androidx.compose.ui.graphics.Color

typealias CellMatrix = Array<Array<Color?>>
data class GameBoard(
    val width: Int,
    val height: Int,
    val cells: CellMatrix = Array(height) { arrayOfNulls<Color?>(width) }
)
private fun convertColor2Digit(color: Color?): Int {
    when(color) {
        Color.Cyan -> return 1
        Color.Yellow -> return 2
        Color.Magenta -> return 3
        Color.Blue -> return 4
        Color.Black -> return 5
        Color.Green -> return 6
        Color.Red -> return 7
        else -> return 0
    }
}

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
fun GameBoard.printBoard(): Unit {
    Log.d("GameTetris" ,"printBoard:\n${cells.joinToString(separator = "\n"){row ->
        row.joinToString(separator = ""){ color -> convertColor2Digit(color = color).toString() }
    }}")
}