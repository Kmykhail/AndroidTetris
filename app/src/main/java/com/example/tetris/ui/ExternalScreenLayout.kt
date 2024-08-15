package com.example.tetris.ui

import androidx.compose.foundation.background
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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.tetris.R

@Composable
fun ExternalScreenLayout(
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
        Row(
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.Center
        ) {
            Column(
                modifier = Modifier
                    .padding(top = 100.dp)
            ) {
                LeftButtonsLayout(
                    isGameRunning = isGameRunning,
                    onLeftClick = { viewModel.moveLeft() },
                    onRotateClick = { viewModel.rotateTetromino() },
                    onPlayPauseClick = { viewModel.toggleGameRunning() },
                    btSize = cellSize * 4
                )
            }
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(horizontal = dimensionResource(R.dimen.padding_medium))
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
            }
            Column(
                modifier = Modifier
                    .padding(top = 100.dp)
            ) {
                RightButtonsLayout(
                    onRightClick = { viewModel.moveRight() },
                    onDownClick = { viewModel.moveDown() },
                    onDownInstantlyClick = { viewModel.moveDownInstantly() },
                    btSize = cellSize * 4
                )
            }
        }
    }
}