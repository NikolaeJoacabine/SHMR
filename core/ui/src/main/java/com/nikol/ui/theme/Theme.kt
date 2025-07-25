package com.nikol.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.nikol.settings.color.AppColorScheme
import com.nikol.ui.theme.color.RoseBackgroundDark
import com.nikol.ui.theme.color.RoseBackgroundLight
import com.nikol.ui.theme.color.RoseErrorContainerDark
import com.nikol.ui.theme.color.RoseErrorContainerLight
import com.nikol.ui.theme.color.RoseErrorDark
import com.nikol.ui.theme.color.RoseErrorLight
import com.nikol.ui.theme.color.RoseInverseOnSurfaceDark
import com.nikol.ui.theme.color.RoseInverseOnSurfaceLight
import com.nikol.ui.theme.color.RoseInversePrimaryDark
import com.nikol.ui.theme.color.RoseInversePrimaryLight
import com.nikol.ui.theme.color.RoseInverseSurfaceDark
import com.nikol.ui.theme.color.RoseInverseSurfaceLight
import com.nikol.ui.theme.color.RoseOnBackgroundDark
import com.nikol.ui.theme.color.RoseOnBackgroundLight
import com.nikol.ui.theme.color.RoseOnErrorContainerDark
import com.nikol.ui.theme.color.RoseOnErrorContainerLight
import com.nikol.ui.theme.color.RoseOnErrorDark
import com.nikol.ui.theme.color.RoseOnErrorLight
import com.nikol.ui.theme.color.RoseOnPrimaryContainerDark
import com.nikol.ui.theme.color.RoseOnPrimaryContainerLight
import com.nikol.ui.theme.color.RoseOnPrimaryDark
import com.nikol.ui.theme.color.RoseOnPrimaryLight
import com.nikol.ui.theme.color.RoseOnSecondaryContainerDark
import com.nikol.ui.theme.color.RoseOnSecondaryContainerLight
import com.nikol.ui.theme.color.RoseOnSecondaryDark
import com.nikol.ui.theme.color.RoseOnSecondaryLight
import com.nikol.ui.theme.color.RoseOnSurfaceDark
import com.nikol.ui.theme.color.RoseOnSurfaceLight
import com.nikol.ui.theme.color.RoseOnSurfaceVariantDark
import com.nikol.ui.theme.color.RoseOnSurfaceVariantLight
import com.nikol.ui.theme.color.RoseOnTertiaryContainerDark
import com.nikol.ui.theme.color.RoseOnTertiaryContainerLight
import com.nikol.ui.theme.color.RoseOnTertiaryDark
import com.nikol.ui.theme.color.RoseOnTertiaryLight
import com.nikol.ui.theme.color.RoseOutlineDark
import com.nikol.ui.theme.color.RoseOutlineLight
import com.nikol.ui.theme.color.RosePrimaryContainerDark
import com.nikol.ui.theme.color.RosePrimaryContainerLight
import com.nikol.ui.theme.color.RosePrimaryDark
import com.nikol.ui.theme.color.RosePrimaryLight
import com.nikol.ui.theme.color.RoseSecondaryContainerDark
import com.nikol.ui.theme.color.RoseSecondaryContainerLight
import com.nikol.ui.theme.color.RoseSecondaryDark
import com.nikol.ui.theme.color.RoseSecondaryLight
import com.nikol.ui.theme.color.RoseSurfaceBrightDark
import com.nikol.ui.theme.color.RoseSurfaceBrightLight
import com.nikol.ui.theme.color.RoseSurfaceContainerDark
import com.nikol.ui.theme.color.RoseSurfaceContainerHighDark
import com.nikol.ui.theme.color.RoseSurfaceContainerHighLight
import com.nikol.ui.theme.color.RoseSurfaceContainerHighestDark
import com.nikol.ui.theme.color.RoseSurfaceContainerHighestLight
import com.nikol.ui.theme.color.RoseSurfaceContainerLight
import com.nikol.ui.theme.color.RoseSurfaceContainerLowDark
import com.nikol.ui.theme.color.RoseSurfaceContainerLowLight
import com.nikol.ui.theme.color.RoseSurfaceContainerLowestDark
import com.nikol.ui.theme.color.RoseSurfaceContainerLowestLight
import com.nikol.ui.theme.color.RoseSurfaceDark
import com.nikol.ui.theme.color.RoseSurfaceDimDark
import com.nikol.ui.theme.color.RoseSurfaceDimLight
import com.nikol.ui.theme.color.RoseSurfaceLight
import com.nikol.ui.theme.color.RoseSurfaceVariantDark
import com.nikol.ui.theme.color.RoseSurfaceVariantLight
import com.nikol.ui.theme.color.RoseTertiaryContainerDark
import com.nikol.ui.theme.color.RoseTertiaryContainerLight
import com.nikol.ui.theme.color.RoseTertiaryDark
import com.nikol.ui.theme.color.RoseTertiaryLight
import com.nikol.ui.theme.color.greenBackgroundDark
import com.nikol.ui.theme.color.greenBackgroundLight
import com.nikol.ui.theme.color.greenErrorContainerDark
import com.nikol.ui.theme.color.greenErrorContainerLight
import com.nikol.ui.theme.color.greenErrorDark
import com.nikol.ui.theme.color.greenErrorLight
import com.nikol.ui.theme.color.greenInverseOnSurfaceDark
import com.nikol.ui.theme.color.greenInverseOnSurfaceLight
import com.nikol.ui.theme.color.greenInversePrimaryDark
import com.nikol.ui.theme.color.greenInversePrimaryLight
import com.nikol.ui.theme.color.greenInverseSurfaceDark
import com.nikol.ui.theme.color.greenInverseSurfaceLight
import com.nikol.ui.theme.color.greenOnBackgroundDark
import com.nikol.ui.theme.color.greenOnBackgroundLight
import com.nikol.ui.theme.color.greenOnErrorContainerDark
import com.nikol.ui.theme.color.greenOnErrorContainerLight
import com.nikol.ui.theme.color.greenOnErrorDark
import com.nikol.ui.theme.color.greenOnErrorLight
import com.nikol.ui.theme.color.greenOnPrimaryContainerDark
import com.nikol.ui.theme.color.greenOnPrimaryContainerLight
import com.nikol.ui.theme.color.greenOnPrimaryDark
import com.nikol.ui.theme.color.greenOnPrimaryLight
import com.nikol.ui.theme.color.greenOnSecondaryContainerDark
import com.nikol.ui.theme.color.greenOnSecondaryContainerLight
import com.nikol.ui.theme.color.greenOnSecondaryDark
import com.nikol.ui.theme.color.greenOnSecondaryLight
import com.nikol.ui.theme.color.greenOnSurfaceDark
import com.nikol.ui.theme.color.greenOnSurfaceLight
import com.nikol.ui.theme.color.greenOnSurfaceVariantDark
import com.nikol.ui.theme.color.greenOnSurfaceVariantLight
import com.nikol.ui.theme.color.greenOnTertiaryContainerDark
import com.nikol.ui.theme.color.greenOnTertiaryContainerLight
import com.nikol.ui.theme.color.greenOnTertiaryDark
import com.nikol.ui.theme.color.greenOnTertiaryLight
import com.nikol.ui.theme.color.greenOutlineDark
import com.nikol.ui.theme.color.greenOutlineLight
import com.nikol.ui.theme.color.greenOutlineVariantDark
import com.nikol.ui.theme.color.greenOutlineVariantLight
import com.nikol.ui.theme.color.greenPrimaryContainerDark
import com.nikol.ui.theme.color.greenPrimaryContainerLight
import com.nikol.ui.theme.color.greenPrimaryDark
import com.nikol.ui.theme.color.greenPrimaryLight
import com.nikol.ui.theme.color.greenScrimDark
import com.nikol.ui.theme.color.greenScrimLight
import com.nikol.ui.theme.color.greenSecondaryContainerDark
import com.nikol.ui.theme.color.greenSecondaryContainerLight
import com.nikol.ui.theme.color.greenSecondaryDark
import com.nikol.ui.theme.color.greenSecondaryLight
import com.nikol.ui.theme.color.greenSurfaceBrightDark
import com.nikol.ui.theme.color.greenSurfaceBrightLight
import com.nikol.ui.theme.color.greenSurfaceContainerDark
import com.nikol.ui.theme.color.greenSurfaceContainerHighDark
import com.nikol.ui.theme.color.greenSurfaceContainerHighLight
import com.nikol.ui.theme.color.greenSurfaceContainerHighestDark
import com.nikol.ui.theme.color.greenSurfaceContainerHighestLight
import com.nikol.ui.theme.color.greenSurfaceContainerLight
import com.nikol.ui.theme.color.greenSurfaceContainerLowDark
import com.nikol.ui.theme.color.greenSurfaceContainerLowLight
import com.nikol.ui.theme.color.greenSurfaceContainerLowestDark
import com.nikol.ui.theme.color.greenSurfaceContainerLowestLight
import com.nikol.ui.theme.color.greenSurfaceDark
import com.nikol.ui.theme.color.greenSurfaceDimDark
import com.nikol.ui.theme.color.greenSurfaceDimLight
import com.nikol.ui.theme.color.greenSurfaceLight
import com.nikol.ui.theme.color.greenSurfaceVariantDark
import com.nikol.ui.theme.color.greenSurfaceVariantLight
import com.nikol.ui.theme.color.greenTertiaryContainerDark
import com.nikol.ui.theme.color.greenTertiaryContainerLight
import com.nikol.ui.theme.color.greenTertiaryDark
import com.nikol.ui.theme.color.greenTertiaryLight
import com.nikol.ui.theme.color.purpleBackgroundDark
import com.nikol.ui.theme.color.purpleBackgroundLight
import com.nikol.ui.theme.color.purpleErrorContainerDark
import com.nikol.ui.theme.color.purpleErrorContainerLight
import com.nikol.ui.theme.color.purpleErrorDark
import com.nikol.ui.theme.color.purpleErrorLight
import com.nikol.ui.theme.color.purpleInverseOnSurfaceDark
import com.nikol.ui.theme.color.purpleInverseOnSurfaceLight
import com.nikol.ui.theme.color.purpleInversePrimaryDark
import com.nikol.ui.theme.color.purpleInversePrimaryLight
import com.nikol.ui.theme.color.purpleInverseSurfaceDark
import com.nikol.ui.theme.color.purpleInverseSurfaceLight
import com.nikol.ui.theme.color.purpleOnBackgroundDark
import com.nikol.ui.theme.color.purpleOnBackgroundLight
import com.nikol.ui.theme.color.purpleOnErrorContainerDark
import com.nikol.ui.theme.color.purpleOnErrorContainerLight
import com.nikol.ui.theme.color.purpleOnErrorDark
import com.nikol.ui.theme.color.purpleOnErrorLight
import com.nikol.ui.theme.color.purpleOnPrimaryContainerDark
import com.nikol.ui.theme.color.purpleOnPrimaryContainerLight
import com.nikol.ui.theme.color.purpleOnPrimaryDark
import com.nikol.ui.theme.color.purpleOnPrimaryLight
import com.nikol.ui.theme.color.purpleOnSecondaryContainerDark
import com.nikol.ui.theme.color.purpleOnSecondaryContainerLight
import com.nikol.ui.theme.color.purpleOnSecondaryDark
import com.nikol.ui.theme.color.purpleOnSecondaryLight
import com.nikol.ui.theme.color.purpleOnSurfaceDark
import com.nikol.ui.theme.color.purpleOnSurfaceLight
import com.nikol.ui.theme.color.purpleOnSurfaceVariantDark
import com.nikol.ui.theme.color.purpleOnSurfaceVariantLight
import com.nikol.ui.theme.color.purpleOnTertiaryContainerDark
import com.nikol.ui.theme.color.purpleOnTertiaryContainerLight
import com.nikol.ui.theme.color.purpleOnTertiaryDark
import com.nikol.ui.theme.color.purpleOnTertiaryLight
import com.nikol.ui.theme.color.purpleOutlineDark
import com.nikol.ui.theme.color.purpleOutlineLight
import com.nikol.ui.theme.color.purpleOutlineVariantDark
import com.nikol.ui.theme.color.purpleOutlineVariantLight
import com.nikol.ui.theme.color.purplePrimaryContainerDark
import com.nikol.ui.theme.color.purplePrimaryContainerLight
import com.nikol.ui.theme.color.purplePrimaryDark
import com.nikol.ui.theme.color.purplePrimaryLight
import com.nikol.ui.theme.color.purpleScrimDark
import com.nikol.ui.theme.color.purpleScrimLight
import com.nikol.ui.theme.color.purpleSecondaryContainerDark
import com.nikol.ui.theme.color.purpleSecondaryContainerLight
import com.nikol.ui.theme.color.purpleSecondaryDark
import com.nikol.ui.theme.color.purpleSecondaryLight
import com.nikol.ui.theme.color.purpleSurfaceBrightDark
import com.nikol.ui.theme.color.purpleSurfaceBrightLight
import com.nikol.ui.theme.color.purpleSurfaceContainerDark
import com.nikol.ui.theme.color.purpleSurfaceContainerHighDark
import com.nikol.ui.theme.color.purpleSurfaceContainerHighLight
import com.nikol.ui.theme.color.purpleSurfaceContainerHighestDark
import com.nikol.ui.theme.color.purpleSurfaceContainerHighestLight
import com.nikol.ui.theme.color.purpleSurfaceContainerLight
import com.nikol.ui.theme.color.purpleSurfaceContainerLowDark
import com.nikol.ui.theme.color.purpleSurfaceContainerLowLight
import com.nikol.ui.theme.color.purpleSurfaceContainerLowestDark
import com.nikol.ui.theme.color.purpleSurfaceContainerLowestLight
import com.nikol.ui.theme.color.purpleSurfaceDark
import com.nikol.ui.theme.color.purpleSurfaceDimDark
import com.nikol.ui.theme.color.purpleSurfaceDimLight
import com.nikol.ui.theme.color.purpleSurfaceLight
import com.nikol.ui.theme.color.purpleSurfaceVariantDark
import com.nikol.ui.theme.color.purpleSurfaceVariantLight
import com.nikol.ui.theme.color.purpleTertiaryContainerDark
import com.nikol.ui.theme.color.purpleTertiaryContainerLight
import com.nikol.ui.theme.color.purpleTertiaryDark
import com.nikol.ui.theme.color.purpleTertiaryLight
import com.nikol.ui.theme.color.violetBackgroundDark
import com.nikol.ui.theme.color.violetBackgroundLight
import com.nikol.ui.theme.color.violetErrorContainerDark
import com.nikol.ui.theme.color.violetErrorContainerLight
import com.nikol.ui.theme.color.violetErrorDark
import com.nikol.ui.theme.color.violetErrorLight
import com.nikol.ui.theme.color.violetInverseOnSurfaceDark
import com.nikol.ui.theme.color.violetInverseOnSurfaceLight
import com.nikol.ui.theme.color.violetInversePrimaryDark
import com.nikol.ui.theme.color.violetInversePrimaryLight
import com.nikol.ui.theme.color.violetInverseSurfaceDark
import com.nikol.ui.theme.color.violetInverseSurfaceLight
import com.nikol.ui.theme.color.violetOnBackgroundDark
import com.nikol.ui.theme.color.violetOnBackgroundLight
import com.nikol.ui.theme.color.violetOnErrorContainerDark
import com.nikol.ui.theme.color.violetOnErrorContainerLight
import com.nikol.ui.theme.color.violetOnErrorDark
import com.nikol.ui.theme.color.violetOnErrorLight
import com.nikol.ui.theme.color.violetOnPrimaryContainerDark
import com.nikol.ui.theme.color.violetOnPrimaryContainerLight
import com.nikol.ui.theme.color.violetOnPrimaryDark
import com.nikol.ui.theme.color.violetOnPrimaryLight
import com.nikol.ui.theme.color.violetOnSecondaryContainerDark
import com.nikol.ui.theme.color.violetOnSecondaryContainerLight
import com.nikol.ui.theme.color.violetOnSecondaryDark
import com.nikol.ui.theme.color.violetOnSecondaryLight
import com.nikol.ui.theme.color.violetOnSurfaceDark
import com.nikol.ui.theme.color.violetOnSurfaceLight
import com.nikol.ui.theme.color.violetOnSurfaceVariantDark
import com.nikol.ui.theme.color.violetOnSurfaceVariantLight
import com.nikol.ui.theme.color.violetOnTertiaryContainerDark
import com.nikol.ui.theme.color.violetOnTertiaryContainerLight
import com.nikol.ui.theme.color.violetOnTertiaryDark
import com.nikol.ui.theme.color.violetOnTertiaryLight
import com.nikol.ui.theme.color.violetOutlineDark
import com.nikol.ui.theme.color.violetOutlineLight
import com.nikol.ui.theme.color.violetOutlineVariantDark
import com.nikol.ui.theme.color.violetOutlineVariantLight
import com.nikol.ui.theme.color.violetPrimaryContainerDark
import com.nikol.ui.theme.color.violetPrimaryContainerLight
import com.nikol.ui.theme.color.violetPrimaryDark
import com.nikol.ui.theme.color.violetPrimaryLight
import com.nikol.ui.theme.color.violetScrimDark
import com.nikol.ui.theme.color.violetScrimLight
import com.nikol.ui.theme.color.violetSecondaryContainerDark
import com.nikol.ui.theme.color.violetSecondaryContainerLight
import com.nikol.ui.theme.color.violetSecondaryDark
import com.nikol.ui.theme.color.violetSecondaryLight
import com.nikol.ui.theme.color.violetSurfaceBrightDark
import com.nikol.ui.theme.color.violetSurfaceBrightLight
import com.nikol.ui.theme.color.violetSurfaceContainerDark
import com.nikol.ui.theme.color.violetSurfaceContainerHighDark
import com.nikol.ui.theme.color.violetSurfaceContainerHighLight
import com.nikol.ui.theme.color.violetSurfaceContainerHighestDark
import com.nikol.ui.theme.color.violetSurfaceContainerHighestLight
import com.nikol.ui.theme.color.violetSurfaceContainerLight
import com.nikol.ui.theme.color.violetSurfaceContainerLowDark
import com.nikol.ui.theme.color.violetSurfaceContainerLowLight
import com.nikol.ui.theme.color.violetSurfaceContainerLowestDark
import com.nikol.ui.theme.color.violetSurfaceContainerLowestLight
import com.nikol.ui.theme.color.violetSurfaceDark
import com.nikol.ui.theme.color.violetSurfaceDimDark
import com.nikol.ui.theme.color.violetSurfaceDimLight
import com.nikol.ui.theme.color.violetSurfaceLight
import com.nikol.ui.theme.color.violetSurfaceVariantDark
import com.nikol.ui.theme.color.violetSurfaceVariantLight
import com.nikol.ui.theme.color.violetTertiaryContainerDark
import com.nikol.ui.theme.color.violetTertiaryContainerLight
import com.nikol.ui.theme.color.violetTertiaryDark
import com.nikol.ui.theme.color.violetTertiaryLight

