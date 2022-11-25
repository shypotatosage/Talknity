package com.imtuc.talknity.view

import android.os.Bundle
import android.window.SplashScreen
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
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
import com.imtuc.talknity.view.ui.theme.Orange500
import com.imtuc.talknity.view.ui.theme.SoftBlack
import com.imtuc.talknity.view.ui.theme.TalknityTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TalknityTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting3("Android")
                }
            }
        }
    }
}

@Composable
fun SplashScreen() {

    val context = LocalContext.current

    Column(
        modifier = Modifier
            .paint(
                painter = painterResource(id = R.drawable.bg1),
                contentScale = ContentScale.Crop
            )
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.talknitylogo),
            contentDescription = "Back",
            modifier = Modifier
                .height(150.dp)
        )

        Row(
            modifier = Modifier
                .padding(50.dp, 20.dp, 0.dp, 0.dp)
                .fillMaxWidth(),
        ) {
            Column() {
                Text(
                    text = "Find Your",
                    fontFamily = FontFamily(Font(R.font.robotoslab_bold)),
                    fontSize = 30.sp,
                    color = SoftBlack
                )
                Text(
                    text = "Community",
                    fontFamily = FontFamily(Font(R.font.robotoslab_bold)),
                    fontSize = 30.sp,
                    color = Orange500
                )
            }
            Image(
                painter = painterResource(id = R.drawable.splashback),
                contentDescription = "Back",
                modifier = Modifier
                    .padding(20.dp, 15.dp, 0.dp, 0.dp)
                    .height(50.dp),
            )
        }
    }
    Column(
        modifier = Modifier
        .fillMaxSize(),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally) {
        Image(
            painter = painterResource(id = R.drawable.splash),
            contentDescription = "Back",
            modifier = Modifier
                .padding(50.dp, 50.dp, 0.dp, 0.dp)
                .size(300.dp)
        )
    }
}


@Composable
fun Greeting3() {
    Column(
        modifier = Modifier
            .paint(
                painter = painterResource(id = R.drawable.bg1),
                contentScale = ContentScale.Crop
            )
            .fillMaxSize()
    ) {

    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    TalknityTheme {
        SplashScreen()
    }
}