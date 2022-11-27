@file:OptIn(ExperimentalFoundationApi::class)

package com.imtuc.talknity.view

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.insets.navigationBarsWithImePadding
import com.google.accompanist.insets.statusBarsPadding
import com.imtuc.talknity.R
import com.imtuc.talknity.components.CommunityCategoryCard
import com.imtuc.talknity.components.IndividualCommunity
import com.imtuc.talknity.view.ui.theme.Orange500
import com.imtuc.talknity.view.ui.theme.SoftBlack
import com.imtuc.talknity.view.ui.theme.TalknityTheme

class HomeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TalknityTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    HomeScreen()
                }
            }
        }
    }

}

@Composable
fun HomeScreen() {
    val context = LocalContext.current
    ProvideWindowInsets(windowInsetsAnimationsEnabled = true) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .navigationBarsWithImePadding(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Row(
                modifier = Modifier
                    .padding(20.dp,25.dp,0.dp,0.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Start
            ) {
                Text(
                    text = "Whats",
                    fontFamily = FontFamily(Font(R.font.robotoslab_bold)),
                    fontSize = 30.sp,
                    color = SoftBlack
                )
                Text(
                    text = " New?",
                    fontFamily = FontFamily(Font(R.font.robotoslab_bold)),
                    fontSize = 30.sp,
                    color = Orange500
                )
            }
            Row(
                modifier = Modifier
                    .padding(20.dp, 13.dp, 0.dp, 8.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Start
            ) {
                Text(
                    text = "Top",
                    fontFamily = FontFamily(Font(R.font.opensans_bold)),
                    fontSize = 25.sp,
                    color = SoftBlack
                )
                Text(
                    text = " Posts",
                    fontFamily = FontFamily(Font(R.font.opensans_bold)),
                    fontSize = 25.sp,
                    color = Orange500
                )
            }
            LazyRow(
                modifier = Modifier
                    .padding(20.dp, 13.dp, 0.dp, 13.dp),
                content = {
                    items(10) {
                        CommunityCategoryCard()
                    }
                }
            )
            Row(
                modifier = Modifier
                    .padding(20.dp, 13.dp, 0.dp, 8.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Start
            ) {
                Text(
                    text = "Top",
                    fontFamily = FontFamily(Font(R.font.opensans_bold)),
                    fontSize = 25.sp,
                    color = SoftBlack
                )
                Text(
                    text = " Community",
                    fontFamily = FontFamily(Font(R.font.opensans_bold)),
                    fontSize = 25.sp,
                    color = Orange500
                )
            }
            LazyColumn(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                content = {
                    items(10) {
                        IndividualCommunity()
                    }
                }
            )
        }

    }
}

@Preview(showBackground = true)
@Composable
fun HomePreview() {
    TalknityTheme {
        HomeScreen()
    }
}