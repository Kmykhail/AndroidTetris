package com.example.tetris.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun TetrisGameScreen(
    viewModel: TetrisViewModel = viewModel()
) {
    val isGameRunning by viewModel.isGameRunning.collectAsState()
    val isGameOver by viewModel.isGameOver.collectAsState()
    val uiState by viewModel.uiState.collectAsState()

    if (isGameOver) {
        viewModel.restart()
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 20.dp)
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
        // Game board
        Spacer(modifier = Modifier.height(20.dp))
        Card(
            shape = RoundedCornerShape(2.dp),
            modifier = Modifier
                .shadow(elevation = 8.dp)
        ) {
            GameBoardComposable(
                gameBoard = uiState.gameBoard,
                currentTetromino = uiState.currentTetromino,
            )
        }
        // Buttons
        Spacer(modifier = Modifier.height(20.dp))
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