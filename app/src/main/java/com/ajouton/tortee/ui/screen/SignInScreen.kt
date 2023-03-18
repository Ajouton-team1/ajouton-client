package com.ajouton.tortee.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.ajouton.tortee.R
import com.ajouton.tortee.TorTeeViewModel
import com.ajouton.tortee.network.UserSignInRequest

@Composable
fun SignInScreen(
    viewModel: TorTeeViewModel,
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.kakaotalk_20230319_045252567),
            contentDescription = null
        )
        OutlinedTextField(
            placeholder = {
                Text(
                    text = stringResource(id = R.string.placeholder_id),
                    color = Color.LightGray,
                    style = MaterialTheme.typography.body1
                )
            },
            value = viewModel.userIdInput.collectAsState().value,
            onValueChange = {
                viewModel.updateUserIdInput(it)
            },
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Next
            ),
            colors = TextFieldDefaults.textFieldColors(
                textColor = Color.Black,
                cursorColor = Color.Black,
                backgroundColor = Color.White,
                focusedIndicatorColor = Color.Black,
                unfocusedIndicatorColor = Color.Black,
                disabledIndicatorColor = Color.Unspecified
            )
        )
        Spacer(modifier = Modifier.padding(12.dp))
        OutlinedTextField(
            placeholder = {
                Text(
                    text = stringResource(id = R.string.placeholder_password),
                    color = Color.LightGray,
                    style = MaterialTheme.typography.body1
                )
            },
            value = viewModel.userPasswordInput.collectAsState().value,
            onValueChange = {
                viewModel.updateUserPasswordInput(it)
            },
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done
            ),
            colors = TextFieldDefaults.textFieldColors(
                textColor = Color.Black,
                cursorColor = Color.Black,
                backgroundColor = Color.White,
                focusedIndicatorColor = Color.Black,
                unfocusedIndicatorColor = Color.Black,
                disabledIndicatorColor = Color.Unspecified
            )
        )
        Spacer(modifier = Modifier.padding(12.dp))
        Button(
            onClick = {
                viewModel.signIn()
            }
        ) {
            Text(text = stringResource(id = R.string.sign_in))
        }
        Spacer(modifier = Modifier.padding(12.dp))
        Button(
            onClick = {
                viewModel.setSignUpPageVisibility(true)
            }
        ) {
            Text(text = stringResource(id = R.string.sign_up))
        }
    }

}