package com.dev.touyou.ffmultiplier.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun HexKeypad(
    onInput: (Char) -> Unit,
    onDelete: () -> Unit,
    onSubmit: () -> Unit
) {
    val hexChars = "0123456789ABCDEF"
    Column(modifier = Modifier.padding(16.dp)) {
        hexChars.chunked(4).forEach { row ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                row.forEach { char ->
                    Button(onClick = { onInput(char) }) {
                        Text(text = char.toString())
                    }
                }
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(onClick = onDelete) {
                Text(text = "Delete")
            }
            Button(onClick = onSubmit) {
                Text(text = "Submit")
            }
        }
    }
}