val lightGreenColorScheme = lightColorScheme(
    primary = greenPrimaryLight,
    onPrimary = greenOnPrimaryLight,
    primaryContainer = greenPrimaryContainerLight,
    onPrimaryContainer = greenOnPrimaryContainerLight,
    secondary = greenSecondaryLight,
    onSecondary = greenOnSecondaryLight,
    secondaryContainer = greenSecondaryContainerLight,
    onSecondaryContainer = greenOnSecondaryContainerLight,
    tertiary = greenTertiaryLight,
    onTertiary = greenOnTertiaryLight,
    tertiaryContainer = greenTertiaryContainerLight,
    onTertiaryContainer = greenOnTertiaryContainerLight,
    error = greenErrorLight,
    onError = greenOnErrorLight,
    errorContainer = greenErrorContainerLight,
    onErrorContainer = greenOnErrorContainerLight,
    background = greenBackgroundLight,
    onBackground = greenOnBackgroundLight,
    surface = greenSurfaceLight,
    onSurface = greenOnSurfaceLight,
    surfaceVariant = greenSurfaceVariantLight,
    onSurfaceVariant = greenOnSurfaceVariantLight,
    outline = greenOutlineLight,
    outlineVariant = greenOutlineVariantLight,
    scrim = greenScrimLight,
    inverseSurface = greenInverseSurfaceLight,
    inverseOnSurface = greenInverseOnSurfaceLight,
    inversePrimary = greenInversePrimaryLight,
    surfaceDim = greenSurfaceDimLight,
    surfaceBright = greenSurfaceBrightLight,
    surfaceContainerLowest = greenSurfaceContainerLowestLight,
    surfaceContainerLow = greenSurfaceContainerLowLight,
    surfaceContainer = greenSurfaceContainerLight,
    surfaceContainerHigh = greenSurfaceContainerHighLight,
    surfaceContainerHighest = greenSurfaceContainerHighestLight,
)

