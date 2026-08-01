package com.example.ui.screens

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import com.example.ui.theme.GoldAccent
import com.example.ui.theme.PoppinsFontFamily
import com.example.ui.theme.SoftWhite
import com.example.ui.theme.VioletGlow

@Composable
fun LandingScreen(
    onOpenLetterClicked: () -> Unit
) {
    val infiniteTransition = rememberInfiniteTransition(label = "button_glow")
    val pulseGlow by infiniteTransition.animateFloat(
        initialValue = 0.98f,
        targetValue = 1.05f,
        animationSpec = infiniteRepeatable(
            animation = tween(1500, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "button_scale"
    )

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 28.dp)
        ) {
            // Glassmorphism Hero Card
            GlassCard(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = 32.dp
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    // Small decorative star icon / badge
                    Text(
                        text = "✨ DEDIKASI ULANG TAHUN ✨",
                        fontFamily = PoppinsFontFamily,
                        fontSize = 11.sp,
                        letterSpacing = 2.5.sp,
                        color = GoldAccent,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    // Large elegant title
                    Text(
                        text = "Selamat Ulang Tahun.",
                        fontFamily = CormorantFontFamily,
                        fontSize = 40.sp,
                        lineHeight = 46.sp,
                        color = SoftWhite,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.testTag("landing_title")
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Small subtitle
                    Text(
                        text = "Untuk seseorang yang pernah menjadi favoritku.",
                        fontFamily = CormorantFontFamily,
                        fontStyle = FontStyle.Italic,
                        fontSize = 20.sp,
                        lineHeight = 28.sp,
                        color = DimWhite,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(36.dp))

                    // Glowing Glass Button
                    val buttonGradients = Brush.horizontalGradient(
                        colors = listOf(
                            Color(0x884F46E5),
                            Color(0xAA818CF8),
                            Color(0x884F46E5)
                        )
                    )

                    Box(
                        modifier = Modifier
                            .scale(pulseGlow)
                            .shadow(
                                elevation = 16.dp,
                                shape = RoundedCornerShape(30.dp),
                                spotColor = GoldAccent,
                                ambientColor = VioletGlow
                            )
                            .clip(RoundedCornerShape(30.dp))
                            .background(buttonGradients)
                            .border(
                                BorderStroke(1.5.dp, GoldAccent.copy(alpha = 0.8f)),
                                RoundedCornerShape(30.dp)
                            )
                            .clickable { onOpenLetterClicked() }
                            .padding(horizontal = 32.dp, vertical = 16.dp)
                            .testTag("open_letter_button")
                    ) {
                        Text(
                            text = "Buka Surat 💌",
                            fontFamily = PoppinsFontFamily,
                            fontSize = 16.sp,
                            letterSpacing = 1.sp,
                            color = SoftWhite,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(28.dp))

            // Gentle footer hint
            Text(
                text = "Sentuh langit malam untuk menyalakan bintang",
                fontFamily = PoppinsFontFamily,
                fontSize = 12.sp,
                color = SoftWhite.copy(alpha = 0.4f),
                textAlign = TextAlign.Center
            )
        }
    }
}
