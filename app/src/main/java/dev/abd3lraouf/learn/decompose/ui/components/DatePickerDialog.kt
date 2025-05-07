package dev.abd3lraouf.learn.decompose.ui.components

import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoDatePickerDialog(
    onDateSelected: (Long) -> Unit,
    onDismissRequest: () -> Unit
) {
    val datePickerState = rememberDatePickerState(
        selectableDates = object : SelectableDates {
            override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                // Only allow dates from today onwards
                val today = Calendar.getInstance().apply {
                    set(Calendar.HOUR_OF_DAY, 0)
                    set(Calendar.MINUTE, 0)
                    set(Calendar.SECOND, 0)
                    set(Calendar.MILLISECOND, 0)
                }.timeInMillis
                return utcTimeMillis >= today
            }
        }
    )

    DatePickerDialog(
        onDismissRequest = onDismissRequest,
        confirmButton = {
            Button(
                onClick = {
                    datePickerState.selectedDateMillis?.let { onDateSelected(it) }
                    onDismissRequest()
                }
            ) {
                Text("OK")
            }
        },
        dismissButton = {
            Button(onClick = onDismissRequest) {
                Text("Cancel")
            }
        }
    ) {
        DatePicker(state = datePickerState)
    }
} 