val darkGreenColorScheme = darkColorScheme(
    primary = greenPrimaryDark,
    onPrimary = greenOnPrimaryDark,
    primaryContainer = greenPrimaryContainerDark,
    onPrimaryContainer = greenOnPrimaryContainerDark,
    secondary = greenSecondaryDark,
    onSecondary = greenOnSecondaryDark,
    secondaryContainer = greenSecondaryContainerDark,
    onSecondaryContainer = greenOnSecondaryContainerDark,
    tertiary = greenTertiaryDark,
    onTertiary = greenOnTertiaryDark,
    tertiaryContainer = greenTertiaryContainerDark,
    onTertiaryContainer = greenOnTertiaryContainerDark,
    error = greenErrorDark,
    onError = greenOnErrorDark,
    errorContainer = greenErrorContainerDark,
    onErrorContainer = greenOnErrorContainerDark,
    background = greenBackgroundDark,
    onBackground = greenOnBackgroundDark,
    surface = greenSurfaceDark,
    onSurface = greenOnSurfaceDark,
    surfaceVariant = greenSurfaceVariantDark,
    onSurfaceVariant = greenOnSurfaceVariantDark,
    outline = greenOutlineDark,
    outlineVariant = greenOutlineVariantDark,
    scrim = greenScrimDark,
    inverseSurface = greenInverseSurfaceDark,
    inverseOnSurface = greenInverseOnSurfaceDark,
    inversePrimary = greenInversePrimaryDark,
    surfaceDim = greenSurfaceDimDark,
    surfaceBright = greenSurfaceBrightDark,
    surfaceContainerLowest = greenSurfaceContainerLowestDark,
    surfaceContainerLow = greenSurfaceContainerLowDark,
    surfaceContainer = greenSurfaceContainerDark,
    surfaceContainerHigh = greenSurfaceContainerHighDark,
    surfaceContainerHighest = greenSurfaceContainerHighestDark,
)

