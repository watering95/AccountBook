package com.example.accountbook.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector

private object Routes {
    const val BOTTOM_BAR = "Bottom Bar"
    const val HOME = "Home"
    const val BOOK = "Book"
    const val SETTING = "Setting"

    const val SET_HOME = "setHome"
    const val SET_ACCOUNT = "setAccount"
    const val SET_CATEGORY = "setCategory"
    const val SET_CREDIT_CARD = "setCreditCard"
    const val SET_GROUP = "setGroup"
    const val ACCOUNT = "account/{${ArgParams.ACCOUNT_ID}}"
}

private object ArgParams {
    const val ACCOUNT_ID = "accountId"

    fun toPath(param: String) = "{${param}}"
}

sealed class AppScreen(val route: String, val title: String, val icon: ImageVector) {
    data object BottomBar : AppScreen(Routes.BOTTOM_BAR,  "Bottom Bar", Icons.Default.Home) {
        data object Home : AppScreen(Routes.HOME,  "Home", Icons.Default.Home)
        data object Book : AppScreen(Routes.BOOK, "가계부", Icons.Default.Book)
        data object Setting : AppScreen(Routes.SETTING, "설정", Icons.Default.Settings)
    }
    data object SetScreen : AppScreen(Routes.SET_HOME, "설정", Icons.Default.Settings) {
        data object SetAccount : AppScreen(Routes.SET_ACCOUNT, "계좌 설정", Icons.Default.AccountBalance)
        data object SetCategory : AppScreen(Routes.SET_CATEGORY, "카테고리 설정", Icons.Default.Category)
        data object SetCreditCard : AppScreen(Routes.SET_CREDIT_CARD, "신용카드 설정", Icons.Default.CreditCard)
        data object SetGroup : AppScreen(Routes.SET_GROUP, "그룹 설정", Icons.Default.Group)
    }


}