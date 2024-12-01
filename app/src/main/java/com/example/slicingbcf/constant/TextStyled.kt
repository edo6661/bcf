package com.example.slicingbcf.constant

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.slicingbcf.R

object StyledText {

  val MobileBaseSemibold = TextStyle(
    fontSize = 16.sp,
    lineHeight = 22.4.sp,
    fontFamily = Poppins,
    fontWeight = FontWeight.SemiBold,
  )
  val MobileBaseRegular = TextStyle(
    fontSize = 16.sp,
    lineHeight = 22.4.sp,
    fontFamily = Poppins,
    fontWeight = FontWeight.Normal,
  )

  val MobileXsRegular = TextStyle(
    fontSize = 12.sp,
    lineHeight = 16.8.sp,
    fontFamily = Poppins,
    fontWeight = FontWeight.Normal,
  )

  val MobileXsMedium = TextStyle(
    fontSize = 12.sp,
    lineHeight = 16.8.sp,
    fontFamily = Poppins,
    fontWeight = FontWeight.Medium,
  )

  val MobileSmallMedium = TextStyle(
    fontSize = 14.sp,
    lineHeight = 19.6.sp,
    fontFamily = Poppins,
    fontWeight = FontWeight.Medium,
  )

  val MobileLargeMedium = TextStyle(
    fontSize = 20.sp,
    lineHeight = 28.sp,
    fontFamily = Poppins,
    fontWeight = FontWeight.Medium,
  )
  val MobileLargeSemibold = TextStyle(
    fontSize = 20.sp,
    lineHeight = 28.sp,
    fontFamily = Poppins,
    fontWeight = FontWeight.SemiBold,
  )
  val MobileLargeBold = TextStyle(
    fontSize = 20.sp,
    lineHeight = 28.sp,
    fontFamily = Poppins,
    fontWeight = FontWeight.Bold,
  )

  val MobileSmallRegular = TextStyle(
    fontSize = 14.sp,
    lineHeight = 19.6.sp,
    fontFamily = Poppins,
    fontWeight = FontWeight.Normal,
  )

  val Mobile3xsMedium = TextStyle(
    fontSize = 10.sp,
    lineHeight = 14.sp,
    fontFamily = Poppins,
    fontWeight = FontWeight.Medium,
  )

  val Mobile2xlBold = TextStyle(
    fontSize = 28.sp,
    lineHeight = 33.6.sp,
    fontFamily = Poppins,
    fontWeight = FontWeight.Bold,
  )

  val MobileXsBold = TextStyle(
    fontSize = 12.sp,
    lineHeight = 16.8.sp,
    fontFamily = Poppins,
    fontWeight = FontWeight.Bold,
  )

  val MobileSmallSemibold = TextStyle(
    fontSize = 14.sp,
    lineHeight = 19.6.sp,
    fontFamily = Poppins,
    fontWeight = FontWeight.SemiBold,
  )

  val Mobile3xsRegular = TextStyle(
    fontSize = 10.sp,
    lineHeight = 14.sp,
    fontFamily = Poppins,
    fontWeight = FontWeight.Normal,
  )

  val MobileMediumMedium = TextStyle(
    fontSize = 18.sp,
    lineHeight = 25.2.sp,
    fontFamily = Poppins,
    fontWeight = FontWeight.Medium,
  )
  val MobileMediumSemibold = TextStyle(
    fontSize = 18.sp,
    lineHeight = 25.2.sp,
    fontFamily = Poppins,
    fontWeight = FontWeight.SemiBold,
  )
  val MobileMediumRegular = TextStyle(
    fontSize = 18.sp,
    lineHeight = 25.2.sp,
    fontFamily = Poppins,
    fontWeight = FontWeight.Normal,
  )

  val Mobile2xsRegular = TextStyle(
    fontSize = 11.sp,
    lineHeight = 15.4.sp,
    fontFamily = Poppins,
    fontWeight = FontWeight.Normal,
  )

  val Mobile2xsSemibold = TextStyle(
    fontSize = 11.sp,
    lineHeight = 15.4.sp,
    fontFamily = Poppins,
    fontWeight = FontWeight.SemiBold,
  )

  val MobileBaseMedium = TextStyle(
    fontSize = 16.sp,
    lineHeight = 22.4.sp,
    fontFamily = Poppins,
    fontWeight = FontWeight.Medium,
  )

  val MobileXlMedium = TextStyle(
    fontSize = 24.sp,
    lineHeight = 28.8.sp,
    fontFamily = Poppins,
    fontWeight = FontWeight.Medium,
  )
  val MobileXlBold = TextStyle(
    fontSize = 24.sp,
    lineHeight = 28.8.sp,
    fontFamily = Poppins,
    fontWeight = FontWeight.Bold,
  )
}

val Poppins = FontFamily(
  Font(R.font.poppins_regular, FontWeight.W400),
  Font(R.font.poppins_medium, FontWeight.W500),
  Font(R.font.poppins_semibold, FontWeight.W600),
  Font(R.font.poppins_bold, FontWeight.W700)
)