val lightPurpleColorScheme = lightColorScheme(
    primary = purplePrimaryLight,
    onPrimary = purpleOnPrimaryLight,
    primaryContainer = purplePrimaryContainerLight,
    onPrimaryContainer = purpleOnPrimaryContainerLight,
    inversePrimary = purpleInversePrimaryLight,
    secondary = purpleSecondaryLight,
    onSecondary = purpleOnSecondaryLight,
    secondaryContainer = purpleSecondaryContainerLight,
    onSecondaryContainer = purpleOnSecondaryContainerLight,
    tertiary = purpleTertiaryLight,
    onTertiary = purpleOnTertiaryLight,
    tertiaryContainer = purpleTertiaryContainerLight,
    onTertiaryContainer = purpleOnTertiaryContainerLight,
    background = purpleBackgroundLight,
    onBackground = purpleOnBackgroundLight,
    surface = purpleSurfaceLight,
    onSurface = purpleOnSurfaceLight,
    surfaceVariant = purpleSurfaceVariantLight,
    onSurfaceVariant = purpleOnSurfaceVariantLight,
    surfaceTint = purplePrimaryLight,
    inverseSurface = purpleInverseSurfaceLight,
    inverseOnSurface = purpleInverseOnSurfaceLight,
    error = purpleErrorLight,
    onError = purpleOnErrorLight,
    errorContainer = purpleErrorContainerLight,
    onErrorContainer = purpleOnErrorContainerLight,
    outline = purpleOutlineLight,
    outlineVariant = purpleOutlineVariantLight,
    scrim = purpleScrimLight,
    surfaceDim = purpleSurfaceDimLight,
    surfaceBright = purpleSurfaceBrightLight,
    surfaceContainerLowest = purpleSurfaceContainerLowestLight,
    surfaceContainerLow = purpleSurfaceContainerLowLight,
    surfaceContainer = purpleSurfaceContainerLight,
    surfaceContainerHigh = purpleSurfaceContainerHighLight,
    surfaceContainerHighest = purpleSurfaceContainerHighestLight
)

