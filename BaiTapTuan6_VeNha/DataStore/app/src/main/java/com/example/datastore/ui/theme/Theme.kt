package com.example.datastore.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import com.example.datastore.data.AppTheme

private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80
)

private val LightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40

    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)
// Định nghĩa bảng màu cho từng theme
private val LightColors = lightColorScheme(
    primary = AppBlue,
    background = AppWhite,
    onBackground = AppBlack,
    surface = AppWhite,
    onSurface = AppBlack
)

private val DarkColors = darkColorScheme(
    primary = AppBlue,
    background = AppDarkGray,
    onBackground = AppWhite,
    surface = AppDarkGray,
    onSurface = AppWhite
)

private val BlueColors = lightColorScheme(
    primary = AppBlue,
    background = AppBlue,
    onBackground = AppBlack,
    surface = AppBlue,
    onSurface = AppBlack
)

private val MagentaColors = lightColorScheme(
    primary = AppBlue,
    background = AppMagenta,
    onBackground = AppWhite,
    surface = AppMagenta,
    onSurface = AppWhite
)

@Composable
fun DataStoreTheme(
//    darkTheme: Boolean = isSystemInDarkTheme(),
//    // Dynamic color is available on Android 12+
//    dynamicColor: Boolean = true,
//    content: @Composable () -> Unit
//) {
//    val colorScheme = when {
//        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
//            val context = LocalContext.current
//            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
//        }
//
//        darkTheme -> DarkColorScheme
//        else -> LightColorScheme
//    }
//
//    MaterialTheme(
//        colorScheme = colorScheme,
//        typography = Typography,
//        content = content
//    )
    dynamicTheme: AppTheme = AppTheme.LIGHT,
    content: @Composable () -> Unit
) {
    val colorScheme = when (dynamicTheme) {
        AppTheme.LIGHT -> LightColors
        AppTheme.DARK -> DarkColors
        AppTheme.BLUE -> BlueColors
        AppTheme.MAGENTA -> MagentaColors
    }

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.background.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars =
                (dynamicTheme != AppTheme.DARK && dynamicTheme != AppTheme.MAGENTA) // Thanh status bar sẽ có chữ màu đen trên nền sáng và ngược lại
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}