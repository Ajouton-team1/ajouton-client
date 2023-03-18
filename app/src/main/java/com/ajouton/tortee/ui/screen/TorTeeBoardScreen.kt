package com.ajouton.tortee.ui.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material3.Card
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.ajouton.tortee.R
import com.ajouton.tortee.TorTeeViewModel
import com.ajouton.tortee.model.Bulletin
import com.ajouton.tortee.model.User
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun TorTeeBoardScreen(
    modifier: Modifier,
    viewModel: TorTeeViewModel,
) {
    var isSearching by remember { mutableStateOf(false) }
    var isMakeDialogVisible by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
    ) {
        if (isMakeDialogVisible) {
            DocBulletinMakeDialog(
                onDismissRequest = { isMakeDialogVisible = false },
                onSubmitRequest = { /*TODO*/ },
                user = User() // 사용자로 변경 필요
            )
        }
        BulletinBoardTopBar(
            modifier = Modifier
                .weight(1f),
            isSearching = isSearching,
            onSearchButtonClick = { isSearching = true },
            onCloseButtonClick = { isSearching = false },
            onValueChange = { /*TODO Search title*/ }
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
        BulletinBoardContent(
            modifier = Modifier
                .weight(7f),
            viewModel = viewModel
        )
    }
}

@Composable
fun BulletinBoardTopBar(
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
                    .background(color = Color(0xFFF0E9D2))
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
                    text = stringResource(id = R.string.document_board),
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
fun BulletinBoardContent(
    modifier: Modifier,
    viewModel: TorTeeViewModel,
) {
    Box(
        modifier = modifier,
    ) {
        LazyColumn(
            modifier = modifier
        ) {
            itemsIndexed(viewModel.getBulletinList()) { _, bulletin ->
                BulletinBoardListItem(
                    bulletin,
                    viewModel::setBulletinContentVisibility
                )
            }
        }

        Box(
            modifier = modifier,
            contentAlignment = Alignment.Center
        ) {
            if (viewModel.isBulletinContentShowing.collectAsState().value) {
                BulletinContentDialog(
                    modifier = modifier,
                    onDismissRequest = viewModel::setBulletinContentVisibility,
                    bulletin = viewModel.selectedBulletin.collectAsState().value
                )
            }
        }
    }
}

@Composable
fun BulletinBoardListItem(
    bulletin: Bulletin,
    onTouchBulletin: (Boolean, Bulletin?) -> Unit,
) {

    androidx.compose.material.Card(
        elevation = 4.dp,
        modifier = Modifier
            .fillMaxWidth()
            .height(170.dp)
            .padding(8.dp),
        shape = RoundedCornerShape(24.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.SpaceAround,
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .clickable(onClick = { onTouchBulletin(true, bulletin) })
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
                        painter = painterResource(id = R.drawable.user_icon),
                        contentDescription = null,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = bulletin.writer,
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
fun DocBulletinMakeDialog(
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

@Composable
fun BulletinContentDialog(
    modifier: Modifier,
    onDismissRequest: (Boolean, Bulletin?) -> Unit,
    bulletin: Bulletin,
    properties: DialogProperties = DialogProperties(),
) {
    Dialog(
        onDismissRequest = { onDismissRequest(false, null) },
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
                        painter = painterResource(id = R.drawable.user_icon),
                        contentDescription = null,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = bulletin.writer,
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
                Spacer(
                    modifier = Modifier
                        .weight(1f)
                        .padding(10.dp)
                )
            }
        }
    }
}