val darkPurpleColorScheme = darkColorScheme(
    primary = purplePrimaryDark,
    onPrimary = purpleOnPrimaryDark,
    primaryContainer = purplePrimaryContainerDark,
    onPrimaryContainer = purpleOnPrimaryContainerDark,
    inversePrimary = purpleInversePrimaryDark,
    secondary = purpleSecondaryDark,
    onSecondary = purpleOnSecondaryDark,
    secondaryContainer = purpleSecondaryContainerDark,
    onSecondaryContainer = purpleOnSecondaryContainerDark,
    tertiary = purpleTertiaryDark,
    onTertiary = purpleOnTertiaryDark,
    tertiaryContainer = purpleTertiaryContainerDark,
    onTertiaryContainer = purpleOnTertiaryContainerDark,
    background = purpleBackgroundDark,
    onBackground = purpleOnBackgroundDark,
    surface = purpleSurfaceDark,
    onSurface = purpleOnSurfaceDark,
    surfaceVariant = purpleSurfaceVariantDark,
    onSurfaceVariant = purpleOnSurfaceVariantDark,
    surfaceTint = purplePrimaryDark,
    inverseSurface = purpleInverseSurfaceDark,
    inverseOnSurface = purpleInverseOnSurfaceDark,
    error = purpleErrorDark,
    onError = purpleOnErrorDark,
    errorContainer = purpleErrorContainerDark,
    onErrorContainer = purpleOnErrorContainerDark,
    outline = purpleOutlineDark,
    outlineVariant = purpleOutlineVariantDark,
    scrim = purpleScrimDark,
    surfaceDim = purpleSurfaceDimDark,
    surfaceBright = purpleSurfaceBrightDark,
    surfaceContainerLowest = purpleSurfaceContainerLowestDark,
    surfaceContainerLow = purpleSurfaceContainerLowDark,
    surfaceContainer = purpleSurfaceContainerDark,
    surfaceContainerHigh = purpleSurfaceContainerHighDark,
    surfaceContainerHighest = purpleSurfaceContainerHighestDark
)

