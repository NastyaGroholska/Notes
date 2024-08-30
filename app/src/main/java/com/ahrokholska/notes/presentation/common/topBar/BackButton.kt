package com.ahrokholska.notes.presentation.common.topBar

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ahrokholska.notes.R

@Composable
fun BackButton(onBackClick: () -> Unit = {}) {
    Row(
        modifier = Modifier.clickable { onBackClick() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier.size(10.dp),
            imageVector = Icons.Filled.ArrowBackIosNew,
            tint = MaterialTheme.colorScheme.primary,
            contentDescription = null
        )
        Spacer(modifier = Modifier.width(5.dp))
        Text(text = stringResource(R.string.back), color = MaterialTheme.colorScheme.primary)
    }
}

@Preview
@Composable
private fun BackButtonPreview() {
    BackButton()
}