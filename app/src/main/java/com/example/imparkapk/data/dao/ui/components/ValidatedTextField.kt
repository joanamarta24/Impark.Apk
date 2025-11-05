package com.example.imparktcc.ui.components

import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.VisualTransformation

@Composable
fun ValidatedTextField(
    value: String,
    onVAlueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    isError: Boolean = false,
    supportingText: String? = null,
    visualTransformation: VisualTransformation = VisualTransformation.None
){
    OutlinedTextField(
        value = value,
        onValueChange = onVAlueChange,
        label = { Text(label)},
        modifier = Modifier,
        isError = isError,
        supportingText = {
            if (supportingText != null){
                Text(supportingText)
            }
        },
        visualTransformation = visualTransformation
    )

}