val lightRoseScheme = lightColorScheme(
    primary = RosePrimaryLight,
    onPrimary = RoseOnPrimaryLight,
    primaryContainer = RosePrimaryContainerLight,
    onPrimaryContainer = RoseOnPrimaryContainerLight,
    secondary = RoseSecondaryLight,
    onSecondary = RoseOnSecondaryLight,
    secondaryContainer = RoseSecondaryContainerLight,
    onSecondaryContainer = RoseOnSecondaryContainerLight,
    tertiary = RoseTertiaryLight,
    onTertiary = RoseOnTertiaryLight,
    tertiaryContainer = RoseTertiaryContainerLight,
    onTertiaryContainer = RoseOnTertiaryContainerLight,
    error = RoseErrorLight,
    onError = RoseOnErrorLight,
    errorContainer = RoseErrorContainerLight,
    onErrorContainer = RoseOnErrorContainerLight,
    background = RoseBackgroundLight,
    onBackground = RoseOnBackgroundLight,
    surface = RoseSurfaceLight,
    onSurface = RoseOnSurfaceLight,
    surfaceVariant = RoseSurfaceVariantLight,
    onSurfaceVariant = RoseOnSurfaceVariantLight,
    outline = RoseOutlineLight,
    inverseOnSurface = RoseInverseOnSurfaceLight,
    inverseSurface = RoseInverseSurfaceLight,
    inversePrimary = RoseInversePrimaryLight,
    surfaceDim = RoseSurfaceDimLight,
    surfaceBright = RoseSurfaceBrightLight,
    surfaceContainerLowest = RoseSurfaceContainerLowestLight,
    surfaceContainerLow = RoseSurfaceContainerLowLight,
    surfaceContainer = RoseSurfaceContainerLight,
    surfaceContainerHigh = RoseSurfaceContainerHighLight,
    surfaceContainerHighest = RoseSurfaceContainerHighestLight
)

