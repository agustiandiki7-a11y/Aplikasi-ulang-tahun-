package com.example.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.ui.theme.GlassBorder
import com.example.ui.theme.GlassSurface

@Composable
fun GlassCard(
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(24.dp),
    borderWidth: Dp = 1.dp,
    borderColor: Color = GlassBorder,
    backgroundColor: Color = GlassSurface,
    contentPadding: Dp = 20.dp,
    content: @Composable BoxScope.() -> Unit
) {
    val glassGradient = Brush.linearGradient(
        colors = listOf(
            backgroundColor,
            backgroundColor.copy(alpha = 0.15f)
        )
    )

    Box(
        modifier = modifier
            .shadow(
                elevation = 12.dp,
                shape = shape,
                ambientColor = Color(0xFF4F46E5),
                spotColor = Color(0xFF818CF8)
            )
            .clip(shape)
            .background(brush = glassGradient)
            .border(
                border = BorderStroke(borderWidth, borderColor),
                shape = shape
            )
            .padding(contentPadding),
        content = content
    )
}
