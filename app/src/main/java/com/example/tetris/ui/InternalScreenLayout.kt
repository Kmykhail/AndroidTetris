package com.example.tetris.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.tetris.R

@Composable
fun InternalScreenLayout(
    viewModel: TetrisViewModel,
    uiState: TetrisUiState,
    isGameRunning: Boolean,
    cellSize: Dp
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
    ) {
        Card(
            shape = RoundedCornerShape(dimensionResource(R.dimen.card_corner_radius_small)),
            modifier = Modifier
                .shadow(dimensionResource(R.dimen.shadow_elevation))
        ) {
            Row(
                modifier = Modifier
                    .width(cellSize * uiState.gameBoard.width)
                    .height(cellSize * 3)
                    .background(MaterialTheme.colorScheme.secondaryContainer),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                StatusSection(label = "Level", value = "1")
                NextTetromino(nextTetromino = uiState.nextTetromino, cellSize = cellSize / 2)
                StatusSection(label = "Score", value = uiState.score.toString())
            }
        }
        // Game board
        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_medium)))
        Card(
            shape = RoundedCornerShape(dimensionResource(R.dimen.card_corner_radius_small)),
            modifier = Modifier
                .shadow(dimensionResource(R.dimen.shadow_elevation))
        ) {
            GameBoardComposable(
                gameBoard = uiState.gameBoard,
                currentTetromino = uiState.currentTetromino,
                cellSize = cellSize
            )
        }
        // Buttons
        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_large)))
        ControlButtons(
            isGameRunning = isGameRunning,
            viewModel = viewModel,
            btSize = cellSize * 3
        )
    }
}

@Composable
fun StatusSection(
    label: String,
    value: String,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(horizontal = dimensionResource(R.dimen.padding_small))
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.titleMedium
        )
        Box(
            modifier = Modifier
                .height(40.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = value,
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center
            )
        }
    }
}
