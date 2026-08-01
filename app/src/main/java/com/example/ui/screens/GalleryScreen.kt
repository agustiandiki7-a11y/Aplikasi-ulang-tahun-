package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.R
import com.example.ui.components.GlassCard
import com.example.ui.theme.CormorantFontFamily
import com.example.ui.theme.DimWhite
import com.example.ui.theme.GlassBorder
import com.example.ui.theme.GoldAccent
import com.example.ui.theme.PoppinsFontFamily
import com.example.ui.theme.SoftWhite

data class PolaroidItem(
    val id: Int,
    val drawableRes: Int,
    val title: String,
    val dateTag: String,
    val story: String,
    val rotationAngle: Float
)

val memoryList = listOf(
    PolaroidItem(
        id = 1,
        drawableRes = R.drawable.img_polaroid_1,
        title = "Cakrawala Senja Keemasan",
        dateTag = "KENANGAN • 01",
        story = "Menyaksikan lautan berubah warna menjadi emas yang tenang. Hembusan angin lembut dan keheningan yang membuat waktu terasa tak terbatas.",
        rotationAngle = -3f
    ),
    PolaroidItem(
        id = 2,
        drawableRes = R.drawable.img_polaroid_2,
        title = "Kopi & Jendela Hujan",
        dateTag = "KENANGAN • 02",
        story = "Minuman hangat saat titik air hujan menari di balik kaca. Berjam-jam berbincang tentang impian terliar dan harapan kita.",
        rotationAngle = 2.5f
    ),
    PolaroidItem(
        id = 3,
        drawableRes = R.drawable.img_polaroid_3,
        title = "Api Unggun di Bawah Bintang",
        dateTag = "KENANGAN • 03",
        story = "Dikelilingi pepohonan pinus di bawah hamparan cahaya galaksi. Tertawa bersama di hangatnya api unggun hingga larut malam.",
        rotationAngle = -2f
    )
)

@Composable
fun GalleryScreen(
    onContinueToEndingClicked: () -> Unit
) {
    var selectedMemory by remember { mutableStateOf<PolaroidItem?>(null) }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 40.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Spacer(modifier = Modifier.height(20.dp))

            // Section Title & Caption
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(horizontal = 24.dp)
            ) {
                Text(
                    text = "GALERI KENANGAN",
                    fontFamily = PoppinsFontFamily,
                    fontSize = 12.sp,
                    letterSpacing = 3.sp,
                    color = GoldAccent
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Beberapa kenangan terlalu indah untuk sirna.",
                    fontFamily = CormorantFontFamily,
                    fontStyle = FontStyle.Italic,
                    fontSize = 24.sp,
                    lineHeight = 32.sp,
                    color = SoftWhite,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.testTag("gallery_caption")
                )
            }

            // Horizontal Polaroid Gallery
            LazyRow(
                contentPadding = PaddingValues(horizontal = 28.dp),
                horizontalArrangement = Arrangement.spacedBy(20.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("polaroid_lazy_row")
            ) {
                itemsIndexed(memoryList) { _, item ->
                    PolaroidCard(
                        item = item,
                        onClick = { selectedMemory = item }
                    )
                }
            }

            // Continue to Ending Scene Button
            Box(
                modifier = Modifier
                    .padding(horizontal = 24.dp)
                    .shadow(elevation = 12.dp, shape = RoundedCornerShape(24.dp), spotColor = GoldAccent)
                    .clip(RoundedCornerShape(24.dp))
                    .background(GoldAccent)
                    .clickable { onContinueToEndingClicked() }
                    .padding(horizontal = 32.dp, vertical = 14.dp)
                    .testTag("continue_ending_button")
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "Harapan Terakhir ✨",
                        fontFamily = PoppinsFontFamily,
                        fontSize = 15.sp,
                        color = Color(0xFF0F172A)
                    )
                }
            }
        }

        // Expanded Polaroid Dialog Modal
        selectedMemory?.let { memory ->
            Dialog(onDismissRequest = { selectedMemory = null }) {
                GlassCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    contentPadding = 20.dp
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = memory.dateTag,
                                fontFamily = PoppinsFontFamily,
                                fontSize = 11.sp,
                                letterSpacing = 2.sp,
                                color = GoldAccent
                            )
                            Box(
                                modifier = Modifier
                                    .clip(CircleShape)
                                    .background(Color.White.copy(alpha = 0.1f))
                                    .clickable { selectedMemory = null }
                                    .padding(6.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Close,
                                    contentDescription = "Close",
                                    tint = SoftWhite,
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        // Polaroid Photo Frame
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .aspectRatio(1.2f)
                                .clip(RoundedCornerShape(12.dp))
                                .background(Color.White)
                                .padding(10.dp)
                        ) {
                            Image(
                                painter = painterResource(id = memory.drawableRes),
                                contentDescription = memory.title,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clip(RoundedCornerShape(6.dp)),
                                contentScale = ContentScale.Crop
                            )
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            text = memory.title,
                            fontFamily = CormorantFontFamily,
                            fontSize = 22.sp,
                            color = SoftWhite,
                            textAlign = TextAlign.Center
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = memory.story,
                            fontFamily = PoppinsFontFamily,
                            fontSize = 13.sp,
                            lineHeight = 20.sp,
                            color = DimWhite,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun PolaroidCard(
    item: PolaroidItem,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .width(240.dp)
            .rotate(item.rotationAngle)
            .shadow(elevation = 16.dp, shape = RoundedCornerShape(16.dp), spotColor = Color.Black)
            .clip(RoundedCornerShape(16.dp))
            .background(Color(0xFFFAF9F6)) // Authentic vintage polaroid paper white
            .border(1.dp, Color(0xFFE2E8F0), RoundedCornerShape(16.dp))
            .clickable { onClick() }
            .padding(14.dp)
            .testTag("polaroid_item_${item.id}")
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            // Photo Area
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1.1f)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color(0xFF020617))
            ) {
                Image(
                    painter = painterResource(id = item.drawableRes),
                    contentDescription = item.title,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Handwritten caption style
            Text(
                text = item.title,
                fontFamily = CormorantFontFamily,
                fontSize = 18.sp,
                color = Color(0xFF1E293B),
                textAlign = TextAlign.Center,
                maxLines = 1
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = item.dateTag,
                fontFamily = PoppinsFontFamily,
                fontSize = 10.sp,
                letterSpacing = 1.5.sp,
                color = Color(0xFF64748B)
            )
        }
    }
}
