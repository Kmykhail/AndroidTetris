package com.example.tetris.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.KeyboardDoubleArrowDown
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.RotateRight
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.Dp
import com.example.tetris.R

@Composable
fun ControlButtons(
    isGameRunning: Boolean,
    viewModel: TetrisViewModel,
    btSize: Dp
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row {
                IconHandler(
                    imageVector = Icons.Default.KeyboardArrowLeft,
                    descriptor = "Left arrow",
                    onButtonClick = { viewModel.moveLeft() },
                    btSize = btSize
                )
                Spacer(modifier = Modifier.width(dimensionResource(R.dimen.padding_ultra_large)))
                IconHandler(
                    imageVector = Icons.Default.KeyboardArrowDown,
                    descriptor = "Down arrow",
                    onButtonClick = { viewModel.moveDown()},
                    btSize = btSize
                )
                Spacer(modifier = Modifier.width(dimensionResource(R.dimen.padding_ultra_large)))
                IconHandler(
                    imageVector = Icons.Default.KeyboardArrowRight,
                    descriptor = "Right arrow",
                    onButtonClick = { viewModel.moveRight() },
                    btSize = btSize
                )
            }
            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_medium)))
            Row {
                IconHandler(
                    imageVector = Icons.Default.RotateRight,
                    descriptor = "Rotate",
                    onButtonClick = { viewModel.rotateTetromino() },
                    btSize = btSize
                )
                Spacer(modifier = Modifier.width(dimensionResource(R.dimen.padding_ultra_large)))
                IconHandler(
                    imageVector = Icons.Default.KeyboardDoubleArrowDown,
                    descriptor = "Double down arrow",
                    onButtonClick = { viewModel.moveDownInstantly()},
                    btSize = btSize
                )
                Spacer(modifier = Modifier.width(dimensionResource(R.dimen.padding_ultra_large)))
                IconHandler(
                    imageVector = if (isGameRunning) Icons.Filled.PlayArrow else Icons.Filled.Pause,
                    descriptor = "Play/Pause",
                    onButtonClick = { viewModel.toggleGameRunning() },
                    btSize = btSize
                )
            }
        }
    }
}

@Composable
fun LeftButtonsLayout(
    isGameRunning: Boolean,
    onLeftClick: () -> Unit,
    onRotateClick: () -> Unit,
    onPlayPauseClick: () -> Unit,
    btSize: Dp
) {
    IconHandler(
        imageVector = Icons.Default.KeyboardArrowLeft,
        descriptor = "Left arrow",
        onButtonClick = onLeftClick,
        btSize = btSize
    )
    Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_medium)))
    IconHandler(
        imageVector = Icons.Default.RotateRight,
        descriptor = "Left arrow",
        onButtonClick = onRotateClick,
        btSize = btSize
    )
    Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_medium)))
    IconHandler(
        imageVector = if (isGameRunning) Icons.Filled.PlayArrow else Icons.Filled.Pause,
        descriptor = "Play/Pause",
        onButtonClick = onPlayPauseClick,
        btSize = btSize
    )
}

@Composable
fun RightButtonsLayout(
    onRightClick: () -> Unit,
    onDownClick: () -> Unit,
    onDownInstantlyClick: () -> Unit,
    btSize: Dp
) {
    IconHandler(
        imageVector = Icons.Default.KeyboardArrowRight,
        descriptor = "Right arrow",
        onButtonClick = onRightClick,
        btSize = btSize
    )
    Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_medium)))
    IconHandler(
        imageVector = Icons.Default.KeyboardArrowDown,
        descriptor = "Down arrow",
        onButtonClick = onDownClick,
        btSize = btSize
    )
    Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_medium)))
    IconHandler(
        imageVector = Icons.Default.KeyboardDoubleArrowDown,
        descriptor = "Double down arrow",
        onButtonClick = onDownInstantlyClick,
        btSize = btSize
    )
}

@Composable
fun IconHandler(
    imageVector: ImageVector,
    descriptor: String,
    onButtonClick: () -> Unit,
    btSize: Dp,
    modifier: Modifier = Modifier
        .clip(RoundedCornerShape(dimensionResource(R.dimen.card_corner_radius_large)))
        .background(MaterialTheme.colorScheme.secondaryContainer)
        .size(btSize)
) {
    IconButton(
        onClick = onButtonClick,
        modifier = modifier
    ) {
        Icon(
            imageVector = imageVector,
            contentDescription = descriptor,
        )
    }
}