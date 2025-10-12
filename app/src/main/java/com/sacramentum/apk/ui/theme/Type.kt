package com.sacramentum.apk.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.sacramentum.apk.R

val LondrinaSolid = FontFamily(Font(R.font.londrina_solid_black))
val RobotoCondensed = FontFamily(Font(R.font.roboto_condensed_variable_font_wght))

val Typography = Typography(
    titleLarge = TextStyle(
        fontFamily = LondrinaSolid,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    titleSmall = TextStyle(
        fontFamily = RobotoCondensed,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )
)