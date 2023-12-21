package com.example.myriadmirror.ui.screen


import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myriadmirror.R
import com.example.myriadmirror.ui.theme.AppBarTitleColor
import com.example.myriadmirror.ui.theme.FloatButtonContainerColor
import com.example.myriadmirror.ui.theme.FloatButtonContentColor
import com.example.myriadmirror.ui.view.BottomNavigationBar
import com.example.myriadmirror.ui.view.CommonAppBar
import com.example.myriadmirror.util.Constants
import com.example.myriadmirror.viewModel.HomeViewModel
import com.example.myriadmirror.viewModel.ViewModelProvider
import kotlinx.coroutines.launch


@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onChatClick: (id: Int) -> Unit = {},
    onRoleClick: (id: Int) -> Unit = {},
    onAddRoleClick: () -> Unit = {},
    viewModel: HomeViewModel = viewModel(factory = ViewModelProvider.Factory)
) {
    val pagerState = rememberPagerState(initialPage = viewModel.currentIndex) {
        Constants.BOTTOM_NAVIGATION_ITEMS.size
    }
    val roleScreenIndex = 1

    Scaffold(
        bottomBar = {
            val composableScope = rememberCoroutineScope()
            BottomNavigationBar(
                items = Constants.BOTTOM_NAVIGATION_ITEMS,
                value = pagerState.currentPage
            ) {
                composableScope.launch {
                    pagerState.animateScrollToPage(it)
                    viewModel.currentIndex = it
                }
            }
        },
        topBar = {
            CommonAppBar {
                Text(
                    text = stringResource(Constants.BOTTOM_NAVIGATION_ITEMS[pagerState.currentPage].first),
                    color = AppBarTitleColor,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = 24.dp, top = 16.dp, bottom = 20.dp)
                )
            }
        },
        floatingActionButton = {
            if (pagerState.currentPage == roleScreenIndex)
                FloatingActionButton(
                    onClick = onAddRoleClick,
                    shape = CircleShape,
                    containerColor = FloatButtonContainerColor,
                    contentColor = FloatButtonContentColor) {
                    Icon(imageVector = Icons.Default.Add,
                        contentDescription = stringResource(R.string.create_role))
                }
        }
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.padding(it)
        ) { index ->
            when (index) {
                0 -> ChatListScreen(onChatClick = onChatClick)
                1 -> RoleListScreen(onRoleClick = onRoleClick)
                2 -> SettingScreen()
            }
        }
    }
}

@Preview
@Composable
private fun HomeScreenPreview() {
    HomeScreen()
}
