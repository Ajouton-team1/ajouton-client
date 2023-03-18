package com.ajouton.tortee.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.ajouton.tortee.TorTeeViewModel
import com.ajouton.tortee.network.UserSignInRequest

@Composable
fun SignInScreen(
    viewModel: TorTeeViewModel
) {
    val userSignUpRequest = UserSignInRequest("email", "password")
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        TextField(
            value = viewModel.userIdInput.collectAsState().value,
            onValueChange = {
                viewModel.updateUserIdInput(it)
            },
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Next
            ),
        )
        Spacer(modifier = Modifier.padding(12.dp))
        TextField(
            value = viewModel.userPasswordInput.collectAsState().value,
            onValueChange = {
                viewModel.updateUserPasswordInput(it)
            },
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done
            )
        )
        Spacer(modifier = Modifier.padding(12.dp))
        Button(
            onClick = {
                viewModel.signIn(userSignUpRequest)
            }
        ) {
            Text("Sign In")
        }
        Spacer(modifier = Modifier.padding(12.dp))
        Button(
            onClick = {
                viewModel.setSignUpPageVisibility(true)
            }
        ) {
            Text("Sign Up")
        }
    }

}