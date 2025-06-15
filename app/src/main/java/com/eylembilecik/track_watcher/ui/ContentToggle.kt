package com.eylembilecik.track_watcher.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ContentToggle(
    isSeriesMode: Boolean,
    onToggle: (Boolean) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(16.dp)
    ) {
        Text("Movies")
        Switch(
            checked = isSeriesMode,
            onCheckedChange = { onToggle(it) },
            modifier = Modifier.padding(horizontal = 8.dp)
        )
        Text("TV Series")
    }
}
