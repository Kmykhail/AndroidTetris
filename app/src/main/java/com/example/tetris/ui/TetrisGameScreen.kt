package com.example.tetris.ui

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.TextField
import com.example.tetris.TetrisViewModel
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
import com.example.tetris.CELL_SIZE_DP
import com.example.tetris.model.GameBoard
import com.example.tetris.model.Tetromino

@Composable
fun TetrisGameScreen(
    viewModel: TetrisViewModel = viewModel()
) {
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
                        .shadow(elevation = 8.dp)
                ) {
                    NextTetromino(nextTetromino = uiState.nextTetromino)
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
            Spacer(modifier = Modifier.width(8.dp))
            IconButton(
                onClick = {viewModel.toggleGameRunning()},
                modifier = Modifier.background(MaterialTheme.colorScheme.primary)
            ) {
                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurface
                )
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
                tetromino = uiState.currentTetromino
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        ControlButtons(
            viewModel = viewModel,
        )
    }
}

@Composable
fun GameBoardComposable(gameBoard: GameBoard, tetromino: Tetromino, cellSize: Dp = CELL_SIZE_DP, padding: Dp = 4.dp) {
    // Draw the game board
    Canvas(modifier = Modifier
        .size(cellSize * gameBoard.width, cellSize * gameBoard.height)
        .background(Color.LightGray)
        .padding(4.dp)
    ) {
        val cornerRadius = cellSize.toPx() / 4

        gameBoard.cells.forEachIndexed { rowIndex, rows ->
            rows.forEachIndexed { columnIndex, cell ->
                if (cell != null) {
                    drawRoundRect(
                        color = cell,
                        topLeft = Offset(
                            x = columnIndex * cellSize.toPx() ,
                            y = rowIndex * cellSize.toPx()
                        ),
                        size = Size(cellSize.toPx() - padding.toPx(), cellSize.toPx() - padding.toPx()),
                        cornerRadius = CornerRadius(cornerRadius, cornerRadius)
                    )
                }
            }
        }

        tetromino.shape.forEachIndexed { rowIndex, rows ->
            rows.forEachIndexed { columnIndex, cell ->
                if (cell == 1) {
                    drawRoundRect(
                        color = tetromino.color,
                        topLeft = Offset(
                            x = (tetromino.xPos + columnIndex) * cellSize.toPx(),
                            y = (tetromino.yPos + rowIndex) * cellSize.toPx()
                        ),
                        size = Size(cellSize.toPx(), cellSize.toPx()),
                        cornerRadius = CornerRadius(cornerRadius, cornerRadius)
                    )
                }
            }
        }
    }
}
@Composable
fun NextTetromino(nextTetromino: Tetromino, cellSize: Dp = 15.dp) {
    val cellSizePx = with(LocalDensity.current) { cellSize.toPx() }
    val cornerRadius = cellSizePx / 4

    Canvas(modifier = Modifier
        .size(cellSize * 6, cellSize * 4)
        .background(Color.LightGray)
        .padding(vertical = 2.dp)
    ) {
        val canvasPxSize = Pair(size.width, size.height)

        val tetrominoWidth = nextTetromino.shape[0].size
        val tetrominoHeight = nextTetromino.shape.size

        val startXOffset = (canvasPxSize.first - (tetrominoWidth * cellSizePx)) / 2
        val startYOffset = (canvasPxSize.second - (tetrominoHeight * cellSizePx)) / 2

        nextTetromino.shape.forEachIndexed { rowIndex, rows ->
            rows.forEachIndexed { columnIndex, cell ->
                if (cell == 1) {
                    drawRoundRect(
                        color = nextTetromino.color,
                        topLeft = Offset(
                            x = startXOffset + columnIndex * cellSizePx,
                            y = startYOffset + rowIndex * cellSizePx
                        ),
                        size = Size(cellSizePx, cellSizePx),
                        cornerRadius = CornerRadius(cornerRadius, cornerRadius)
                    )
                }
            }
        }
    }
}

@Composable
fun ControlButtons(
    viewModel: TetrisViewModel,
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween
    ){
        IconButton(
            onClick = {viewModel.moveTetromino(-1, 0)},
            modifier = Modifier
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primary)
                .size(40.dp)
        ) {
            Icon(
                imageVector = Icons.Default.KeyboardArrowLeft,
                contentDescription = "Left Arrow",
                tint = MaterialTheme.colorScheme.onSurface
            )
        }
        Spacer(modifier = Modifier.width(8.dp))
        IconButton(
            onClick = {viewModel.moveTetromino(1, 0)},
            modifier = Modifier
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primary)
                .size(40.dp)
        ) {
            Icon(
                imageVector = Icons.Default.KeyboardArrowRight,
                contentDescription = "Right arrow",
                tint = MaterialTheme.colorScheme.onSurface
            )
        }
        Spacer(modifier = Modifier.width(8.dp))
        IconButton(
            onClick = {viewModel.moveTetromino(0, 1)},
            modifier = Modifier
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primary)
                .size(40.dp)
        ) {
            Icon(
                imageVector = Icons.Default.KeyboardArrowDown,
                contentDescription = "Down arrow",
                tint = MaterialTheme.colorScheme.onSurface
            )
        }
        Spacer(modifier = Modifier.width(8.dp))
        IconButton(
            onClick = {viewModel.rotateTetromino()},
            modifier = Modifier
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primary)
                .size(40.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Refresh,
                contentDescription = "Rotate",
                tint = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@Composable
@Preview
fun xxx() {
    TetrisGameScreen()
}