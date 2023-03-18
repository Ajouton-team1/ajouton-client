package com.ajouton.tortee.ui.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.ajouton.tortee.R
import com.ajouton.tortee.TorTeeViewModel
import com.ajouton.tortee.model.MenteeBulletin
import com.ajouton.tortee.model.User
import com.ajouton.tortee.ui.theme.TorTeeTheme
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun TorTeeMenteeListScreen(
    modifier: Modifier,
    viewModel: TorTeeViewModel,
) {
    var isMakeDialogVisible by remember { mutableStateOf(false) }
    var isDialogVisible by remember { mutableStateOf(false) }
    var isSearching by remember { mutableStateOf(false) }
    var targetBulletin by remember { mutableStateOf(MenteeBulletin()) }
    viewModel.getMenteeList()

    

    Column(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        if (isDialogVisible) {
            MenteeBulletinDialog(
                onDismissRequest = { isDialogVisible = false },
                onSubmitRequest = { /* TODO */ },
                bulletin = targetBulletin
            )
        }
        if (isMakeDialogVisible) {
            MenteeBulletinMakeDialog(
                onDismissRequest = { isMakeDialogVisible = false },
                onSubmitRequest = { /*TODO*/ },
                user = User() // 사용자로 변경 필요
            )
        }
        NavigationBarAbove_Mentee(
            modifier = Modifier
                .weight(1f),
            isSearching = isSearching,
            onSearchButtonClick = { isSearching = true },
            onCloseButtonClick = { isSearching = false },
            onValueChange = { /* TODO */ }
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            androidx.compose.material3.ExtendedFloatingActionButton(
                onClick = { isMakeDialogVisible = true },
                contentColor = Color.White,
                modifier = Modifier
                    .width(160.dp)
                    .padding(10.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_add_24),
                    contentDescription = null
                )
                Spacer(modifier = Modifier.padding(5.dp))
                Text(
                    text = "New",
                    style = MaterialTheme.typography.h5
                )
            }
        }
        MenteeBoard(
            onClickCard = { bulletin ->
                isDialogVisible = true
                targetBulletin = bulletin
            },
            modifier = Modifier
                .weight(7f),

        )
    }
}


@Composable
fun MenteeBoard(
    onClickCard: (MenteeBulletin) -> Unit,
    modifier: Modifier,
    bulletins: List<MenteeBulletin> = listOf<MenteeBulletin>()
) {
    // test 용도
    val bulletins: List<MenteeBulletin> = listOf<MenteeBulletin>(
        MenteeBulletin(writer = User(name = "Test1")),
        MenteeBulletin(writer = User(name = "Test2")),
        MenteeBulletin(writer = User(name = "Test3"))
    )

    Column(
        modifier = modifier.fillMaxSize()
    ) {
        MenteeBoardColumn(bulletins = bulletins, onClickCard = onClickCard)
    }
}

@Composable
fun MenteeBoardColumn(
    bulletins: List<MenteeBulletin>,
    onClickCard: (MenteeBulletin) -> Unit,
) {
    LazyColumn {
        items(bulletins) { bulletin ->
            MenteeBulletinCard(
                bulletin = bulletin,
                onClickCard = onClickCard,
                modifier = Modifier
            )
        }
    }
}

@Composable
fun MenteeBulletinCard(
    bulletin: MenteeBulletin,
    onClickCard: (MenteeBulletin) -> Unit,
    modifier: Modifier,
) {
    Card(
        elevation = 4.dp,
        modifier = modifier
            .fillMaxWidth()
            .height(170.dp)
            .padding(8.dp),
        shape = RoundedCornerShape(24.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.SpaceAround,
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .clickable(onClick = { onClickCard(bulletin) })
                .padding(16.dp)
        ) {
            Text(
                text = bulletin.title,
                style = MaterialTheme.typography.h4,
                textAlign = TextAlign.Center
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = bulletin.writeDate,
                    style = MaterialTheme.typography.body1,
                    color = Color.Gray,
                    textAlign = TextAlign.Center
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier
                ) {
                    Image(
                        painter = painterResource(id = bulletin.writer.imageResId),
                        contentDescription = null,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = bulletin.writer.name,
                        style = MaterialTheme.typography.body2,
                        textAlign = TextAlign.Center
                    )
                }
            }

            Text(
                text = bulletin.content,
                maxLines = 2,
                modifier = Modifier
                    .padding(start = 5.dp)
            )
        }
    }
}