val darkRoseScheme = darkColorScheme(
    primary = RosePrimaryDark,
    onPrimary = RoseOnPrimaryDark,
    primaryContainer = RosePrimaryContainerDark,
    onPrimaryContainer = RoseOnPrimaryContainerDark,
    secondary = RoseSecondaryDark,
    onSecondary = RoseOnSecondaryDark,
    secondaryContainer = RoseSecondaryContainerDark,
    onSecondaryContainer = RoseOnSecondaryContainerDark,
    tertiary = RoseTertiaryDark,
    onTertiary = RoseOnTertiaryDark,
    tertiaryContainer = RoseTertiaryContainerDark,
    onTertiaryContainer = RoseOnTertiaryContainerDark,
    error = RoseErrorDark,
    onError = RoseOnErrorDark,
    errorContainer = RoseErrorContainerDark,
    onErrorContainer = RoseOnErrorContainerDark,
    background = RoseBackgroundDark,
    onBackground = RoseOnBackgroundDark,
    surface = RoseSurfaceDark,
    onSurface = RoseOnSurfaceDark,
    surfaceVariant = RoseSurfaceVariantDark,
    onSurfaceVariant = RoseOnSurfaceVariantDark,
    outline = RoseOutlineDark,
    inverseOnSurface = RoseInverseOnSurfaceDark,
    inverseSurface = RoseInverseSurfaceDark,
    inversePrimary = RoseInversePrimaryDark,
    surfaceDim = RoseSurfaceDimDark,
    surfaceBright = RoseSurfaceBrightDark,
    surfaceContainerLowest = RoseSurfaceContainerLowestDark,
    surfaceContainerLow = RoseSurfaceContainerLowDark,
    surfaceContainer = RoseSurfaceContainerDark,
    surfaceContainerHigh = RoseSurfaceContainerHighDark,
    surfaceContainerHighest = RoseSurfaceContainerHighestDark
)

