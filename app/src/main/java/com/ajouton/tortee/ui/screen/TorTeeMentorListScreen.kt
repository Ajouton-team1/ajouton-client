package com.ajouton.tortee.ui.screen

import android.graphics.ColorSpace.Rgb
import android.graphics.Paint.Align
import android.service.autofill.OnClickAction
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ajouton.tortee.model.User
import com.ajouton.tortee.ui.theme.TorTeeTheme
import com.ajouton.tortee.R
import com.ajouton.tortee.TorTeeViewModel
import com.ajouton.tortee.ui.theme.Typography

@Composable
fun TorTeeMentorListScreen(
    modifier: Modifier,
    viewModel: TorTeeViewModel,
) {
    var isDialogVisible by remember { mutableStateOf(false) }
    var isSearching by remember { mutableStateOf(false) }
    var targetUser by remember { mutableStateOf(User()) }


    Column(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        if (isDialogVisible) {
            MentorRequestDialog(
                onDismissRequest = { isDialogVisible = false },
                onSubmitRequest = { targetUser ->
                    viewModel.makeMatching(false, targetUser.id)
                },
                user = targetUser
            )
        }
        NavigationBarAbove(
            modifier = Modifier
                .weight(1f),
            isSearching = isSearching,
            onSearchButtonClick = { isSearching = true },
            onCloseButtonClick = { isSearching = false },
            onValueChange = { tag ->
                viewModel.searchMentor(tag)
            }
        )
        MentorFinderBoard(
            onClickCard = { user ->
                isDialogVisible = true
                targetUser = user
            },
            modifier = Modifier
                .weight(7f),
            userList = viewModel.mentorList.collectAsState().value ?: listOf()
        )
    }
}

@Composable
fun MentorFinderBoard(
    onClickCard: (User) -> Unit,
    modifier: Modifier,
    userList: List<User> = listOf()
) {
    /*
    // test 용도
    val userList: List<User> = listOf<User>(
        User(
            name = "testName1", technics = listOf<String>("test1", "test2", "test3")
        ),
        User(
            name = "testName1", technics = listOf<String>("test1", "test2")
        ),
        User(
            name = "testName1", technics = listOf<String>("test1")
        ),
        User(
            name = "testName1", technics = listOf<String>("test1")
        ),
        User(
            name = "testName1", technics = listOf<String>("test1")
        )
    )
    */

    Column(
        modifier = modifier.fillMaxSize()
    ) {
        Text(
            text = stringResource(id = R.string.mentorlist),
            style = MaterialTheme.typography.h4,
            modifier = Modifier.padding(10.dp, 20.dp)
        )
        MentorFinderGrid(users = userList, onClickCard = onClickCard)
    }
}

@Composable
fun MentorFinderGrid(
    users: List<User>,
    onClickCard: (User) -> Unit,
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 128.dp),
        modifier = Modifier.padding(top = 20.dp, start = 10.dp, end = 10.dp)
    ) {
        items(users) { user ->
            MentorFinderCard(
                user = user,
                onClickCard = { onClickCard(user) },
                modifier = Modifier
            )
        }
    }
}

@Composable
fun MentorFinderCard(
    user: User,
    onClickCard: () -> Unit,
    modifier: Modifier,
) {
    Card(
        elevation = 4.dp,
        modifier = modifier
            .width(200.dp)
            .height(230.dp)
            .padding(8.dp),
        shape = RoundedCornerShape(24.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .clickable(onClick = onClickCard)
        ) {
            Image(
                painter = painterResource(id = R.drawable.user_icon),
                contentDescription = null,
                modifier = Modifier
                    .size(120.dp)
                    .padding(10.dp)
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = user.name,
                    style = MaterialTheme.typography.h6
                )
            }
            TechnicListColumn(technics = user.technics)
        }
    }
}

@Composable
fun TechnicListColumn(
    technics: List<String>,
) {
    LazyColumn {
        items(technics.take(2)) { technic ->
            Text(
                text = technic,
                style = MaterialTheme.typography.body2
            )
        }
    }
}

