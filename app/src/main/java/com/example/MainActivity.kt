package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.audio.AmbientAudioPlayer
import com.example.ui.components.NightSkyBackground
import com.example.ui.components.TopHeaderBar
import com.example.ui.screens.EndingScreen
import com.example.ui.screens.GalleryScreen
import com.example.ui.screens.LandingScreen
import com.example.ui.screens.LetterScreen
import com.example.ui.theme.StitchTheme

enum class AppScreen {
    LANDING,
    LETTER,
    GALLERY,
    ENDING
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            StitchTheme {
                StitchBirthdayApp()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        AmbientAudioPlayer.stop()
    }
}

@Composable
fun StitchBirthdayApp() {
    var currentScreen by remember { mutableStateOf(AppScreen.LANDING) }
    var letterReadingProgress by remember { mutableFloatStateOf(0f) }

    DisposableEffect(Unit) {
        onDispose {
            AmbientAudioPlayer.stop()
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = Color.Transparent
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // Animated starry night sky background with petals on ending
            NightSkyBackground(
                enablePetals = (currentScreen == AppScreen.ENDING)
            ) {
                Box(modifier = Modifier.fillMaxSize()) {
                    // Top Bar Header
                    TopHeaderBar(
                        readingProgress = if (currentScreen == AppScreen.LETTER) letterReadingProgress else null
                    )

                    // Screen crossfade transition
                    Crossfade(
                        targetState = currentScreen,
                        animationSpec = tween(700),
                        label = "screen_transition",
                        modifier = Modifier.fillMaxSize()
                    ) { screen ->
                        when (screen) {
                            AppScreen.LANDING -> {
                                LandingScreen(
                                    onOpenLetterClicked = {
                                        currentScreen = AppScreen.LETTER
                                    }
                                )
                            }
                            AppScreen.LETTER -> {
                                LetterScreen(
                                    onProgressUpdate = { progress ->
                                        letterReadingProgress = progress
                                    },
                                    onContinueToGalleryClicked = {
                                        currentScreen = AppScreen.GALLERY
                                    }
                                )
                            }
                            AppScreen.GALLERY -> {
                                GalleryScreen(
                                    onContinueToEndingClicked = {
                                        currentScreen = AppScreen.ENDING
                                    }
                                )
                            }
                            AppScreen.ENDING -> {
                                EndingScreen(
                                    onReplayClicked = {
                                        currentScreen = AppScreen.LANDING
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
