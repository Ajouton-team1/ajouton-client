package com.ajouton.tortee.ui.screen

import android.graphics.Paint.Align
import android.service.autofill.OnClickAction
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
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ajouton.tortee.model.User
import com.ajouton.tortee.ui.theme.TorTeeTheme
import com.ajouton.tortee.R
import com.ajouton.tortee.TorTeeViewModel

@Composable
fun TorTeeMentorListScreen(
    modifier: Modifier,
    viewModel: TorTeeViewModel
) {
    var isDialogVisible by remember { mutableStateOf(false) }
    var targetUser by remember { mutableStateOf(User())}

    Column(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        if(isDialogVisible) {
            MentorRequestDialog(
                onDismissRequest = { isDialogVisible = false },
                onSubmitRequest = { /* TODO */ },
                user = targetUser
            )
        }
        NavigationBarAbove(
            modifier = Modifier
                .weight(1f)
        )
        MentorFinderBoard(
            onClickCard = { user ->
                isDialogVisible = true
                targetUser = user },
            modifier = Modifier
                .padding(20.dp)
                .weight(7f)
        )
    }
}

@Composable
fun MentorFinderBoard(
    onClickCard: (User) -> Unit,
    modifier: Modifier
) {
    // test 용도
    val userList: List<User> = listOf<User>(
        User(
            name = "testName1", technics =  listOf<String>("test1","test2", "test3")
        ),
        User(
            name = "testName1", technics =  listOf<String>("test1","test2")
        ),
        User(
            name = "testName1", technics =  listOf<String>("test1")
        ),
        User(
            name = "testName1", technics =  listOf<String>("test1")
        ),
        User(
            name = "testName1", technics =  listOf<String>("test1")
        )
    )

    Column(
        modifier = modifier.fillMaxSize()
    ) {
        Text(
            text = stringResource(id = R.string.mentorlist),
            modifier = Modifier.padding(10.dp,20.dp)
        )
        MentorFinderGrid(users = userList, onClickCard = onClickCard)
    }
}

@Composable
fun MentorFinderGrid(
    users: List<User>,
    onClickCard: (User) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 128.dp)
    ) {
        items(users){ user ->
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
    modifier: Modifier
) {
    Card(
        elevation = 4.dp,
        modifier = modifier
            .width(200.dp)
            .height(200.dp)
            .padding(8.dp),
        shape = RoundedCornerShape(24.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .clickable(onClick = onClickCard)
        ) {
            Image(
                painter = painterResource(id = user.imageResId),
                contentDescription = null,
                modifier = Modifier
                    .size(120.dp)
                    .padding(10.dp)
            )
            Row(

            ) {
                Image(
                    painter = painterResource(id = user.rankResId),
                    contentDescription = null
                )
                Text(text = user.name)
            }
            TechnicListColumn(technics = user.technics)
        }
    }
}

@Composable
fun TechnicListColumn(
    technics: List<String>
) {
    LazyColumn {
        items(technics) { technic ->
            Text(text = technic)
        }
    }
}

@Composable
fun NavigationBarAbove(
    modifier: Modifier = Modifier,
    onSearchButtonClick: ()->Unit = { }
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
            .fillMaxWidth()
    ) {
        Text(
            text = stringResource(id = R.string.app_name),
            fontSize = 32.sp,
            modifier = Modifier.padding(6.dp)
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.wrapContentSize()
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_foreground),
                contentDescription = null,
                modifier = Modifier.size(48.dp)
            )
            Image(
                painter = painterResource(id = R.drawable.baseline_search_24),
                contentDescription = stringResource(id = R.string.search_description),
                modifier = Modifier
                    .size(36.dp)
                    .clickable(onClick = onSearchButtonClick)
            )
        }
    }
}

@Composable
fun MentorRequestDialog(
    onDismissRequest: () -> Unit,
    onSubmitRequest: () -> Unit,
    properties: DialogProperties = DialogProperties(),
    user: User
) {
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = properties
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.9f)
        ) {
            Row() {
                Image(
                    painter = painterResource(id = user.imageResId),
                    contentDescription = null
                )
                Column() {
                    Text(
                        text = user.name
                    )
                    Text(text = user.email)
                }
            }
            Box(modifier = Modifier) {
                Text(text = user.info)
            }
            Box(modifier = Modifier) {
                LazyColumn {
                    items(user.technics) { technic ->
                        Text(text = technic)
                    }
                }
            }
            Row() {
                Text(
                    text = stringResource(id = R.string.button_cancle),
                    modifier = Modifier.clickable { onDismissRequest }
                )
                Text(
                    text = stringResource(id = R.string.button_submit),
                    modifier = Modifier.clickable { onSubmitRequest }
                )
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