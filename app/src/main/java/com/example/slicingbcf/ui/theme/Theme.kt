package com.example.slicingbcf.ui.theme

import android.os.Build
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.example.slicingbcf.constant.ColorPalette

private val DarkColorScheme = darkColorScheme(
  background = ColorPalette.OnPrimary
)

private val LightColorScheme = lightColorScheme(

  background = ColorPalette.OnPrimary,
  onBackground = ColorPalette.Black,
  surface = ColorPalette.OnPrimary,
  onSurface = ColorPalette.Black,
)

@Composable
fun SlicingBcfTheme(
  // Dynamic color is available on Android 12+
//  dynamicColor : Boolean = true,
//  darkTheme : Boolean = isSystemInDarkTheme(),
  // ! false karena harus sesuai dengan figma
  dynamicColor : Boolean = false,
  darkTheme : Boolean = false,
  content : @Composable () -> Unit,
) {
  val colorScheme = when {
    dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
      val context = LocalContext.current
      if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
    }


    darkTheme                                                      -> DarkColorScheme
    else                                                           -> LightColorScheme
  }

  MaterialTheme(
    colorScheme = colorScheme,
    typography = Typography,
    content = content
  )
}