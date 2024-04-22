package com.example.accountbook.navigation

import com.example.accountbook.R

sealed class NavItem(var title: String, var icon: Int, var route: String) {
    data object MainScreen : NavItem("Main", R.drawable.ic_launcher_foreground, "Main")
    data object SetAccountScreen : NavItem("SetAccount", R.drawable.ic_launcher_foreground, "SetAccount")
    data object SetCategoryScreen : NavItem("SetCategory", R.drawable.ic_launcher_foreground, "SetCategory")
    data object SetCreditCardScreen : NavItem("SetCreditCard", R.drawable.ic_launcher_foreground, "SetCreditCard")
    data object SetGroupScreen : NavItem("SetGroup", R.drawable.ic_launcher_foreground, "SetGroup")
}

sealed class NavItemBottom(var title: String, var icon: Int, var route: String) {
    data object HomeScreen : NavItem("Home", R.drawable.ic_launcher_foreground, "Home")
    data object BookScreen : NavItem("Book", R.drawable.ic_launcher_foreground, "Book")
}