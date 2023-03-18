package com.ajouton.tortee.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Text
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.ajouton.tortee.TorTeeViewModel
import com.ajouton.tortee.model.Bulletin

@Composable
fun TorTeeBoardScreen(
    modifier: Modifier,
    viewModel: TorTeeViewModel
) {
    Column(
        modifier = modifier
    ) {
        BulletinBoardTopBar(
            modifier = Modifier
                .weight(1f)
        )
        BulletinBoardContent(
            modifier = Modifier
                .weight(7f),
            viewModel = viewModel
        )
    }
}

@Composable
fun BulletinBoardTopBar(
    modifier: Modifier
) {
    Box(
        modifier = modifier
    ) {
        Text("TopBar")
    }
}

@Composable
fun BulletinBoardContent(
    modifier: Modifier,
    viewModel: TorTeeViewModel
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
            if(viewModel.isBulletinContentShowing.collectAsState().value) {
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
    onTouchBulletin: (Boolean, Bulletin?) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 12.dp, top = 6.dp, end = 12.dp, bottom = 6.dp)
            .clickable {
                onTouchBulletin(true, bulletin)
            }
    ) {
        Text(
            modifier = Modifier
                .padding(12.dp),
            text = bulletin.title
        )
    }
}

@Composable
fun BulletinContentDialog(
    modifier: Modifier,
    onDismissRequest: (Boolean, Bulletin?) -> Unit,
    bulletin: Bulletin,
    properties: DialogProperties = DialogProperties()
) {
    Dialog(
        onDismissRequest = { onDismissRequest(false, null) }
    ) {
        Column(
            modifier = modifier
                .fillMaxHeight(0.9f)
                .fillMaxWidth()
                .background(Color(0.8f, 0.8f, 0.8f))
        ) {
            Text(bulletin.writer)
            Text(bulletin.title)
            Text(bulletin.content)
        }
    }
}