val lightVioletColorScheme = lightColorScheme(
    primary = violetPrimaryLight,
    onPrimary = violetOnPrimaryLight,
    primaryContainer = violetPrimaryContainerLight,
    onPrimaryContainer = violetOnPrimaryContainerLight,
    secondary = violetSecondaryLight,
    onSecondary = violetOnSecondaryLight,
    secondaryContainer = violetSecondaryContainerLight,
    onSecondaryContainer = violetOnSecondaryContainerLight,
    tertiary = violetTertiaryLight,
    onTertiary = violetOnTertiaryLight,
    tertiaryContainer = violetTertiaryContainerLight,
    onTertiaryContainer = violetOnTertiaryContainerLight,
    error = violetErrorLight,
    onError = violetOnErrorLight,
    errorContainer = violetErrorContainerLight,
    onErrorContainer = violetOnErrorContainerLight,
    background = violetBackgroundLight,
    onBackground = violetOnBackgroundLight,
    surface = violetSurfaceLight,
    onSurface = violetOnSurfaceLight,
    surfaceVariant = violetSurfaceVariantLight,
    onSurfaceVariant = violetOnSurfaceVariantLight,
    outline = violetOutlineLight,
    outlineVariant = violetOutlineVariantLight,
    scrim = violetScrimLight,
    inverseSurface = violetInverseSurfaceLight,
    inverseOnSurface = violetInverseOnSurfaceLight,
    inversePrimary = violetInversePrimaryLight,
    surfaceDim = violetSurfaceDimLight,
    surfaceBright = violetSurfaceBrightLight,
    surfaceContainerLowest = violetSurfaceContainerLowestLight,
    surfaceContainerLow = violetSurfaceContainerLowLight,
    surfaceContainer = violetSurfaceContainerLight,
    surfaceContainerHigh = violetSurfaceContainerHighLight,
    surfaceContainerHighest = violetSurfaceContainerHighestLight,
    surfaceTint = violetPrimaryLight
)

val darkVioletColorScheme = darkColorScheme(
    primary = violetPrimaryDark,
    onPrimary = violetOnPrimaryDark,
    primaryContainer = violetPrimaryContainerDark,
    onPrimaryContainer = violetOnPrimaryContainerDark,
    secondary = violetSecondaryDark,
    onSecondary = violetOnSecondaryDark,
    secondaryContainer = violetSecondaryContainerDark,
    onSecondaryContainer = violetOnSecondaryContainerDark,
    tertiary = violetTertiaryDark,
    onTertiary = violetOnTertiaryDark,
    tertiaryContainer = violetTertiaryContainerDark,
    onTertiaryContainer = violetOnTertiaryContainerDark,
    error = violetErrorDark,
    onError = violetOnErrorDark,
    errorContainer = violetErrorContainerDark,
    onErrorContainer = violetOnErrorContainerDark,
    background = violetBackgroundDark,
    onBackground = violetOnBackgroundDark,
    surface = violetSurfaceDark,
    onSurface = violetOnSurfaceDark,
    surfaceVariant = violetSurfaceVariantDark,
    onSurfaceVariant = violetOnSurfaceVariantDark,
    outline = violetOutlineDark,
    outlineVariant = violetOutlineVariantDark,
    scrim = violetScrimDark,
    inverseSurface = violetInverseSurfaceDark,
    inverseOnSurface = violetInverseOnSurfaceDark,
    inversePrimary = violetInversePrimaryDark,
    surfaceDim = violetSurfaceDimDark,
    surfaceBright = violetSurfaceBrightDark,
    surfaceContainerLowest = violetSurfaceContainerLowestDark,
    surfaceContainerLow = violetSurfaceContainerLowDark,
    surfaceContainer = violetSurfaceContainerDark,
    surfaceContainerHigh = violetSurfaceContainerHighDark,
    surfaceContainerHighest = violetSurfaceContainerHighestDark,
    surfaceTint = violetPrimaryDark
)

@Composable
fun YandexSchoolTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    appColorScheme: AppColorScheme = AppColorScheme.Green,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        else -> when (appColorScheme) {
            AppColorScheme.Green -> if (darkTheme) darkGreenColorScheme else lightGreenColorScheme
            AppColorScheme.Purple -> if (darkTheme) darkPurpleColorScheme else lightPurpleColorScheme
            AppColorScheme.Rose -> if (darkTheme) darkRoseScheme else lightRoseScheme
            AppColorScheme.Violet -> if (darkTheme) darkVioletColorScheme else lightVioletColorScheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = AppTypography,
        content = content
    )
}
