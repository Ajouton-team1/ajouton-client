package com.ajouton.tortee.ui.screen

import androidx.compose.material.Button
import androidx.compose.runtime.Composable
import com.ajouton.tortee.TorTeeViewModel
import com.ajouton.tortee.model.User
import com.ajouton.tortee.network.UserSignUpRequest

@Composable
fun SignUpScreen(
    viewModel: TorTeeViewModel
) {
    val userSignUpRequest = UserSignUpRequest("email", "name", "nickname", "password")
    Button(onClick = {
        viewModel.signUp(userSignUpRequest)
    }) {

    }
}