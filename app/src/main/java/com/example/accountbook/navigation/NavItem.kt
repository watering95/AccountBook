package com.example.accountbook.navigation

import com.example.accountbook.R

sealed class NavItem(var title: String, var icon: Int, var route: String) {
    object HomeScreen : NavItem("Home", R.drawable.ic_launcher_foreground, "Home")
    object BookScreen : NavItem("Book", R.drawable.ic_launcher_foreground, "Book")
    object MainScreen : NavItem("Main", R.drawable.ic_launcher_foreground, "Main")
}