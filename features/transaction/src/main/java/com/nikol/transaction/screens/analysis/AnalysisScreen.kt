package com.nikol.transaction.screens.analysis

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.nikol.transaction.R
import com.nikol.transaction.screens.analysis.components.AnalysisContentSection
import com.nikol.transaction.screens.analysis.stateHoisting.AnalysisScreenAction
import com.nikol.transaction.screens.analysis.stateHoisting.AnalysisScreenEffect
import com.nikol.transaction.screens.analysis.stateHoisting.AnalysisScreenState
import com.nikol.ui.customUiComponents.ErrorDateDialog
import com.nikol.ui.customUiComponents.ShowDatePickerDialog
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun AnalysisScreen(
    viewModel: AnalysisScreenViewModel,
    onNavigateBack: () -> Unit
) {
    val state = viewModel.state.collectAsStateWithLifecycle()
    val editingDateType = remember { mutableStateOf<DateType?>(null) }
    val showErrorDialog = remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is AnalysisScreenEffect.NavigateBack ->
                    onNavigateBack()

                is AnalysisScreenEffect.OpenEndDateEditDialog ->
                    editingDateType.value = DateType.END

                is AnalysisScreenEffect.OpenStartDateEditDialog ->
                    editingDateType.value = DateType.START

                is AnalysisScreenEffect.OpenErrorDialog -> showErrorDialog.value = true
            }
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = stringResource(R.string.analysis))
                },
                navigationIcon = {
                    IconButton(
                        onClick = { viewModel.onAction(AnalysisScreenAction.OnNavigateBack) }
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "back"
                        )
                    }

                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier.padding(top = paddingValues.calculateTopPadding())
        ) {
            if (state.value is AnalysisScreenState.Content) {
                val currentState = state.value as AnalysisScreenState.Content
                AnalysisContentSection(
                    content = currentState,
                    onAction = { viewModel.onAction(it) }
                )
            }
        }

        editingDateType.value?.let {
            val currentState = state.value as? AnalysisScreenState.Content ?: return@Scaffold
            val initialDate = when (editingDateType.value) {
                DateType.START -> currentState.startDate
                DateType.END -> currentState.endDate
                else -> LocalDate.now()
            }

            ShowDatePickerDialog(
                initialDate = initialDate,
                onDismiss = { editingDateType.value = null },
                onDateSelected = { selectedDate ->
                    val type = editingDateType.value
                    editingDateType.value = null
                    when (type) {
                        DateType.START -> viewModel.onAction(
                            AnalysisScreenAction.StartDeteEdit(
                                selectedDate
                            )
                        )

                        DateType.END -> viewModel.onAction(
                            AnalysisScreenAction.EndDateEdit(
                                selectedDate
                            )
                        )

                        else -> Unit
                    }
                }
            )
        }

        ErrorDateDialog(
            showDialog = showErrorDialog.value,
            onDismiss = { showErrorDialog.value = false }
        )
    }
}
