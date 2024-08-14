package com.example.tetris

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.material3.Surface
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.window.layout.WindowMetricsCalculator
import com.example.tetris.ui.TetrisGameScreen
import com.example.tetris.ui.theme.TetrisTheme
import com.example.tetris.utils.ScreenType

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TetrisTheme {
                val layoutDirection = LocalLayoutDirection.current
                Surface(
                    modifier = Modifier
                        .padding(
                            start = WindowInsets.safeDrawing.asPaddingValues().calculateStartPadding(layoutDirection),
                            end = WindowInsets.safeDrawing.asPaddingValues().calculateEndPadding(layoutDirection)
                        ),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val windowSize = calculateWindowSizeClass(this)
                    val windowMetrics = WindowMetricsCalculator.getOrCreate().computeCurrentWindowMetrics(this)
                    val width = windowMetrics.bounds.width()
                    val height = windowMetrics.bounds.height()
                    val aspectRatio = width.toFloat() / height

                    var screenType = ScreenType.InternalPortraitScreen
                    when (windowSize.widthSizeClass) {
                        WindowWidthSizeClass.Compact -> {
                            if (aspectRatio > 0.9f && aspectRatio < 1.1f) {
                                screenType = ScreenType.ExternalScreen
                            } else if (width < height) {
                                screenType = ScreenType.InternalPortraitScreen
                            }
                        }
                        else -> {}
                    }
                    TetrisGameScreen(
                        screenType = screenType
                    )
                }
            }
        }
    }
}