package com.example.tetris.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tetris.model.GameBoard
import com.example.tetris.model.Tetromino
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import androidx.compose.ui.unit.dp
import com.example.tetris.model.getRandomTetromino

const val BOARD_WIDTH = 10
const val BOARD_HEIGHT = 20

class TetrisViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(createInitialUiState())
    private val _isGameOver = MutableStateFlow(false)
    private val _isGameRunning = MutableStateFlow(true)

    val isGameRunning: StateFlow<Boolean> = _isGameRunning
    val uiState: StateFlow<TetrisUiState> = _uiState
    val isGameOver: StateFlow<Boolean> = _isGameOver

    init {
        startGameLoop()
    }

    private fun createInitialUiState(): TetrisUiState {
        return TetrisUiState(
            currentTetromino = getRandomTetromino(),
            nextTetromino = getRandomTetromino(),
            gameBoard = GameBoard(BOARD_WIDTH, BOARD_HEIGHT),
            score = 0
        )
    }
    fun restart() {
        _isGameRunning.value = true
        _isGameOver.value = false
        _uiState.value = createInitialUiState()
    }

    private fun startGameLoop() {
        viewModelScope.launch {
            while (!_isGameOver.value) {
                if (_isGameRunning.value) {
                    moveDown()
                }
                delay(500)
            }
        }
    }

    fun toggleGameRunning() {
        _isGameRunning.value = !_isGameRunning.value
    }

    fun rotateTetromino() {
        if (!_isGameRunning.value) return
        _uiState.value.run {
            currentTetromino.rotate(gameBoard)
        }
    }

    fun moveLeft() {
        if (!_isGameRunning.value) return
        _uiState.value.run {
            currentTetromino.tryMove(-1, 0, gameBoard)
        }
    }

    fun moveRight() {
        if (!_isGameRunning.value) return
        _uiState.value.run {
            currentTetromino.tryMove(1, 0, gameBoard)
        }
    }

    fun moveDownInstantly() {
        if (!_isGameRunning.value) return
        _uiState.value.run {
            while (currentTetromino.tryMove(0, 1, gameBoard));
            moveDown()
        }
    }
    fun moveDown() {
        if (!_isGameRunning.value) return
        _uiState.value.run {
            if (!currentTetromino.tryMove(0, 1, gameBoard)) {
                if (!gameBoard.placeTetromino(currentTetromino)) {
                    _isGameOver.value = true
                    _isGameRunning.value = false
                    return
                }
                score += gameBoard.clearCompletedRows() * 100
                initNewTetromino()
            }
        }
    }

    private fun initNewTetromino() {
        _uiState.value.run {
            nextTetromino.updateShadow(gameBoard)
            _uiState.update { it.copy(
                currentTetromino = nextTetromino,
                nextTetromino = getRandomTetromino()
            ) }
        }
    }
}

data class TetrisUiState(
    val gameBoard: GameBoard,
    val currentTetromino: Tetromino,
    val nextTetromino: Tetromino,
    var score: Int = 0
)