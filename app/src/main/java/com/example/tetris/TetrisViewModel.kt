package com.example.tetris

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
import com.example.tetris.model.printBoard
import com.example.tetris.model.printTetromino

const val BOARD_WIDTH = 10
const val BOARD_HEIGHT = 20
val CELL_SIZE_DP = 30.dp
class TetrisViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(
        TetrisUiState()
    )
    private val _isGameOver = MutableStateFlow(false)
    val isGameOver: StateFlow<Boolean> = _isGameOver

    private var _isGameRunning = MutableStateFlow(true)
    val isGameRunning: StateFlow<Boolean> = _isGameRunning

    val uiState: StateFlow<TetrisUiState> = _uiState

    init {
        startGameLoop()
    }

    fun toggleGameRunning() {
        _isGameRunning.value = !_isGameRunning.value
    }

    fun rotateTetromino() {
        _uiState.value.run {
            val n = currentTetromino.shape.size
            val m = currentTetromino.shape[0].size
            val rotatedShape = Array(m) {IntArray(n)}

            for (row in currentTetromino.shape.indices) {
                for (column in currentTetromino.shape[row].indices) {
                    rotatedShape[column][n - row - 1] = currentTetromino.shape[row][column]
                }
            }

            val rotatedTetromino = currentTetromino.copy(shape = rotatedShape)
            // is it possible to rotate on the same place
            if (rotatedTetromino.tryMove(gameBoard)) {
                updateTetromino(rotatedTetromino)
            }
        }
    }

    private fun startGameLoop() {
        viewModelScope.launch {
            while (!_isGameOver.value) {
                if (_isGameRunning.value) {
                    moveTetromino(0, 1)
                }
                delay(500)
            }
        }
    }

    fun moveTetromino(x: Int, y: Int) {
        val xPos = _uiState.value.currentTetromino.xPos
        val yPos = _uiState.value.currentTetromino.yPos
        val newTetromino = _uiState.value.currentTetromino.copy(xPos = x + xPos, yPos = y + yPos)

        _uiState.value.run {
            if (!newTetromino.tryMove(gameBoard)) {
                // Tetromino can't move out of game board width
                if (x != 0) return

                if (!gameBoard.placeTetromino(currentTetromino)) {
                    _isGameOver.value = true
                    return
                }

                updateGameBoard(gameBoard)
                initNewTetromino()
            } else {
                updateTetromino(newTetromino)
            }
        }
    }

    private fun updateGameBoard(gameBoard: GameBoard) {
        var clearedRows: Int = 0
        for (rRow in gameBoard.cells.indices.reversed()) {
            if (gameBoard.cells[rRow].all { it != null }) {
                gameBoard.cells[rRow].fill(null)
                for (r in rRow downTo 1) {
                    gameBoard.cells[r] = gameBoard.cells[r - 1]
                }
                gameBoard.cells[0] = Array(gameBoard.width) { null }
                clearedRows++
            }
        }
        _uiState.update { it.copy(score = _uiState.value.score + (clearedRows * 100)) }
        _uiState.update { it.copy(gameBoard = gameBoard) }
        gameBoard.printBoard()
    }

    private fun updateTetromino(newTetromino: Tetromino) {
        _uiState.update { it.copy(currentTetromino = newTetromino) }
    }

    private fun initNewTetromino() {
        _uiState.update {
            it.copy(
                currentTetromino = _uiState.value.nextTetromino,
                nextTetromino = tetrominoShapes.random()
            )
        }
        _uiState.value.nextTetromino.printTetromino()
    }
}

data class TetrisUiState(
    val gameBoard: GameBoard = GameBoard(BOARD_WIDTH, BOARD_HEIGHT),
    val currentTetromino: Tetromino = tetrominoShapes.random(),
    val nextTetromino: Tetromino = tetrominoShapes.random(),
    val score: Int = 0
)
