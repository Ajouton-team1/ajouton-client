package com.ajouton.tortee.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
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

@Composable
fun SignUpScreen(
    viewModel: TorTeeViewModel
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            Image(
                painter = painterResource(id = R.drawable.kakaotalk_20230319_045252567),
                contentDescription = null,
                modifier = Modifier
                    .size(200.dp)
            )
        }
        OutlinedTextField(
            placeholder = {
                Text(
                    text = stringResource(id = R.string.signup_email),
                    color = Color.LightGray,
                    style = MaterialTheme.typography.body1
                )
            },
            value = viewModel.signUpEmail.collectAsState().value,
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
                    text = stringResource(id = R.string.signup_password),
                    color = Color.LightGray,
                    style = MaterialTheme.typography.body1
                )
            },
            value = viewModel.signUpPassword.collectAsState().value,
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
                    text = stringResource(id = R.string.signup_name),
                    color = Color.LightGray,
                    style = MaterialTheme.typography.body1
                )
            },
            value = viewModel.signUpName.collectAsState().value,
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
                    text = stringResource(id = R.string.signup_nickname),
                    color = Color.LightGray,
                    style = MaterialTheme.typography.body1
                )
            },
            value = viewModel.signUpNickname.collectAsState().value,
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
                    text = stringResource(id = R.string.signup_description),
                    color = Color.LightGray,
                    style = MaterialTheme.typography.body1
                )
            },
            value = viewModel.signUpDescription.collectAsState().value,
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
        Button(
            onClick = {
                viewModel.signUp()
            }
        ) {
            Text(text = stringResource(id = R.string.sign_up))
        }
        Spacer(modifier = Modifier.padding(12.dp))
        Button(
                onClick = {
                    viewModel.hideSignUpPage()
                }
                ) {
            Text(text = stringResource(id = R.string.go_back))
        }
    }
}