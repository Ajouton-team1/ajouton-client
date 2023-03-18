package com.ajouton.tortee.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.ajouton.tortee.TorTeeViewModel

@Composable
fun TorTeeMyPageScreen(
    modifier: Modifier,
    viewModel: TorTeeViewModel
) {
    Box(
        modifier = modifier
    ) {
        Text("MyPageScreen")
    }
}