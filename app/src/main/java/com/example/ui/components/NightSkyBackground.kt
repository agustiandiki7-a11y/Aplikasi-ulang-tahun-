package com.example.ui.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import com.example.ui.theme.DarkPurpleSurface
import com.example.ui.theme.DeepPurple
import com.example.ui.theme.GoldAccent
import com.example.ui.theme.MidnightBlue
import com.example.ui.theme.SoftWhite
import com.example.ui.theme.VioletGlow
import kotlinx.coroutines.delay
import kotlin.math.cos
import kotlin.math.sin
import kotlin.random.Random

data class Star(
    val xRatio: Float,
    val yRatio: Float,
    val radiusDp: Float,
    val baseAlpha: Float,
    val pulseSpeed: Float,
    val phase: Float,
    val color: Color
)

data class ShootingStar(
    val id: Long,
    var startXRatio: Float,
    var startYRatio: Float,
    var lengthDp: Float,
    var progress: Float, // 0f to 1f
    var angleRad: Float
)

data class Particle(
    val id: Long,
    var xRatio: Float,
    var yRatio: Float,
    val radiusDp: Float,
    val speedYRatio: Float,
    val swaySpeed: Float,
    val phase: Float,
    val color: Color
)

@Composable
fun NightSkyBackground(
    modifier: Modifier = Modifier,
    enablePetals: Boolean = false,
    content: @Composable () -> Unit = {}
) {
    val infiniteTransition = rememberInfiniteTransition(label = "sky_time")
    val time by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 2000f,
        animationSpec = infiniteRepeatable(
            animation = tween(200000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "time_flow"
    )

    // Generate static star field positions
    val stars = remember {
        val list = mutableListOf<Star>()
        val rnd = Random(42)
        val colors = listOf(SoftWhite, GoldAccent, VioletGlow, Color(0xFFC084FC))
        repeat(120) {
            list.add(
                Star(
                    xRatio = rnd.nextFloat(),
                    yRatio = rnd.nextFloat(),
                    radiusDp = rnd.nextFloat() * 2.2f + 0.8f,
                    baseAlpha = rnd.nextFloat() * 0.5f + 0.3f,
                    pulseSpeed = rnd.nextFloat() * 2f + 1f,
                    phase = rnd.nextFloat() * 6.28f,
                    color = colors[rnd.nextInt(colors.size)]
                )
            )
        }
        list
    }

    // Dynamic shooting stars list
    val shootingStars = remember { mutableStateListOf<ShootingStar>() }
    // Dynamic floating petals list
    val petals = remember { mutableStateListOf<Particle>() }

    // Touch interactive tap sparkles
    val tapSparkles = remember { mutableStateListOf<Particle>() }

    // Shooting star trigger loop
    LaunchedEffect(Unit) {
        var idCounter = 0L
        while (true) {
            delay(Random.nextLong(2500, 5000))
            if (shootingStars.size < 3) {
                shootingStars.add(
                    ShootingStar(
                        id = idCounter++,
                        startXRatio = Random.nextFloat() * 0.7f + 0.1f,
                        startYRatio = Random.nextFloat() * 0.4f,
                        lengthDp = Random.nextFloat() * 80f + 60f,
                        progress = 0f,
                        angleRad = 0.65f + Random.nextFloat() * 0.2f // diagonal right down
                    )
                )
            }
        }
    }

    // Petals generator loop when enabled
    LaunchedEffect(enablePetals) {
        if (!enablePetals) {
            petals.clear()
            return@LaunchedEffect
        }
        var pCounter = 0L
        val petalColors = listOf(GoldAccent, Color(0xFFF472B6), Color(0xFFC084FC), SoftWhite)
        while (enablePetals) {
            if (petals.size < 35) {
                petals.add(
                    Particle(
                        id = pCounter++,
                        xRatio = Random.nextFloat(),
                        yRatio = -0.05f,
                        radiusDp = Random.nextFloat() * 3f + 2f,
                        speedYRatio = Random.nextFloat() * 0.0015f + 0.0008f,
                        swaySpeed = Random.nextFloat() * 2f + 1f,
                        phase = Random.nextFloat() * 6.28f,
                        color = petalColors[Random.nextInt(petalColors.size)]
                    )
                )
            }
            delay(300)
        }
    }

    // Update shooting stars frame loop
    LaunchedEffect(time) {
        // Progress shooting stars
        val toRemove = mutableListOf<ShootingStar>()
        shootingStars.forEach { ss ->
            ss.progress += 0.04f
            if (ss.progress >= 1f) {
                toRemove.add(ss)
            }
        }
        shootingStars.removeAll(toRemove)

        // Progress petals
        val petalsToRemove = mutableListOf<Particle>()
        petals.forEach { p ->
            p.yRatio += p.speedYRatio
            if (p.yRatio > 1.05f) {
                petalsToRemove.add(p)
            }
        }
        petals.removeAll(petalsToRemove)

        // Progress tap sparkles
        val sparkToRemove = mutableListOf<Particle>()
        tapSparkles.forEach { s ->
            s.yRatio += s.speedYRatio
            if (s.yRatio > 1.05f || s.yRatio < -0.05f) {
                sparkToRemove.add(s)
            }
        }
        tapSparkles.removeAll(sparkToRemove)
    }

    val backgroundGradient = remember {
        Brush.verticalGradient(
            colors = listOf(
                MidnightBlue,
                DarkPurpleSurface,
                MidnightBlue,
                Color(0xFF020617)
            )
        )
    }

    Canvas(
        modifier = modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures { offset ->
                    val width = size.width.toFloat()
                    val height = size.height.toFloat()
                    val tapX = offset.x / width
                    val tapY = offset.y / height
                    // Spawn 6 burst sparkles at touch location
                    repeat(6) {
                        tapSparkles.add(
                            Particle(
                                id = Random.nextLong(),
                                xRatio = tapX + (Random.nextFloat() - 0.5f) * 0.05f,
                                yRatio = tapY + (Random.nextFloat() - 0.5f) * 0.05f,
                                radiusDp = Random.nextFloat() * 4f + 2f,
                                speedYRatio = (Random.nextFloat() - 0.5f) * 0.002f,
                                swaySpeed = Random.nextFloat() * 3f,
                                phase = Random.nextFloat() * 6.28f,
                                color = GoldAccent
                            )
                        )
                    }
                }
            }
    ) {
        val width = size.width
        val height = size.height

        // Fill cosmic background
        drawRect(brush = backgroundGradient)

        // Nebula atmospheric glow in upper center
        drawCircle(
            brush = Brush.radialGradient(
                colors = listOf(DeepPurple.copy(alpha = 0.35f), Color.Transparent),
                center = Offset(width * 0.5f, height * 0.25f),
                radius = width * 0.7f
            ),
            center = Offset(width * 0.5f, height * 0.25f),
            radius = width * 0.7f
        )

        // Draw Twinkling Stars
        stars.forEach { star ->
            val pxX = star.xRatio * width
            val pxY = star.yRatio * height
            val rPx = star.radiusDp * density

            val pulse = (sin((time * star.pulseSpeed + star.phase).toDouble()).toFloat() + 1f) / 2f
            val alpha = (star.baseAlpha + pulse * 0.45f).coerceIn(0.1f, 1f)

            drawCircle(
                color = star.color.copy(alpha = alpha),
                radius = rPx,
                center = Offset(pxX, pxY)
            )

            // Outer subtle star glow for larger stars
            if (star.radiusDp > 2f) {
                drawCircle(
                    color = star.color.copy(alpha = alpha * 0.3f),
                    radius = rPx * 2.5f,
                    center = Offset(pxX, pxY)
                )
            }
        }

        // Draw Shooting Stars (Meteors)
        shootingStars.forEach { ss ->
            val startPxX = ss.startXRatio * width
            val startPxY = ss.startYRatio * height
            val lengthPx = ss.lengthDp * density

            val currentHeadX = startPxX + cos(ss.angleRad) * lengthPx * ss.progress
            val currentHeadY = startPxY + sin(ss.angleRad) * lengthPx * ss.progress

            val tailLengthPx = lengthPx * 0.4f
            val tailX = currentHeadX - cos(ss.angleRad) * tailLengthPx
            val tailY = currentHeadY - sin(ss.angleRad) * tailLengthPx

            val meteorAlpha = (1f - ss.progress).coerceIn(0f, 1f)

            drawLine(
                brush = Brush.linearGradient(
                    colors = listOf(Color.Transparent, GoldAccent, SoftWhite),
                    start = Offset(tailX, tailY),
                    end = Offset(currentHeadX, currentHeadY)
                ),
                start = Offset(tailX, tailY),
                end = Offset(currentHeadX, currentHeadY),
                strokeWidth = 2.5f * density,
                cap = StrokeCap.Round
            )

            // Glowing head
            drawCircle(
                color = SoftWhite.copy(alpha = meteorAlpha),
                radius = 3f * density,
                center = Offset(currentHeadX, currentHeadY)
            )
        }

        // Draw Floating Petals / Particles
        petals.forEach { p ->
            val sway = sin((time * 0.05f * p.swaySpeed + p.phase).toDouble()).toFloat() * 15f * density
            val pxX = p.xRatio * width + sway
            val pxY = p.yRatio * height
            val rPx = p.radiusDp * density

            // Draw petal as smooth tilted oval
            drawCircle(
                color = p.color.copy(alpha = 0.75f),
                radius = rPx,
                center = Offset(pxX, pxY)
            )
            drawCircle(
                color = GoldAccent.copy(alpha = 0.3f),
                radius = rPx * 1.8f,
                center = Offset(pxX, pxY)
            )
        }

        // Draw Touch Sparkles
        tapSparkles.forEach { s ->
            val pxX = s.xRatio * width
            val pxY = s.yRatio * height
            drawCircle(
                color = s.color,
                radius = s.radiusDp * density,
                center = Offset(pxX, pxY)
            )
        }
    }

    content()
}
