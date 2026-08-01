package com.example.ui.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.MusicOff
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.audio.AmbientAudioPlayer
import com.example.ui.theme.GlassBorder
import com.example.ui.theme.GoldAccent
import com.example.ui.theme.PoppinsFontFamily
import com.example.ui.theme.SoftWhite
import com.example.ui.theme.VioletGlow
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun TopHeaderBar(
    modifier: Modifier = Modifier,
    readingProgress: Float? = null,
    onMusicToggle: () -> Unit = { AmbientAudioPlayer.toggle() }
) {
    val isAudioPlaying by AmbientAudioPlayer.isPlaying.collectAsState()

    var currentTimeString by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        val sdf = SimpleDateFormat("hh:mm a", Locale.getDefault())
        while (true) {
            currentTimeString = sdf.format(Date())
            delay(10000)
        }
    }

    val pulseTransition = rememberInfiniteTransition(label = "music_pulse")
    val pulseScale by pulseTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.15f,
        animationSpec = infiniteRepeatable(
            animation = tween(800, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulse"
    )

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Digital Clock & Night Sky Label
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(8.dp)
                    .clip(CircleShape)
                    .background(GoldAccent)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = if (currentTimeString.isEmpty()) "LANGIT MALAM" else "MALAM • $currentTimeString",
                fontFamily = PoppinsFontFamily,
                fontSize = 11.sp,
                letterSpacing = 1.5.sp,
                color = SoftWhite.copy(alpha = 0.8f)
            )
        }

        // Reading Progress indicator (if in letter mode)
        if (readingProgress != null) {
            Box(
                modifier = Modifier
                    .width(100.dp)
                    .height(4.dp)
                    .clip(RoundedCornerShape(2.dp))
                    .background(Color.White.copy(alpha = 0.15f))
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(readingProgress.coerceIn(0f, 1f))
                        .height(4.dp)
                        .clip(RoundedCornerShape(2.dp))
                        .background(GoldAccent)
                )
            }
        }

        // Music Play/Pause Button
        Box(
            modifier = Modifier
                .scale(if (isAudioPlaying) pulseScale else 1f)
                .clip(CircleShape)
                .background(Color(0x331E1B4B))
                .border(1.dp, if (isAudioPlaying) GoldAccent else GlassBorder, CircleShape)
                .clickable { onMusicToggle() }
                .padding(horizontal = 12.dp, vertical = 8.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = if (isAudioPlaying) Icons.Default.MusicNote else Icons.Default.MusicOff,
                    contentDescription = "Music toggle",
                    tint = if (isAudioPlaying) GoldAccent else SoftWhite.copy(alpha = 0.6f),
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = if (isAudioPlaying) "MUSIK NYALA" else "MUSIK MATI",
                    fontFamily = PoppinsFontFamily,
                    fontSize = 10.sp,
                    letterSpacing = 1.sp,
                    color = if (isAudioPlaying) GoldAccent else SoftWhite.copy(alpha = 0.6f)
                )
            }
        }
    }
}
