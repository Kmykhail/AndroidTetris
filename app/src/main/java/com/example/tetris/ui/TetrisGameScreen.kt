package com.example.tetris.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tetris.R
import com.example.tetris.utils.ScreenType

@Composable
fun TetrisGameScreen(
    screenType: ScreenType,
    viewModel: TetrisViewModel = viewModel()
) {
    val isGameRunning by viewModel.isGameRunning.collectAsState()
    val isGameOver by viewModel.isGameOver.collectAsState()
    val uiState by viewModel.uiState.collectAsState()

    if (isGameOver) {
        viewModel.restart()
    }
    when (screenType) {
        ScreenType.InternalPortraitScreen -> {
            InternalScreenLayout(
                viewModel = viewModel,
                uiState = uiState,
                isGameRunning = isGameRunning,
                cellSize = dimensionResource(R.dimen.internal_portrait)
            )
        }
        ScreenType.ExternalScreen -> {
            ExternalScreenLayout(
                viewModel = viewModel,
                uiState = uiState,
                isGameRunning = isGameRunning,
                cellSize = dimensionResource(R.dimen.external_portrait)
            )
        }
    }
}

@Composable
@Preview
fun ExternalScreenLayoutPreview() {
    TetrisGameScreen(ScreenType.ExternalScreen)
}

@Composable
@Preview
fun InternalScreenLayoutPreview() {
    TetrisGameScreen(ScreenType.InternalPortraitScreen)
}
