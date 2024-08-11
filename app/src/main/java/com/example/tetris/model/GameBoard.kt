package com.example.tetris.model

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color

typealias CellMatrix = Array<Array<Color?>>
class GameBoard(initialWidth: Int, initialHeight: Int) {
    var width by mutableStateOf(initialWidth)
        private set
    var height by mutableStateOf(initialHeight)
        private set
    var cells by mutableStateOf(Array(initialHeight) { arrayOfNulls<Color?>(initialWidth) })
        private set

    fun placeTetromino(tetromino: Tetromino): Boolean {
        for (row in tetromino.matrix.indices) {
            for (column in tetromino.matrix[row].indices) {
                if (tetromino.matrix[row][column] == 1) {
                    val boardX = tetromino.x + column
                    val boardY = tetromino.y + row
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

    fun clearRows() : Int {
        var clearedRows = 0
        for (rRow in cells.indices.reversed()) {
            if (cells[rRow].all { it != null }) {
                cells[rRow].fill(null)
                for (r in rRow downTo 1) {
                    cells[r] = cells[r - 1]
                }
                cells[0] = Array(width) { null }
                clearedRows++
            }
        }
        return clearedRows
    }
}
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
fun GameBoard.printBoard(): Unit {
    Log.d("GameTetris" ,"printBoard:\n${cells.joinToString(separator = "\n"){row ->
        row.joinToString(separator = ""){ color -> convertColor2Digit(color = color).toString() }
    }}")
}