package com.example.myriadmirror.util

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument

sealed class Screen(
    val route: String,
    val navArguments: List<NamedNavArgument> = emptyList()
) {
    data object Home : Screen("home")

    data object ChatDetail: Screen(
        route = "chatDetail/{$roleId}",
        navArguments = listOf(navArgument(roleId) {
            type = NavType.IntType
        })
    ) {
        fun createRoute(roleId: Int) = "chatDetail/$roleId"
    }

    data object RoleDetail: Screen(
        route = "roleDetail/{$roleId}",
        navArguments = listOf(navArgument(roleId) {
            type = NavType.IntType
        })
    ) {
        fun createRoute(roleId: Int) = "roleDetail/$roleId"
    }

    companion object {
        const val roleId: String = "roleId"
    }
}
