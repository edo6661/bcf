package com.example.slicingbcf.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.slicingbcf.R

val Poppins = FontFamily(
  Font(R.font.poppins_regular, FontWeight.Normal),
  Font(R.font.poppins_medium, FontWeight.W500),
  Font(R.font.poppins_semibold, FontWeight.W600),
  Font(R.font.poppins_bold, FontWeight.W700)
)


val Typography = Typography(
  titleLarge = TextStyle(
    fontFamily = Poppins,
    fontWeight = FontWeight.W700,
    fontSize = 28.sp,
    lineHeight = 33.6.sp,
  ),
  bodyLarge = TextStyle(
    fontFamily = Poppins,
    fontWeight = FontWeight.W600,
    fontSize = 16.sp,
    lineHeight = 22.4.sp,
  ),

  )
