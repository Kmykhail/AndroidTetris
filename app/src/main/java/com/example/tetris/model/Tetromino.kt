package com.example.tetris.model

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import com.example.tetris.ui.BOARD_HEIGHT
import com.example.tetris.ui.BOARD_WIDTH

typealias Matrix = Array<IntArray>
class Tetromino(initialX: Int, initialY: Int, initialMatrix: Matrix, initialColor: Color) {
    var x by mutableStateOf(initialX)
        private set

    var y by mutableStateOf(initialY)
        private set

    var matrix by mutableStateOf(initialMatrix)
        private set

    var color by mutableStateOf(initialColor)
        private set

    var shadowX by mutableStateOf(x)
        private set
    var shadowY by mutableStateOf(BOARD_HEIGHT - matrix.size)
        private set

    fun tryMove(xPos: Int, yPos: Int, gameBoard: GameBoard, matrix: Matrix = this.matrix): Boolean {
        val isValid = collisionCheck(x + xPos, y + yPos, gameBoard, matrix)
        if (isValid) {
            x += xPos
            y += yPos
            shadowCollision(gameBoard, matrix)
        }
        return isValid
    }

    private fun shadowCollision(gameBoard: GameBoard, matrix: Matrix) {
        var tmp = shadowY
        while (collisionCheck(x, tmp, gameBoard, matrix)) {
            tmp += 1
        }
        if (tmp != shadowY) {
            shadowY = tmp - 1
        }  else {
            while (!collisionCheck(x, --tmp, gameBoard, matrix));
            shadowY = tmp
        }
        shadowX = x
    }
    private fun collisionCheck(x: Int, y: Int, gameBoard: GameBoard, matrix: Matrix): Boolean {
        for (row in matrix.indices) {
            for (column in matrix[row].indices) {
                val boardCellX = x + column
                val boardCellY = y + row

                if (matrix[row][column] > 0) {
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
    fun rotate(gameBoard: GameBoard) {
        if (color == Color.Yellow) { // skip for square
            return
        }

        val n = matrix.size
        val m = matrix[0].size
        val rotatedMatrix = Array(m) {IntArray(n)}

        matrix.forEachIndexed { rowIndex, rows ->
            rows.forEachIndexed { columnIndex, value ->
                rotatedMatrix[columnIndex][n - rowIndex - 1] = value
            }
        }

        if (tryMove(0,0, gameBoard, rotatedMatrix)) {
            matrix = rotatedMatrix
        }
    }

    fun printTetromino(info: String, matrix: Matrix = this.matrix){
        Log.d("GameTetris" ,"${info} x: ${x}, y: ${y}\nprintTetromino:\n${matrix.joinToString(separator = "\n"){row ->
            row.joinToString(separator = ""){ cell -> cell.toString() }
        }}")
    }

    fun updateShadow(gameBoard: GameBoard) {
        shadowY = y
        while (collisionCheck(x, shadowY + 1, gameBoard, matrix)) {
            shadowY++
        }
    }
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

fun getRandomTetromino(): Tetromino  {
    val (shape, matrixList) = matrixShapes.entries.random()
    val matrix = matrixList.random()
    val tetromino = Tetromino(
        initialX = (BOARD_WIDTH - matrix[0].size) / 2,
        initialY = 0,
        initialMatrix = matrix,
        initialColor = when(shape) {
            'I' -> Color.Cyan
            'O' -> Color.Yellow
            'T' -> Color.Magenta
            'L' -> Color.Blue
            'J' -> Color(0xFFFFA500) // orange
            'S' -> Color.Green
            'Z' -> Color.Red
            else -> Color.Black
        }
    )
    return tetromino
}