package com.ajouton.tortee.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material3.Card
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ajouton.tortee.R
import com.ajouton.tortee.TorTeeViewModel
import com.ajouton.tortee.model.User
import com.ajouton.tortee.ui.theme.TorTeeTheme

@Composable
fun TorTeeMyPageScreen(
    modifier: Modifier,
    viewModel: TorTeeViewModel,
) {
    var user by remember { mutableStateOf(User()) }

    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
        ) {
            Text(
                text = stringResource(id = R.string.mypage_title),
                style = MaterialTheme.typography.h3,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(10.dp)
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            ) {
                Image(
                    painter = painterResource(id = user.imageResId),
                    contentDescription = null
                )
                Spacer(modifier = Modifier.padding(10.dp))
                Column() {
                    Text(
                        text = (user.name + stringResource(id = R.string.greeting)),
                        style = MaterialTheme.typography.h5
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                    Text(
                        text = user.email,
                        style = MaterialTheme.typography.body1
                    )
                }
            }
            Card(
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth()
                    .wrapContentHeight()
            ) {
                Column(
                    modifier = Modifier.padding(10.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.mypage_info),
                        style = MaterialTheme.typography.h5
                    )
                    Text(
                        text = user.info,
                        style = MaterialTheme.typography.body1,
                        modifier = Modifier.padding(start = 10.dp)
                    )
                }
            }
            Card(
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth()
                    .wrapContentHeight()
            ) {
                Column(
                    modifier = Modifier.padding(10.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.mypage_technic),
                        style = MaterialTheme.typography.h5
                    )
                    LazyColumn {
                        items(user.technics) { technic ->
                            Text(
                                text = technic,
                                style = MaterialTheme.typography.body1
                            )
                        }
                    }
                }
            }
        }
        Row(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth()
        ) {
            Button(
                onClick = { /*TODO*/ },
                modifier = Modifier
                    .padding(10.dp)
                    .weight(1f)
            ) {
                Text(
                    text = stringResource(id = R.string.button_my_mentor),
                    style = MaterialTheme.typography.button
                )
            }
            Button(
                onClick = { /*TODO*/ },
                modifier = Modifier
                    .padding(10.dp)
                    .weight(1f)
            ) {
                Text(
                    text = stringResource(id = R.string.button_my_mentee),
                    style = MaterialTheme.typography.button
                )
            }
            Button(
                onClick = { /*TODO*/ },
                modifier = Modifier
                    .padding(10.dp)
                    .weight(1f)
            ) {
                Text(
                    text = stringResource(id = R.string.button_my_document),
                    style = MaterialTheme.typography.button
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreviewMyPage() {
    TorTeeTheme {
        TorTeeMyPageScreen(modifier = Modifier, viewModel = viewModel())
    }
}