package com.dev.touyou.ffmultiplier.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dev.touyou.ffmultiplier.models.FFProblem

@Composable
fun ProblemDisplay(problem: FFProblem?, userInput: String) {
    Column(
        modifier = Modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (problem != null) {
            Text(text = "${problem.val1.value0} x ${problem.val2.value0}", fontSize = 32.sp)
        } else {
            Text(text = "Finish!", fontSize = 32.sp)
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = userInput, fontSize = 24.sp)
    }
}