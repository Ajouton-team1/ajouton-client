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

@Composable
fun SignUpScreen(
    viewModel: TorTeeViewModel
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = viewModel.signUpEmail.collectAsState().value,
            onValueChange = {
                viewModel.updateUserIdInput(it)
            },
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Next
            ),
        )
        Spacer(modifier = Modifier.padding(12.dp))
        TextField(
            value = viewModel.signUpPassword.collectAsState().value,
            onValueChange = {
                viewModel.updateUserIdInput(it)
            },
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Next
            ),
        )
        Spacer(modifier = Modifier.padding(12.dp))
        TextField(
            value = viewModel.signUpName.collectAsState().value,
            onValueChange = {
                viewModel.updateUserIdInput(it)
            },
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Next
            ),
        )
        Spacer(modifier = Modifier.padding(12.dp))
        TextField(
            value = viewModel.signUpNickname.collectAsState().value,
            onValueChange = {
                viewModel.updateUserIdInput(it)
            },
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Next
            ),
        )
        Spacer(modifier = Modifier.padding(12.dp))
        TextField(
            value = viewModel.signUpDescription.collectAsState().value,
            onValueChange = {
                viewModel.updateUserIdInput(it)
            },
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Next
            ),
        )
        Spacer(modifier = Modifier.padding(12.dp))
        Button(
            onClick = {
                viewModel.signUp()
            }
        ) {
            Text("Sign Up")
        }
        Spacer(modifier = Modifier.padding(12.dp))
        Button(
                onClick = {
                    viewModel.hideSignUpPage()
                }
                ) {
            Text("Go Back")
        }
    }
}