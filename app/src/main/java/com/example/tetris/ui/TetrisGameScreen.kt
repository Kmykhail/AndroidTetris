package com.example.tetris.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import com.example.tetris.TetrisViewModel
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
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
        Card(
//            elevation = CardDefaults.elevatedCardElevation(20.dp),
            shape = RoundedCornerShape(4.dp),
            modifier = Modifier
                .shadow(elevation = 20.dp, shape = RoundedCornerShape(12.dp))

        ) {
//            TetrominoComposable(tetromino = uiState.currentTetromino)
            GameBoardComposable(gameBoard = uiState.gameBoard, tetromino = uiState.currentTetromino)
        }

    }
//    Box(
//        modifier = Modifier
//        .fillMaxSize()
////        .size(CELL_SIZE_DP * uiState.gameBoard.width, CELL_SIZE_DP * uiState.gameBoard.height)
//    ) {
//        GameBoardComposable(gameBoard = uiState.gameBoard)
//        TetrominoComposable(tetromino = uiState.currentTetromino)
//        ControlButtons(viewModel = viewModel)
//    }
}

@Composable
fun GameBoardComposable(gameBoard: GameBoard, tetromino: Tetromino, cellSize: Dp = CELL_SIZE_DP) {
    // Draw the game board
    Canvas(modifier = Modifier
        .size(cellSize * gameBoard.width, cellSize * gameBoard.height)
        .background(Color.LightGray)
        .padding(horizontal = 4.dp, vertical = 4.dp)
    ) {
        gameBoard.cells.forEachIndexed { rowIndex, rows ->
            rows.forEachIndexed { columnIndex, cell ->
                if (cell != null) {
                    drawRect(
                        color = cell,
                        topLeft = Offset(
                            x = columnIndex * cellSize.toPx() ,
                            y = rowIndex * cellSize.toPx()
                        ),
                        size = Size(cellSize.toPx() , cellSize.toPx() )
                    )
                }
            }
        }

        tetromino.shape.forEachIndexed { rowIndex, rows ->
            rows.forEachIndexed { columnIndex, cell ->
                if (cell == 1) {
                    drawRect(
                        color = tetromino.color,
                        topLeft = Offset(
                            x = (tetromino.xPos + columnIndex) * cellSize.toPx(),
                            y = (tetromino.yPos + rowIndex) * cellSize.toPx()
                        ),
                        size = Size(cellSize.toPx(), cellSize.toPx())
                    )
                }
            }
        }
    }

//    // Draw current tetromino
//    Canvas(modifier = Modifier
//        .size(cellSize * tetromino.shape[0].size, cellSize * tetromino.shape.size)
//    ) {
//        tetromino.shape.forEachIndexed { rowIndex, rows ->
//            rows.forEachIndexed { columnIndex, cell ->
//                if (cell == 1) {
//                    drawRect(
//                        color = tetromino.color,
//                        topLeft = Offset(
//                            x = (tetromino.xPos + columnIndex) * cellSize.toPx(),
//                            y = (tetromino.yPos + rowIndex) * cellSize.toPx()
//                        ),
//                        size = Size(cellSize.toPx(), cellSize.toPx())
//                    )
//                }
//            }
//        }
//    }
}
@Composable
fun ControlButtons(
    viewModel: TetrisViewModel
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ){
        Button(onClick = {viewModel.moveTetromino(-1, 0)} ) {
            Text("<")
        }
        Button(onClick = {viewModel.moveTetromino(1, 0)}) {
            Text(">")
        }
        Button(onClick = {viewModel.moveTetromino(0, 1)}) {
            Text("D")
        }
        Button(onClick = {viewModel.rotateTetromino()}) {
            Text("R")
        }
    }
}

@Composable
@Preview
fun xxx() {
    TetrisGameScreen()
}