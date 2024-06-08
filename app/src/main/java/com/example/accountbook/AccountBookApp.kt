package com.example.accountbook

import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.rememberNavController
import com.example.accountbook.navigation.RootNavHost


@Composable
fun AccountBookApp() {
    RootNavHost(rememberNavController(), AppRoomDatabase.getInstance(LocalContext.current, rememberCoroutineScope()))
}

