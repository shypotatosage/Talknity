package com.imtuc.talknity.view

import android.Manifest
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.paint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.imtuc.talknity.R
import com.imtuc.talknity.helper.RequestMultiplePermissions
import com.imtuc.talknity.navigation.BottomNavScreen
import com.imtuc.talknity.navigation.Screen
import com.imtuc.talknity.view.ui.theme.Orange500
import com.imtuc.talknity.view.ui.theme.SoftBlack
import com.imtuc.talknity.view.ui.theme.TalknityTheme
import kotlinx.coroutines.delay

class SplashScreenActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TalknityTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    SplashScreen(1f)
                }
            }
        }
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun AnimatedSplashScreen(navController: NavHostController) {
    RequestMultiplePermissions(
        permissions = listOf(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.MANAGE_EXTERNAL_STORAGE,
            Manifest.permission.INTERNET
        )
    )

    var startAnimation by remember {
        mutableStateOf(false)
    }

    val context = LocalContext.current

    val preferences = context.getSharedPreferences("user", Context.MODE_PRIVATE)

    val alphaAnimation = animateFloatAsState(
        targetValue = if (startAnimation) { 1f } else { 0f },
        animationSpec = tween(
            durationMillis = 2500
        )
    )

    LaunchedEffect(key1 = true) {
        startAnimation = true
        delay(4000)
        if (preferences.getInt("user_id", -1) > 0) {
            navController.popBackStack()
            navController.navigate(BottomNavScreen.Home.route)
        } else {
            navController.popBackStack()
            navController.navigate(Screen.Login.route)
        }
    }

    SplashScreen(alphaAnimation.value)
}

@Composable
fun SplashScreen(alpha: Float) {
    Column(
        modifier = Modifier
            .paint(
                painter = painterResource(id = R.drawable.bg1),
                contentScale = ContentScale.Crop
            )
            .fillMaxSize()
            .alpha(alpha)
            .padding(0.dp, 0.dp, 0.dp, 75.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.talknitylogo),
            contentDescription = "Back",
            modifier = Modifier
                .width(300.dp)
        )

        Row(
            modifier = Modifier
                .padding(0.dp, 20.dp, 0.dp, 0.dp)
                .width(300.dp),
            verticalAlignment = Alignment.CenterVertically
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
            .offset(x=70.dp,y=500.dp),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.puzzlepeople),
            contentDescription = "Back",
            modifier = Modifier
                .padding(75.dp, 40.dp, 0.dp, 0.dp)
                .size(300.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SplashPreview() {
    TalknityTheme {
        SplashScreen(1f)
    }
}