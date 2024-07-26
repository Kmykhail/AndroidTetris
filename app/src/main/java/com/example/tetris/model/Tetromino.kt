package com.example.tetris.model

import android.util.Log
import androidx.compose.ui.graphics.Color
import com.example.tetris.ui.BOARD_WIDTH

typealias Matrix = Array<IntArray>
data class Tetromino(
    val shape: Matrix,
    val color: Color,
    var xPos: Int = (BOARD_WIDTH - shape[0].size) / 2,
    var yPos: Int = -1
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

fun Tetromino.printTetromino(){
    Log.d("GameTetris" ,"printTetromino:\n${shape.joinToString(separator = "\n"){row ->
        row.joinToString(separator = ""){ cell -> cell.toString() }
    }}")
}

private val matrixShapes = mapOf(
    'I' to listOf( // I-shape
        arrayOf(
            intArrayOf(0, 0, 0, 0),
            intArrayOf(1, 1, 1, 1),
            intArrayOf(0, 0, 0, 0),
            intArrayOf(0, 0, 0, 0)
          ),
        arrayOf(
            intArrayOf(0, 0, 1, 0),
            intArrayOf(0, 0, 1, 0),
            intArrayOf(0, 0, 1, 0),
            intArrayOf(0, 0, 1, 0)
          ),
        arrayOf(
            intArrayOf(0, 0, 0, 0),
            intArrayOf(0, 0, 0, 0),
            intArrayOf(1, 1, 1, 1),
            intArrayOf(0, 0, 0, 0)
          ),
        arrayOf(
            intArrayOf(0, 1, 0, 0),
            intArrayOf(0, 1, 0, 0),
            intArrayOf(0, 1, 0, 0),
            intArrayOf(0, 1, 0, 0)
        ),
    ),
    'J' to listOf( // J-shape
        arrayOf(
            intArrayOf(1, 0, 0),
            intArrayOf(1, 1, 1),
            intArrayOf(0, 0, 0)
        ),
        arrayOf(
            intArrayOf(0, 1, 1),
            intArrayOf(0, 1, 0),
            intArrayOf(0, 1, 0)
        ),
        arrayOf(
            intArrayOf(0, 0, 0),
            intArrayOf(1, 1, 1),
            intArrayOf(0, 0, 1)
        ),
        arrayOf(
            intArrayOf(0, 1, 0),
            intArrayOf(0, 1, 0),
            intArrayOf(1, 1, 0)
        ),
    ),
    'L' to listOf( // L-shape
        arrayOf(
            intArrayOf(0, 0, 1),
            intArrayOf(1, 1, 1),
            intArrayOf(0, 0, 0),
        ),
        arrayOf(
            intArrayOf(0, 1, 0),
            intArrayOf(0, 1, 0),
            intArrayOf(0, 1, 1),
        ),
        arrayOf(
            intArrayOf(0, 0, 0),
            intArrayOf(1, 1, 1),
            intArrayOf(1, 0, 0),
        ),
        arrayOf(
            intArrayOf(1, 1, 0),
            intArrayOf(0, 1, 0),
            intArrayOf(0, 1, 0),
        ),
    ),
    'T' to listOf( // T-shape
        arrayOf(
            intArrayOf(0, 1, 0),
            intArrayOf(1, 1, 1),
            intArrayOf(0, 0, 0)
        ),
        arrayOf(
            intArrayOf(0, 1, 0),
            intArrayOf(0, 1, 1),
            intArrayOf(0, 1, 0)
        ),
        arrayOf(
            intArrayOf(0, 0, 0),
            intArrayOf(1, 1, 1),
            intArrayOf(0, 1, 0)
        ),
        arrayOf(
            intArrayOf(0, 1, 0),
            intArrayOf(1, 1, 0),
            intArrayOf(0, 1, 0)
        )
    ),
    'O' to listOf( // O-shape
        arrayOf(
            intArrayOf(1, 1),
            intArrayOf(1, 1)
        )
    ),
    'S' to listOf( // S-shape
        arrayOf(
            intArrayOf(0, 1, 1),
            intArrayOf(1, 1, 0),
            intArrayOf(0, 0, 0),
        ),
        arrayOf(
            intArrayOf(0, 1, 0),
            intArrayOf(0, 1, 1),
            intArrayOf(0, 0, 1),
        ),
        arrayOf(
            intArrayOf(0, 0, 0),
            intArrayOf(0, 1, 1),
            intArrayOf(1, 1, 0),
        ),
        arrayOf(
            intArrayOf(1, 0, 0),
            intArrayOf(1, 1, 0),
            intArrayOf(0, 1, 0),
        ),
    ),
    'Z' to listOf( // Z-shape
        arrayOf(
            intArrayOf(1, 1, 0),
            intArrayOf(0, 1, 1),
            intArrayOf(0, 0, 0),
        ),
        arrayOf(
            intArrayOf(0, 0, 1),
            intArrayOf(0, 1, 1),
            intArrayOf(0, 1, 0),
        ),
        arrayOf(
            intArrayOf(0, 0, 0),
            intArrayOf(1, 1, 0),
            intArrayOf(0, 1, 1),
        ),
        arrayOf(
            intArrayOf(0, 1, 0),
            intArrayOf(1, 1, 0),
            intArrayOf(1, 0, 0),
        ),
    )
)

val tetrominoShapes = listOf(
    Tetromino(
        shape = matrixShapes['I']!!.random(),
        color = Color.Cyan
    ),
    Tetromino(
        shape = matrixShapes['O']!!.random(),
        color = Color.Yellow
    ),
    Tetromino(
        shape = matrixShapes['T']!!.random(),
        color = Color.Magenta
    ),
    Tetromino(
        shape = matrixShapes['L']!!.random(),
        color = Color.Blue
    ),
    Tetromino(
        shape = matrixShapes['J']!!.random(),
        color = Color.Black
    ),
    Tetromino(
        shape = matrixShapes['S']!!.random(),
        color = Color.Green
    ),
    Tetromino(
        shape = matrixShapes['Z']!!.random(),
        color = Color.Red
    )
)

