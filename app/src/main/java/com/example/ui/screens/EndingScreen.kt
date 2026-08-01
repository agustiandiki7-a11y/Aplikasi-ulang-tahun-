package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.GlassCard
import com.example.ui.theme.CormorantFontFamily
import com.example.ui.theme.DimWhite
import com.example.ui.theme.GlassBorder
import com.example.ui.theme.GoldAccent
import com.example.ui.theme.PoppinsFontFamily
import com.example.ui.theme.SoftWhite
import com.example.ui.theme.VioletGlow

val presetWishes = listOf(
    "Semoga hatimu selalu terasa ringan dan penuh dengan kasih.",
    "Semoga setiap usahamu dalam hening membuahkan keberhasilan yang indah.",
    "Semoga kesehatan, kedamaian, dan ketenangan selalu menyertaimu.",
    "Semoga engkau selalu dikelilingi kehangatan sejati dan tersenyum setiap hari."
)

@Composable
fun EndingScreen(
    onReplayClicked: () -> Unit
) {
    val wishStars = remember { mutableStateListOf<String>() }

    val infiniteTransition = rememberInfiniteTransition(label = "ending_star_glow")
    val starGlowScale by infiniteTransition.animateFloat(
        initialValue = 0.95f,
        targetValue = 1.15f,
        animationSpec = infiniteRepeatable(
            animation = tween(1400, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "star_scale"
    )

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.height(40.dp))

            // Cinematic Glass Card
            GlassCard(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = 32.dp
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    // Glowing Golden Star
                    Box(
                        modifier = Modifier
                            .scale(starGlowScale)
                            .size(54.dp)
                            .clip(CircleShape)
                            .background(GoldAccent.copy(alpha = 0.15f))
                            .border(1.dp, GoldAccent, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = "Glowing star",
                            tint = GoldAccent,
                            modifier = Modifier.size(28.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // Final Birthday Greeting
                    Text(
                        text = "Selamat Ulang Tahun",
                        fontFamily = CormorantFontFamily,
                        fontSize = 36.sp,
                        color = SoftWhite,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.testTag("ending_happy_birthday")
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    // Key Quotes
                    Text(
                        text = "“Semoga setiap impian yang kau kejar menemukan jalannya padamu.”",
                        fontFamily = CormorantFontFamily,
                        fontStyle = FontStyle.Italic,
                        fontSize = 20.sp,
                        lineHeight = 28.sp,
                        color = GoldAccent,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "“Terima kasih telah pernah menjadi bagian dari kisahku.”",
                        fontFamily = CormorantFontFamily,
                        fontStyle = FontStyle.Italic,
                        fontSize = 19.sp,
                        lineHeight = 27.sp,
                        color = DimWhite,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(32.dp))

                    // Interactive Wish Light Button
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(20.dp))
                            .background(Color.White.copy(alpha = 0.08f))
                            .border(BorderStroke(1.dp, GlassBorder), RoundedCornerShape(20.dp))
                            .clickable {
                                if (presetWishes.isNotEmpty()) {
                                    val nextWish = presetWishes[wishStars.size % presetWishes.size]
                                    wishStars.add(nextWish)
                                }
                            }
                            .padding(horizontal = 20.dp, vertical = 12.dp)
                            .testTag("light_wish_star_button")
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.AutoAwesome,
                                contentDescription = "Bintang Harapan",
                                tint = GoldAccent,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Nyalakan Bintang Harapan 🌟",
                                fontFamily = PoppinsFontFamily,
                                fontSize = 13.sp,
                                color = SoftWhite
                            )
                        }
                    }
                }
            }

            // Display lit wishes
            if (wishStars.isNotEmpty()) {
                Spacer(modifier = Modifier.height(20.dp))
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    wishStars.forEachIndexed { idx, wish ->
                        AnimatedVisibility(
                            visible = true,
                            enter = fadeIn()
                        ) {
                            GlassCard(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp),
                                contentPadding = 14.dp
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = Icons.Default.Star,
                                        contentDescription = "Star",
                                        tint = GoldAccent,
                                        modifier = Modifier.size(14.dp)
                                    )
                                    Spacer(modifier = Modifier.width(10.dp))
                                    Text(
                                        text = wish,
                                        fontFamily = PoppinsFontFamily,
                                        fontSize = 12.sp,
                                        color = SoftWhite
                                    )
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Replay experience button
            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .background(Color.White.copy(alpha = 0.1f))
                    .border(1.dp, GlassBorder, CircleShape)
                    .clickable { onReplayClicked() }
                    .padding(horizontal = 20.dp, vertical = 10.dp)
                    .testTag("replay_button")
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Refresh,
                        contentDescription = "Replay",
                        tint = DimWhite,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "PUTAR ULANG PENGALAMAN",
                        fontFamily = PoppinsFontFamily,
                        fontSize = 11.sp,
                        letterSpacing = 1.5.sp,
                        color = DimWhite
                    )
                }
            }

            Spacer(modifier = Modifier.height(60.dp))
        }
    }
}
