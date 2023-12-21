package com.example.myriadmirror.util


import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.VisibilityThreshold
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleOut
import androidx.compose.animation.shrinkOut
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOut
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.IntOffset
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.myriadmirror.ui.screen.ChatDetailScreen
import com.example.myriadmirror.ui.screen.HomeScreen
import com.example.myriadmirror.ui.screen.RoleDetailScreen

@Composable
fun MyriadMirrorNavHost(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
        enterTransition = {
            slideInHorizontally { it / 2 }
        },
        exitTransition = {
            slideOutHorizontally { -it }
        },
        popEnterTransition = {
            slideInHorizontally { -it }
        },
        popExitTransition = {
            slideOutHorizontally { it }
        }
    ) {
        composable(route = Screen.Home.route) {
            HomeScreen(
                onChatClick = {
                    navController.navigate(
                        Screen.ChatDetail.createRoute(roleId = it)
                    )
                },
                onRoleClick = {
                    navController.navigate(
                        Screen.ChatDetail.createRoute(roleId = it)
                    )
                },
                onAddRoleClick = {
                    navController.navigate(
                        Screen.RoleDetail.createRoute(Constants.CREATE_ROLE_ID)
                    )
                }
            )
        }
        composable(
            route = Screen.ChatDetail.route,
            arguments = Screen.ChatDetail.navArguments
        ) {
            ChatDetailScreen(
                onBackClick = { navController.navigateUp() },
                onOptionClick = {
                    navController.navigate(
                        Screen.RoleDetail.createRoute(it)
                    )
                }
            )
        }
        composable(
            route = Screen.RoleDetail.route,
            arguments = Screen.RoleDetail.navArguments
        ) {
            RoleDetailScreen(
                navigationUp = { navController.navigateUp() },
            )
        }

    }
}