@Composable
fun NavigationBarAbove(
    modifier: Modifier = Modifier,
    isSearching: Boolean,
    onSearchButtonClick: () -> Unit,
    onCloseButtonClick: () -> Unit,
    onValueChange: (String) -> Unit,
) {
    var text by remember { mutableStateOf(TextFieldValue()) }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = modifier
            .fillMaxWidth()
    ) {
        AnimatedVisibility(
            visible = isSearching,
            enter = slideInHorizontally(initialOffsetX = { it }),
            exit = slideOutHorizontally(targetOffsetX = { it }),
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .padding(10.dp)
                    .clip(shape = RoundedCornerShape(12.dp))
                    .background(Color(R.color.search_bar))
                    .padding(10.dp, 0.dp)
            ) {
                TextField(
                    value = text,
                    singleLine = true,
                    shape = RectangleShape,
                    colors = TextFieldDefaults.textFieldColors(
                        textColor = Color.Black,
                        cursorColor = Color.Black,
                        backgroundColor = Color.Unspecified,
                        focusedIndicatorColor = Color.Unspecified,
                        unfocusedIndicatorColor = Color.Unspecified,
                        disabledIndicatorColor = Color.Unspecified
                    ),
                    modifier = Modifier.weight(9f),
                    onValueChange = {
                        onValueChange(it.text)
                        text = it
                    }
                )
                IconButton(
                    onClick = {
                        onCloseButtonClick()
                        text = TextFieldValue("")
                    },
                    modifier = Modifier
                        .weight(1f)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_close_24),
                        contentDescription = null,
                        modifier = Modifier
                            .size(36.dp)
                    )
                }
            }
        }
        AnimatedVisibility(visible = !isSearching) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = modifier
                    .padding(10.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = stringResource(id = R.string.mentor_board),
                    style = MaterialTheme.typography.h3,
                    modifier = Modifier.padding(6.dp, 0.dp)
                )
                IconButton(
                    onClick = onSearchButtonClick,
                    modifier = Modifier
                        .size(36.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_search_24),
                        contentDescription = stringResource(id = R.string.search_description),
                        modifier = Modifier
                            .size(36.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun MentorRequestDialog(
    onDismissRequest: () -> Unit,
    onSubmitRequest: (User) -> Unit,
    properties: DialogProperties = DialogProperties(),
    user: User,
) {
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = properties
    ) {
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .clip(RoundedCornerShape(12.dp))
                .background(color = Color.White)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.user_icon),
                    contentDescription = null
                )
                Column(
                    verticalArrangement = Arrangement.SpaceAround,
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = user.name,
                        style = MaterialTheme.typography.h5
                    )
                    Text(
                        text = user.email,
                        style = MaterialTheme.typography.body1
                    )
                }
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(10.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color.LightGray)
            ) {
                Text(
                    text = user.info,
                    style = MaterialTheme.typography.body1,
                    modifier = Modifier.padding(10.dp)
                )
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(10.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color.LightGray)
            ) {
                LazyColumn {
                    items(user.technics) { technic ->
                        Text(
                            text = technic,
                            style = MaterialTheme.typography.body1,
                            modifier = Modifier.padding(10.dp)
                        )
                    }
                }
            }
            Row(modifier = Modifier.padding(10.dp)) {
                Button(
                    onClick = onDismissRequest,
                    modifier = Modifier
                        .weight(1f)
                        .padding(10.dp)
                        .clip(RoundedCornerShape(12.dp))
                ) {
                    Text(
                        text = stringResource(id = R.string.button_cancel),
                        style = MaterialTheme.typography.button,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .weight(1f)
                            .padding(10.dp)
                    )
                }
                Button(
                    onClick = {onSubmitRequest(user)},
                    modifier = Modifier
                        .weight(1f)
                        .padding(10.dp)
                        .clip(RoundedCornerShape(12.dp))
                ) {
                    Text(
                        text = stringResource(id = R.string.button_submit),
                        style = MaterialTheme.typography.button,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .weight(1f)
                            .padding(10.dp)
                    )
                }

            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    TorTeeTheme {
        TorTeeMentorListScreen(modifier = Modifier, viewModel = viewModel())
    }
}