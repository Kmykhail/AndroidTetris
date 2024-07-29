package com.example.tetris.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tetris.model.GameBoard
import com.example.tetris.model.Tetromino

@Composable
fun TetrisGameScreen(
    viewModel: TetrisViewModel = viewModel()
) {
    val isGameRunning by viewModel.isGameRunning.collectAsState()
    val uiState by viewModel.uiState.collectAsState()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
    ) {
        Row(
            horizontalArrangement = Arrangement.Start,
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "NEXT")
                Card(
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                ) {
                    NextTetromino(
                        gameBoard = uiState.gameBoard,
                        nextTetromino = uiState.nextTetromino
                    )
                }
            }
            Spacer(modifier = Modifier.width(8.dp))
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "SCORE")
                Card(
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .shadow(elevation = 8.dp)
                ) {
                    Text(
                        text = uiState.score.toString(),
                        style = MaterialTheme.typography.titleLarge,
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Card(
            shape = RoundedCornerShape(2.dp),
            modifier = Modifier
                .shadow(elevation = 8.dp)
        ) {
            GameBoardComposable(
                gameBoard = uiState.gameBoard,
                currentTetromino = uiState.currentTetromino,
                shadowTetromino = uiState.shadowTetromino
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        ControlButtons(
            isGameRunning = isGameRunning,
            viewModel = viewModel,
        )
    }
}

@Composable
@Preview
fun TetrisGameScreenPreview() {
    TetrisGameScreen()
}