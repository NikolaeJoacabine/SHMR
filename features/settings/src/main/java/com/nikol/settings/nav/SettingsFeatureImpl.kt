package com.nikol.settings.nav

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.nikol.di.FeatureDependencies
import com.nikol.navigation.FeatureApi
import com.nikol.navigation.SettingsGraph
import com.nikol.settings.color.AppColorScheme
import com.nikol.settings.di.DaggerSettingsFeatureComponent
import com.nikol.settings.screens.SettingsViewModel
import com.nikol.settings.screens.appInfo.AppInfoScreen
import com.nikol.settings.screens.colorSelecter.ColorSelectionScreen
import com.nikol.settings.screens.pincode.PinCodeSelectScreen
import com.nikol.settings.screens.pincode.PinCodeViewModel
import com.nikol.settings.screens.selectLanguage.LocaleSettingsScreen
import com.nikol.settings.screens.setings.SettingsScreen
import com.nikol.settings.screens.vibrator.VibrationSettingsScreen
import com.nikol.settings.screens.workManager.SyncSettingsScreen
import com.nikol.ui.theme.color.RosePrimaryDark
import com.nikol.ui.theme.color.greenPrimaryDark
import com.nikol.ui.theme.color.purplePrimaryDark
import com.nikol.ui.theme.color.violetPrimaryDark

class SettingsFeatureImpl(
    private val dependencies: FeatureDependencies
) : FeatureApi {
    @RequiresApi(Build.VERSION_CODES.Q)
    override fun registerGraph(
        navGraphBuilder: NavGraphBuilder,
        navController: NavHostController,
        modifier: Modifier
    ) {
        val featureComponent = DaggerSettingsFeatureComponent.factory()
            .create(dependencies)

        navGraphBuilder.navigation<SettingsGraph>(
            startDestination = Settings
        ) {
            composable<Settings> {
                val viewModel = viewModel<SettingsViewModel>(
                    factory = featureComponent.viewModelFactory().create()
                )
                SettingsScreen(
                    viewModel = viewModel,
                    onClickColor = { navController.navigate(ColorPicker) },
                    onClickVibrator = { navController.navigate(Vibrator) },
                    onClickLanguage = { navController.navigate(LocaleSettings) },
                    onClickAddDetail = { navController.navigate(AppDetail) },
                    onclickWorker = { navController.navigate(WorkerSettings) },
                    onClickPinCode = { navController.navigate(PinCode) }
                )
            }

            composable<ColorPicker> {

                val viewModel = viewModel<SettingsViewModel>(
                    factory = featureComponent.viewModelFactory().create()
                )
                val state = viewModel.colorScheme.collectAsStateWithLifecycle()

                ColorSelectionScreen(
                    when (state.value) {
                        AppColorScheme.Green -> greenPrimaryDark
                        AppColorScheme.Purple -> purplePrimaryDark
                        AppColorScheme.Rose -> RosePrimaryDark
                        AppColorScheme.Violet -> violetPrimaryDark
                    },
                    onColorSelected = {
                        viewModel.setColorScheme(
                            when (it) {
                                greenPrimaryDark -> AppColorScheme.Green
                                purplePrimaryDark -> AppColorScheme.Purple
                                RosePrimaryDark -> AppColorScheme.Rose
                                violetPrimaryDark -> AppColorScheme.Violet
                                else -> AppColorScheme.Green
                            }
                        )
                    },
                    navigateBack = { navController.popBackStack() }
                )
            }

            composable<Vibrator> {
                val viewModel = viewModel<SettingsViewModel>(
                    factory = featureComponent.viewModelFactory().create()
                )
                VibrationSettingsScreen(viewModel) {
                    navController.popBackStack()
                }
            }

            composable<LocaleSettings> {
                val viewModel = viewModel<SettingsViewModel>(
                    factory = featureComponent.viewModelFactory().create()
                )
                LocaleSettingsScreen(viewModel) {
                    navController.popBackStack()
                }
            }

            composable<AppDetail> {
                val viewModel = viewModel<SettingsViewModel>(
                    factory = featureComponent.viewModelFactory().create()
                )
                AppInfoScreen(
                    viewModel
                ) {
                    navController.popBackStack()
                }
            }

            composable<WorkerSettings> {
                val viewModel = viewModel<SettingsViewModel>(
                    factory = featureComponent.viewModelFactory().create()
                )
                SyncSettingsScreen(viewModel) {
                    navController.popBackStack()
                }
            }

            composable<PinCode> {

                val viewModel = viewModel<PinCodeViewModel>(
                    factory = featureComponent.viewModelSecyrFactory()
                )
                val context = LocalContext.current
                PinCodeSelectScreen(
                    onPinEntered = {
                        viewModel.setPin(it)
                        Toast.makeText(context, "Пин код установлен", Toast.LENGTH_SHORT).show()
                        navController.popBackStack()
                    },
                    onBackClick = { navController.popBackStack() },
                    onClickClear = {
                        viewModel.clearPin()
                        Toast.makeText(context, "Пин код очищен", Toast.LENGTH_SHORT).show()
                        navController.popBackStack()
                    }
                )
            }
        }
    }
}
