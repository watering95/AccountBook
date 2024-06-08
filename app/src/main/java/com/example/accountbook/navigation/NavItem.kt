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

    const val SET_SCREEN = "setScreen"
    const val ACCOUNT_SET = "accountSet"
    const val CATEGORY_SET = "categorySet"
    const val CREDIT_CARD_SET = "creditCardSet"
    const val GROUP_SET = "groupSet"
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
    data object SetScreen : AppScreen(Routes.SET_SCREEN, "설정", Icons.Default.Settings) {
        data object AccountSet : AppScreen(Routes.ACCOUNT_SET, "계좌 설정", Icons.Default.AccountBalance)
        data object CategorySet : AppScreen(Routes.CATEGORY_SET, "카테고리 설정", Icons.Default.Category)
        data object CreditCardSet : AppScreen(Routes.CREDIT_CARD_SET, "신용카드 설정", Icons.Default.CreditCard)
        data object GroupSet : AppScreen(Routes.GROUP_SET, "그룹 설정", Icons.Default.Group)
    }


}