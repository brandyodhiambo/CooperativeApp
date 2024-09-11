package com.brandy.cooperativeapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.brandy.cooperativeapp.navigation.SetUpNavGraph
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var navController: NavHostController
    private val viewModel:MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Disable to see the safe area on the ui
        //enableEdgeToEdge()
        setContent {
            val loginState by viewModel.loginState.collectAsState()
            navController = rememberNavController()
            when(val isLoggedIn = loginState){
                LoginState.Loading -> Unit
                is LoginState.Success -> {
                    SetUpNavGraph(
                        navController = navController,
                        isLoggedIn = isLoggedIn.result
                    )
                }
            }
        }
    }
}