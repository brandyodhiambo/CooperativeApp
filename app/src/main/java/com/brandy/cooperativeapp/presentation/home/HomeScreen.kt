package com.brandy.cooperativeapp.presentation.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.brandy.cooperativeapp.R
import com.brandy.cooperativeapp.ui.theme.CooperativeAppTheme
import com.brandy.cooperativeapp.ui.theme.GreenColor
import com.brandy.cooperativeapp.ui.theme.WhiteColor


@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val username by viewModel.userName.collectAsState()

    HomeScreenContent(
        username = username,
        onClickLogOut = {
            viewModel.logOut()
        }
    )

}

@Composable
fun HomeScreenContent(
    username: String,
    onClickLogOut: () -> Unit
) {
    Box {
        Image(
            painter = painterResource(id = R.drawable.bgimage),
            contentDescription = "",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )

        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 20.dp),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = onClickLogOut,
                    modifier = Modifier.padding(top = 10.dp, start = 3.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_logout),
                        contentDescription = "Log Out",
                        tint = WhiteColor
                    )
                }

                Text(
                    text = "Logout",
                    modifier = Modifier.padding(top = 10.dp, start = 14.dp),
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold
                    ),
                    color = WhiteColor
                )
            }

            Box(
                modifier = Modifier
                    .fillMaxHeight(0.25f)
                    .fillMaxWidth()
                    .padding(),
                contentAlignment = Alignment.Center
            ) {

                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "",
                    modifier = Modifier
                        .size(100.dp)
                )

            }

            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                Text(
                    text = buildAnnotatedString {
                        append("Welcome ")
                        withStyle(
                            style = SpanStyle(
                                color = GreenColor,
                                fontWeight = FontWeight.Bold
                            )
                        ) {
                            append(username)
                        }
                        append(" to the new Co-op Bank App!")
                    },
                    modifier = Modifier
                        .wrapContentSize()
                        .padding(top = 14.dp),
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Normal,
                        color = WhiteColor
                    ),
                    textAlign = TextAlign.Center
                )
            }

        }
    }
}

@Preview
@Composable
fun HomeScreenPreview(modifier: Modifier = Modifier) {
    CooperativeAppTheme {
        HomeScreenContent(username = "Brandy", onClickLogOut = {})
    }
}