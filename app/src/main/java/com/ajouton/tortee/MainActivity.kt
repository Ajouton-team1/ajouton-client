package com.ajouton.tortee

import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.ajouton.tortee.Menti.MentiFragment
import com.ajouton.tortee.databinding.ActivityMainBinding
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ajouton.tortee.data.ViewType
import com.ajouton.tortee.ui.TorTeeBoardScreen
import com.ajouton.tortee.ui.TorTeeMenteeListScreen
import com.ajouton.tortee.ui.TorTeeMentorListScreen
import com.ajouton.tortee.ui.TorTeeMyPageScreen
import com.ajouton.tortee.ui.state.TorteeUIState
import com.ajouton.tortee.ui.theme.TorTeeTheme
class MainActivity : AppCompatActivity(){


    override fun onCreate(savedInstanceState: Bundle?) {

        setContent {
            TorTeeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val viewModel: TorTeeViewModel = viewModel()
                    TorTeeScreen(
                        viewModel = viewModel
                    )
                }
            }
        }
    }
}

@Composable
fun TorTeeScreen(
    viewModel: TorTeeViewModel
) {
    val navigationItemContentList = listOf(
        NavigationItemContent(
            viewType = ViewType.MentorList,
            icon = R.drawable.user_icon,
            text = "MentorList"
        ),
        NavigationItemContent(
            viewType = ViewType.MenteeList,
            icon = R.drawable.user_icon,
            text = "MenteeList"
        ),
        NavigationItemContent(
            viewType = ViewType.Board,
            icon = R.drawable.user_icon,
            text = "Board"
        ),
        NavigationItemContent(
            viewType = ViewType.MyPage,
            icon = R.drawable.user_icon,
            text = "MyPage"
        )
    )

    val torteeUIState = viewModel.uiState.collectAsState().value
    TorTeeAppContent(
        torteeUIstate = torteeUIState,
        navigationItemContentList = navigationItemContentList,
        viewModel = viewModel
    )
}

@Composable
private fun TorTeeAppContent(
    torteeUIstate: TorteeUIState,
    navigationItemContentList: List<NavigationItemContent>,
    viewModel: TorTeeViewModel
) {
    Column() {
        TorTeeOnlyContentView(
            modifier = Modifier.weight(13f),
            currentTab = torteeUIstate.currentViewType,
            viewModel = viewModel
        )
        TorTeeBottomNavigationBar(
            navigationItemContentList = navigationItemContentList,
            modifier = Modifier.weight(1f),
            onTabPressed = { viewType: ViewType ->
                viewModel.updateCurrentView(viewType = viewType)
            },
            currentTab = torteeUIstate.currentViewType
        )
    }
}

@Composable
private fun TorTeeOnlyContentView(
    modifier: Modifier,
    currentTab: ViewType,
    viewModel: TorTeeViewModel
) {
    when(currentTab) {
        ViewType.MentorList -> {
            TorTeeMentorListScreen(
                modifier = modifier,
                viewModel = viewModel
            )
        }
        ViewType.MenteeList -> {
            TorTeeMenteeListScreen(
                modifier = modifier,
                viewModel = viewModel
            )
        }
        ViewType.Board -> {
            TorTeeBoardScreen(
                modifier = modifier,
                viewModel = viewModel
            )
        }
        else -> {
            TorTeeMyPageScreen(
                modifier = modifier,
                viewModel = viewModel
            )
        }
    }
}

@Composable
private fun TorTeeBottomNavigationBar(
    navigationItemContentList: List<NavigationItemContent>,
    modifier: Modifier,
    onTabPressed: (ViewType) -> Unit,
    currentTab: ViewType
) {
    NavigationBar(
        modifier = modifier
    ) {
        navigationItemContentList.forEachIndexed { _, navItem ->
            NavigationBarItem(
                selected = currentTab == navItem.viewType,
                onClick = {
                    onTabPressed(navItem.viewType)
                },
                icon = {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = navItem.icon),
                        contentDescription = navItem.text,
                        modifier = Modifier
                            .fillMaxSize(0.6f)
                    )
                }
            )
        }
    }
}

private data class NavigationItemContent(
    val viewType: ViewType,
    @DrawableRes
    val icon: Int,
    val text: String
)