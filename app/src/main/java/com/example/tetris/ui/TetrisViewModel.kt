package com.example.tetris.ui

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
import com.example.tetris.model.clearRows
import com.example.tetris.model.deepCopy
import com.example.tetris.model.getShadowTetromino
import com.example.tetris.model.getWidthHeight
import com.example.tetris.model.tryMove
import com.example.tetris.model.printBoard
import com.example.tetris.model.printTetromino
import com.example.tetris.model.rotate

const val BOARD_WIDTH = 10
const val BOARD_HEIGHT = 20
val CELL_SIZE_DP = 30.dp
class TetrisViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(createInitialUiState())
    private val _isGameOver = MutableStateFlow(false)
    val isGameOver: StateFlow<Boolean> = _isGameOver

    private var _isGameRunning = MutableStateFlow(true)
    val isGameRunning: StateFlow<Boolean> = _isGameRunning

    val uiState: StateFlow<TetrisUiState> = _uiState

    init {
        startGameLoop()
    }

    private fun createInitialUiState(): TetrisUiState {
        val randomTetromino = tetrominoShapes.random()
        return TetrisUiState(
            currentTetromino = randomTetromino,
            shadowTetromino = randomTetromino,
            nextTetromino = tetrominoShapes.random(),
            gameBoard = GameBoard(BOARD_WIDTH, BOARD_HEIGHT),
            score = 0
        )
    }
    fun restart() {
        _isGameRunning.value = true
        _isGameOver.value = false
        _uiState.value = createInitialUiState()
    }

    fun toggleGameRunning() {
        _isGameRunning.value = !_isGameRunning.value
    }

    fun rotateTetromino() {
        _uiState.value.run {
            val rotatedShape = currentTetromino.rotate()
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
                    _uiState.update { it.copy(gameBoard = GameBoard(BOARD_WIDTH, BOARD_HEIGHT)) }
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
        var clearedRows = gameBoard.clearRows()
        _uiState.update { it.copy(score = _uiState.value.score + (clearedRows * 100)) }
        _uiState.update { it.copy(gameBoard = gameBoard) }
        gameBoard.printBoard()
    }

    private fun updateTetromino(newTetromino: Tetromino) {
        _uiState.update { it.copy(currentTetromino = newTetromino) }
        _uiState.value.run {
            val newShadowTetromino = currentTetromino.getShadowTetromino(gameBoard)
            _uiState.update { it.copy(shadowTetromino = newShadowTetromino) }
        }
    }
    private fun initNewTetromino() {
        _uiState.update {
            it.copy(
                currentTetromino = _uiState.value.nextTetromino,
                shadowTetromino = _uiState.value.nextTetromino,
                nextTetromino = tetrominoShapes.random()
            )
        }
    }
}

data class TetrisUiState(
    val gameBoard: GameBoard,
    val currentTetromino: Tetromino,
    val nextTetromino: Tetromino,
    val shadowTetromino: Tetromino,
    val score: Int = 0
)