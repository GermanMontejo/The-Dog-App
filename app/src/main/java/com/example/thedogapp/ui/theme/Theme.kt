package com.example.thedogapp.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.example.thedogapp.ui.theme.Color.BluePrimaryDark
import com.example.thedogapp.ui.theme.Color.BluePrimaryLight
import com.example.thedogapp.ui.theme.Color.BlueSecondaryDark
import com.example.thedogapp.ui.theme.Color.BlueSecondaryLight
import com.example.thedogapp.ui.theme.Color.OnPrimaryDark
import com.example.thedogapp.ui.theme.Color.OnPrimaryLight
import com.example.thedogapp.ui.theme.Color.OnSecondaryDark
import com.example.thedogapp.ui.theme.Color.OnSecondaryLight
import com.example.thedogapp.ui.theme.Color.OnTertiaryDark
import com.example.thedogapp.ui.theme.Color.OnTertiaryLight
import com.example.thedogapp.ui.theme.Color.SurfaceColorDark
import com.example.thedogapp.ui.theme.Color.SurfaceColorLight
import com.example.thedogapp.ui.theme.Color.TertiaryColorDark
import com.example.thedogapp.ui.theme.Color.TertiaryColorLight

private val DarkColorScheme = darkColorScheme(
    primary = BluePrimaryDark,
    secondary = BlueSecondaryDark,
    tertiary = TertiaryColorDark,
    surface = SurfaceColorDark,
    onPrimary = OnPrimaryDark,
    onSecondary = OnSecondaryDark,
    onTertiary = OnTertiaryDark,
)

private val LightColorScheme = lightColorScheme(
    primary = BluePrimaryLight,
    secondary = BlueSecondaryLight,
    tertiary = TertiaryColorLight,
    surface = SurfaceColorLight,
    onPrimary = OnPrimaryLight,
    onSecondary = OnSecondaryLight,
    onTertiary = OnTertiaryLight,
)

@Composable
fun TheDogAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
