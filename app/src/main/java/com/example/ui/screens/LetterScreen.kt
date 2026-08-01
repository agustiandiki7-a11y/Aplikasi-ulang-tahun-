package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
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
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.FastForward
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.GlassCard
import com.example.ui.theme.CormorantFontFamily
import com.example.ui.theme.DimWhite
import com.example.ui.theme.GlassBorder
import com.example.ui.theme.GoldAccent
import com.example.ui.theme.PoppinsFontFamily
import com.example.ui.theme.SoftWhite
import kotlinx.coroutines.delay

val letterParagraphs = listOf(
    "Saat satu tahun lagi berganti dan keheningan tengah malam memenuhi langit, aku ingin mengirimkan doa yang tenang melewati jarak. Waktu dengan lembut melunakkan setiap sudut yang tajam, menyisakan kehangatan dan rasa syukur atas momen-momen yang pernah kita lewati bersama.",
    "Kita mungkin berjalan di jalur yang berbeda sekarang, namun aku tetap merayakan sosokmu dan segala hal baik yang terus tumbuh dalam dirimu. Gelak tawa, percakapan tenang di penghujung hari, dan impian yang pernah kita bicarakan akan selalu memiliki tempat istimewa di ingatan. Terima kasih telah menjadi bab yang sangat berarti dalam kisahku.",
    "Semoga tahun yang baru ini membawakanmu kedamaian yang mendalam, kebahagiaan tanpa akhir, dan seluruh cahaya yang layak engkau dapatkan. Semoga setiap impian yang kau kejar menemukan jalannya padamu, dan semoga engkau selalu tahu betapa sangat berartinya dirimu. Selamat Ulang Tahun."
)

@Composable
fun LetterScreen(
    onProgressUpdate: (Float) -> Unit,
    onContinueToGalleryClicked: () -> Unit
) {
    var paragraphIndex by remember { mutableIntStateOf(0) }
    var typedText by remember { mutableStateOf("") }
    var isTypingComplete by remember { mutableStateOf(false) }

    val currentFullText = letterParagraphs.getOrElse(paragraphIndex) { "" }

    // Typewriter effect coroutine
    LaunchedEffect(paragraphIndex) {
        typedText = ""
        isTypingComplete = false
        val totalLength = currentFullText.length

        for (i in 1..totalLength) {
            typedText = currentFullText.substring(0, i)
            delay(28) // smooth typing pace
        }
        isTypingComplete = true
    }

    // Update overall reading progress
    LaunchedEffect(paragraphIndex, typedText, isTypingComplete) {
        val currentProgress = (paragraphIndex.toFloat() + (typedText.length.toFloat() / currentFullText.length.coerceAtLeast(1))) / letterParagraphs.size
        onProgressUpdate(currentProgress.coerceIn(0f, 1f))
    }

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
            Spacer(modifier = Modifier.height(60.dp))

            // Glassmorphism Letter Container
            GlassCard(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = 28.dp
            ) {
                Column {
                    // Header label with wax seal symbol
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(24.dp)
                                    .clip(CircleShape)
                                    .background(GoldAccent.copy(alpha = 0.2f))
                                    .border(1.dp, GoldAccent, CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "✉",
                                    fontSize = 11.sp,
                                    color = GoldAccent
                                )
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(
                                text = "PARAGRAF ${paragraphIndex + 1} DARI ${letterParagraphs.size}",
                                fontFamily = PoppinsFontFamily,
                                fontSize = 11.sp,
                                letterSpacing = 2.sp,
                                color = GoldAccent
                            )
                        }

                        // Fast forward / Instant reveal button
                        if (!isTypingComplete) {
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(Color.White.copy(alpha = 0.08f))
                                    .clickable {
                                        typedText = currentFullText
                                        isTypingComplete = true
                                    }
                                    .padding(horizontal = 8.dp, vertical = 4.dp)
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = Icons.Default.FastForward,
                                        contentDescription = "Fast forward",
                                        tint = DimWhite,
                                        modifier = Modifier.size(12.dp)
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(
                                        text = "Tampilkan",
                                        fontFamily = PoppinsFontFamily,
                                        fontSize = 10.sp,
                                        color = DimWhite
                                    )
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // Typed Letter Paragraph
                    Text(
                        text = typedText + if (!isTypingComplete) " ▌" else "",
                        fontFamily = CormorantFontFamily,
                        fontSize = 22.sp,
                        lineHeight = 34.sp,
                        color = SoftWhite,
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("letter_text_content")
                    )

                    Spacer(modifier = Modifier.height(28.dp))

                    // Next Paragraph or Continue to Gallery Button
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        if (paragraphIndex < letterParagraphs.size - 1) {
                            // Next Paragraph button
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(20.dp))
                                    .background(
                                        Brush.horizontalGradient(
                                            listOf(
                                                Color(0xAA4F46E5),
                                                Color(0xAA818CF8)
                                            )
                                        )
                                    )
                                    .border(1.dp, GlassBorder, RoundedCornerShape(20.dp))
                                    .clickable {
                                        if (isTypingComplete) {
                                            paragraphIndex++
                                        } else {
                                            typedText = currentFullText
                                            isTypingComplete = true
                                        }
                                    }
                                    .padding(horizontal = 20.dp, vertical = 10.dp)
                                    .testTag("next_paragraph_button")
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(
                                        text = "Lanjut Baca",
                                        fontFamily = PoppinsFontFamily,
                                        fontSize = 13.sp,
                                        color = SoftWhite
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Icon(
                                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                                        contentDescription = "Lanjut",
                                        tint = SoftWhite,
                                        modifier = Modifier.size(14.dp)
                                    )
                                }
                            }
                        } else {
                            // Continue to Gallery button
                            AnimatedVisibility(
                                visible = isTypingComplete,
                                enter = fadeIn() + slideInVertically { it / 2 }
                            ) {
                                Box(
                                    modifier = Modifier
                                        .shadow(
                                            elevation = 8.dp,
                                            shape = RoundedCornerShape(20.dp),
                                            spotColor = GoldAccent
                                        )
                                        .clip(RoundedCornerShape(20.dp))
                                        .background(
                                            Brush.horizontalGradient(
                                                listOf(
                                                    GoldAccent.copy(alpha = 0.8f),
                                                    Color(0xFFF59E0B)
                                                )
                                            )
                                        )
                                        .clickable { onContinueToGalleryClicked() }
                                        .padding(horizontal = 24.dp, vertical = 12.dp)
                                        .testTag("continue_gallery_button")
                                ) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Text(
                                            text = "Lihat Kenangan 📷",
                                            fontFamily = PoppinsFontFamily,
                                            fontSize = 14.sp,
                                            color = Color(0xFF0F172A)
                                        )
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Icon(
                                            imageVector = Icons.Default.AutoAwesome,
                                            contentDescription = "Gallery",
                                            tint = Color(0xFF0F172A),
                                            modifier = Modifier.size(16.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Paragraph step indicator dots
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                letterParagraphs.indices.forEach { idx ->
                    Box(
                        modifier = Modifier
                            .padding(4.dp)
                            .size(if (idx == paragraphIndex) 10.dp else 6.dp)
                            .clip(CircleShape)
                            .background(
                                if (idx == paragraphIndex) GoldAccent else Color.White.copy(alpha = 0.2f)
                            )
                            .clickable {
                                paragraphIndex = idx
                            }
                    )
                }
            }

            Spacer(modifier = Modifier.height(60.dp))
        }
    }
}