@Composable
fun NavigationBarAbove_Mentee(
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
                        onValueChange(it.toString())
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
                    text = stringResource(id = R.string.mentee_board),
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
fun MenteeBulletinDialog(
    onDismissRequest: () -> Unit,
    onSubmitRequest: () -> Unit,
    properties: DialogProperties = DialogProperties(),
    bulletin: MenteeBulletin,
) {
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = properties
    ) {
        Column(
            verticalArrangement = Arrangement.SpaceAround,
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .clip(RoundedCornerShape(12.dp))
                .background(color = Color.White)
                .padding(10.dp)
        ) {
            Text(
                text = bulletin.title,
                style = MaterialTheme.typography.h4,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 5.dp)
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp, vertical = 10.dp)
            ) {
                Text(
                    text = bulletin.writeDate,
                    style = MaterialTheme.typography.body1,
                    color = Color.Gray,
                    textAlign = TextAlign.Center
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier
                ) {
                    Image(
                        painter = painterResource(id = bulletin.writer.imageResId),
                        contentDescription = null,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = bulletin.writer.name,
                        style = MaterialTheme.typography.body2,
                        textAlign = TextAlign.Center
                    )
                }
            }

            Text(
                text = bulletin.content,
                modifier = Modifier
                    .padding(start = 5.dp)
            )
            Row(
                horizontalArrangement = Arrangement.End,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Spacer(
                    modifier = Modifier
                        .weight(1f)
                        .padding(10.dp)
                )
                Button(
                    onClick = onSubmitRequest,
                    modifier = Modifier
                        .weight(1f)
                        .padding(10.dp)
                        .clip(RoundedCornerShape(12.dp))
                ) {
                    Text(
                        text = stringResource(id = R.string.button_submit),
                        style = MaterialTheme.typography.button
                    )
                }
            }
        }
    }
}

@Composable
fun MenteeBulletinMakeDialog(
    onDismissRequest: () -> Unit,
    onSubmitRequest: () -> Unit,
    user: User,
    properties: DialogProperties = DialogProperties(),
) {
    val time = Calendar.getInstance().time
    val formatter = SimpleDateFormat("yyyy.MM.dd")
    val current = formatter.format(time)

    var title: String by remember { mutableStateOf("") }
    var content: String by remember { mutableStateOf("") }

    Dialog(
        onDismissRequest = onDismissRequest,
        properties = properties
    ) {
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(.7f)
                .clip(RoundedCornerShape(12.dp))
                .background(color = Color.White)
                .padding(10.dp)
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {

                TextField(
                    placeholder = {
                        Text(
                            text = stringResource(id = R.string.placeholder_title),
                            style = MaterialTheme.typography.h4
                        )
                    },
                    value = title,
                    onValueChange = {
                        title = it
                    },
                    shape = RectangleShape,
                    colors = TextFieldDefaults.textFieldColors(
                        textColor = Color.Black,
                        cursorColor = Color.Black,
                        backgroundColor = Color.Unspecified,
                        focusedIndicatorColor = Color.Unspecified,
                        unfocusedIndicatorColor = Color.Unspecified,
                        disabledIndicatorColor = Color.Unspecified
                    ),
                    textStyle = MaterialTheme.typography.h4
                )
                TextField(
                    placeholder = {
                        Text(
                            text = stringResource(id = R.string.placeholder_body),
                            style = MaterialTheme.typography.body1
                        )
                    },
                    value = content,
                    onValueChange = {
                        content = it
                    },
                    shape = RectangleShape,
                    colors = TextFieldDefaults.textFieldColors(
                        textColor = Color.Black,
                        cursorColor = Color.Black,
                        backgroundColor = Color.Unspecified,
                        focusedIndicatorColor = Color.Unspecified,
                        unfocusedIndicatorColor = Color.Unspecified,
                        disabledIndicatorColor = Color.Unspecified
                    ),
                    textStyle = MaterialTheme.typography.body1,
                    modifier = Modifier
                        .padding(start = 5.dp)
                )
            }
            Row(
                horizontalArrangement = Arrangement.End,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Button(
                    onClick = onDismissRequest,
                    modifier = Modifier
                        .weight(1f)
                        .padding(10.dp)
                        .clip(RoundedCornerShape(12.dp))
                ) {
                    Text(
                        text = stringResource(id = R.string.button_cancel),
                        style = MaterialTheme.typography.button
                    )
                }
                Button(
                    onClick = onSubmitRequest,
                    modifier = Modifier
                        .weight(1f)
                        .padding(10.dp)
                        .clip(RoundedCornerShape(12.dp))
                ) {
                    Text(
                        text = stringResource(id = R.string.button_make),
                        style = MaterialTheme.typography.button
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview_mentee() {
    TorTeeTheme {
        MenteeBulletinMakeDialog(
            onDismissRequest = {},
            onSubmitRequest = {},
            user = User()
        )
    }
}