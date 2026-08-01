package com.example.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.R

val CormorantFontFamily = FontFamily(
    Font(R.font.cormorant_garamond, FontWeight.Normal),
    Font(R.font.cormorant_garamond, FontWeight.Bold),
    Font(R.font.cormorant_garamond_italic, FontWeight.Normal, FontStyle.Italic)
)

val PoppinsFontFamily = FontFamily(
    Font(R.font.poppins, FontWeight.Normal),
    Font(R.font.poppins_medium, FontWeight.Medium)
)

val AppTypography = Typography(
    displayLarge = TextStyle(
        fontFamily = CormorantFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 42.sp,
        lineHeight = 48.sp,
        letterSpacing = 0.5.sp,
        color = SoftWhite
    ),
    displayMedium = TextStyle(
        fontFamily = CormorantFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 32.sp,
        lineHeight = 38.sp,
        letterSpacing = 0.25.sp,
        color = SoftWhite
    ),
    headlineMedium = TextStyle(
        fontFamily = CormorantFontFamily,
        fontWeight = FontWeight.Normal,
        fontStyle = FontStyle.Italic,
        fontSize = 24.sp,
        lineHeight = 32.sp,
        letterSpacing = 0.15.sp,
        color = SoftWhite
    ),
    bodyLarge = TextStyle(
        fontFamily = PoppinsFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 26.sp,
        letterSpacing = 0.5.sp,
        color = SoftWhite
    ),
    bodyMedium = TextStyle(
        fontFamily = PoppinsFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 22.sp,
        letterSpacing = 0.25.sp,
        color = DimWhite
    ),
    labelLarge = TextStyle(
        fontFamily = PoppinsFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 15.sp,
        lineHeight = 20.sp,
        letterSpacing = 1.sp,
        color = GoldAccent
    )
)
