package com.example.tetris

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.currentRecomposeScope
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tetris.model.GameBoard
import com.example.tetris.model.Tetromino
import com.example.tetris.model.placeTetromino
import com.example.tetris.model.tetrominoShapes
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import androidx.compose.ui.unit.dp
import com.example.tetris.model.tryMove
import com.example.tetris.model.CellMatrix
import com.example.tetris.model.Matrix

const val BOARD_WIDTH = 10
const val BOARD_HEIGHT = 20
val CELL_SIZE_DP = 30.dp
class TetrisViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(
        TetrisUiState()
    )
    private var isGameOver: Boolean = false

    val uiState: StateFlow<TetrisUiState> = _uiState

    init {
        startGameLoop()
    }

    fun rotateTetromino() {
        // Implement rotation logic and position validation
        _uiState.value.run {
            val n = currentTetromino.shape.size
            val m = currentTetromino.shape[0].size
            val rotatedShape = Array(m) {IntArray(n)}

            for (row in currentTetromino.shape.indices) {
                for (column in currentTetromino.shape[row].indices) {
                    rotatedShape[column][n - row - 1] = currentTetromino.shape[row][column]
                }
            }
            updateTetromino(currentTetromino.copy(shape = rotatedShape))
        }
    }

    private fun startGameLoop() {
        viewModelScope.launch {
            do {
                Log.d("Timer", "click")
                delay(1000)
                updateGame()
            } while (!isGameOver)
        }
    }

    fun moveTetromino(x: Int, y: Int) {
        val xPos = _uiState.value.currentTetromino.xPos
        val yPos = _uiState.value.currentTetromino.yPos
        val newTetromino = _uiState.value.currentTetromino.copy(xPos = x + xPos, yPos = y + yPos)

        _uiState.value.run {
            if (!newTetromino.tryMove(gameBoard)) {
                Log.d("tetromino", "Check!!!")
                if (!gameBoard.placeTetromino(currentTetromino)) {
                    isGameOver = true
                    return
                }
                updateGameBoard(gameBoard)
                //clearFiled
                initNewTetromino()
            } else {
                Log.d("tetromino", "(1)yPos:${newTetromino.yPos}")
                updateTetromino(newTetromino)
            }
        }
    }

    private fun updateGameBoard(newGameBoard: GameBoard) {
        _uiState.update { it.copy(gameBoard = newGameBoard) }
    }

    private fun updateTetromino(newTetromino: Tetromino) {
        _uiState.update { it.copy(currentTetromino = newTetromino) }
    }

    private fun initNewTetromino() {
        _uiState.update { it.copy(currentTetromino = tetrominoShapes.random()) }
    }

    private fun updateGame() {
        moveTetromino(0, 1)
    }
}

data class TetrisUiState(
    val gameBoard: GameBoard = GameBoard(BOARD_WIDTH, BOARD_HEIGHT),
    val currentTetromino: Tetromino = tetrominoShapes.random()
)
