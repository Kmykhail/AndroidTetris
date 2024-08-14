package com.example.tetris.ui

import android.app.Activity
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
    val cellSize = when (screenType) {
        ScreenType.InternalPortraitScreen -> dimensionResource(R.dimen.internal_portrait)
        ScreenType.ExternalScreen -> dimensionResource(R.dimen.external_portrait)
//        Landscape orientation is disabled
    }

    Log.d("GameTetris", "cellSize:$cellSize")
    if (isGameOver) {
        viewModel.restart()
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 2.dp)
    ) {
        Card(
            shape = RoundedCornerShape(4.dp),
            modifier = Modifier
                .shadow(elevation = 8.dp)
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
            shape = RoundedCornerShape(4.dp),
            modifier = Modifier
                .shadow(elevation = 8.dp)
        ) {
            GameBoardComposable(
                gameBoard = uiState.gameBoard,
                currentTetromino = uiState.currentTetromino,
                cellSize = cellSize
            )
        }
        // Buttons
        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_medium)))
        ControlButtons(
            isGameRunning = isGameRunning,
            viewModel = viewModel,
        )
    }
}

@Composable
fun StatusSection(label: String, value: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(horizontal = 4.dp)
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.titleMedium
        )
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .background(Color.LightGray)
                .width(70.dp)
                .height(40.dp)
            ,
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = value,
                fontSize = 16.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
@Preview
fun TetrisGameScreenPreview() {
    TetrisGameScreen(ScreenType.InternalPortraitScreen)
}