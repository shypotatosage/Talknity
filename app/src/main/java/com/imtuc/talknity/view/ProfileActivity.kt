package com.imtuc.talknity.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.style.UnderlineSpan
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.insets.navigationBarsWithImePadding
import com.google.accompanist.insets.statusBarsPadding
import com.imtuc.talknity.R
import com.imtuc.talknity.view.ui.theme.*
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.imtuc.talknity.helper.Const
import com.imtuc.talknity.model.CommunityCategory
import com.imtuc.talknity.model.User
import com.imtuc.talknity.navigation.BottomNavScreen
import com.imtuc.talknity.navigation.CustomBottomNavigation
import com.imtuc.talknity.navigation.Screen
import com.imtuc.talknity.viewmodel.AuthViewModel

class ProfileActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TalknityTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
//                    Profile()
                }
            }
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Profile(authViewModel: AuthViewModel, navController: NavHostController, lifecycleOwner: LifecycleOwner) {
    val context = LocalContext.current

    var user_displayname = remember {
        mutableStateOf("")
    }

    var user_username = remember {
        mutableStateOf("")
    }

    var user_email = remember {
        mutableStateOf("")
    }

    var user_image = remember {
        mutableStateOf("")
    }

    var profileLoading = remember {
        mutableStateOf(true)
    }

    val preferences = context.getSharedPreferences("user", Context.MODE_PRIVATE)

    authViewModel.getProfile(preferences.getInt("user_id", -1).toString())

    authViewModel.profile.observe(lifecycleOwner, Observer { response ->
        if (response.user_id.toInt() > -1) {
            user_displayname.value = response.user_displayname
            user_username.value = response.user_username
            user_email.value = response.user_email
            user_image.value = response.user_image

            profileLoading.value = false
        } else {
            profileLoading.value = true
            Toast.makeText(context, "Failed To Get Your Profile", Toast.LENGTH_SHORT)
                .show()
        }
    })

    val currentScreen = remember {
        mutableStateOf<BottomNavScreen>(BottomNavScreen.Profile)
    }

    ProvideWindowInsets(windowInsetsAnimationsEnabled = true) {
        androidx.compose.material3.Scaffold(
            bottomBar = {
                CustomBottomNavigation(currentScreenRoute = currentScreen.value.route) {
                    navController.popBackStack()
                    navController.navigate(it.route)
                }
            }
        ) {
            if (profileLoading.value) {
                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(300.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        CircularProgressIndicator(
                            color = SoftBlack
                        )
                    }
                    Spacer(modifier = Modifier.height(300.dp))
                }
            } else {
                Column(
                    modifier = Modifier
                        .paint(
                            painter = painterResource(id = R.drawable.bg1),
                            contentScale = ContentScale.Crop
                        )
                        .fillMaxSize()
                        .statusBarsPadding()
                        .navigationBarsWithImePadding()
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally
                )
                {
                    Column(
                        modifier = Modifier
                            .padding(0.dp, 0.dp, 0.dp, 0.dp)
                            .fillMaxWidth()
                            .height(250.dp)
                    ) {
                        Image(
                            painter =
                            if (user_image.value.isNotEmpty()) {
                                rememberAsyncImagePainter(Const.BASE_URL + "/" + user_image.value)
                            } else {
                                painterResource(id = R.drawable.defaultprofilepicture)
                            },
                            contentDescription = "Profile Picture",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .fillMaxSize()
                        )
                    }

                    Surface(
                        shape = RoundedCornerShape(50.dp),
                        shadowElevation = 8.dp,
                        modifier = Modifier
                            .wrapContentHeight()
                            .offset(0.dp, -30.dp)
                            .fillMaxWidth(0.75f)
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center,
                        ) {
                            Text(
                                text = user_displayname.value,
                                textAlign = TextAlign.Center,
                                fontSize = 25.sp,
                                fontFamily = FontFamily(Font(R.font.opensans_bold)),
                                modifier = Modifier
                                    .padding(28.dp, 16.dp),
                                lineHeight = 30.sp
                            )
                        }
                    }
                    Column(
                        modifier = Modifier
                            .padding(24.dp, 10.dp, 0.dp, 0.dp)
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.Start
                    ) {
                        Row() {
                            Text(
                                text = "Your ",
                                fontFamily = FontFamily(Font(R.font.robotoslab_semibold)),
                                fontSize = 26.sp,
                                color = SoftBlack
                            )
                            Text(
                                text = "Information",
                                fontFamily = FontFamily(Font(R.font.robotoslab_semibold)),
                                fontSize = 26.sp,
                                color = Orange500
                            )
                        }
                    }
                    Column(
                        modifier = Modifier
                            .padding(24.dp, 0.dp)
                            .fillMaxWidth(),
                    ) {
                        Row(
                            modifier = Modifier
                                .padding(0.dp, 20.dp, 0.dp, 0.dp)
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "Username ",
                                fontFamily = FontFamily(Font(R.font.opensans_semibold)),
                                fontSize = 16.sp,
                                color = SoftBlack,
                                modifier = Modifier
                                    .fillMaxWidth(0.4f)
                            )
                            Text(
                                text = user_username.value,
                                fontFamily = FontFamily(Font(R.font.opensans_semibold)),
                                fontSize = 16.sp,
                                color = Gray300
                            )
                        }
                        Row(
                            modifier = Modifier
                                .padding(0.dp, 16.dp, 0.dp, 0.dp)
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Email ",
                                fontFamily = FontFamily(Font(R.font.opensans_semibold)),
                                fontSize = 16.sp,
                                color = SoftBlack,
                                modifier = Modifier
                                    .fillMaxWidth(0.4f)
                            )
                            Text(
                                text = user_email.value,
                                fontFamily = FontFamily(Font(R.font.opensans_semibold)),
                                fontSize = 16.sp,
                                color = Gray300,
                                textAlign = TextAlign.End
                            )
                        }
                        Row(
                            modifier = Modifier
                                .padding(0.dp, 16.dp, 0.dp, 0.dp)
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "Password",
                                fontFamily = FontFamily(Font(R.font.opensans_semibold)),
                                fontSize = 16.sp,
                                color = SoftBlack,
                                modifier = Modifier
                                    .fillMaxWidth(0.4f)
                            )
                            Text(
                                text = "••••••••••",
                                fontFamily = FontFamily(Font(R.font.opensans_semibold)),
                                fontSize = 16.sp,
                                color = Gray300
                            )
                        }
                    }
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(0.dp, 36.dp, 0.dp, 0.dp)
                    ) {
                        Button(
                            onClick = {
                                navController.navigate(
                                    Screen.EditProfile.passParam(
                                        user_displayname.value,
                                        user_username.value,
                                        user_email.value,
                                        user_image.value
                                    )
                                )
                            },
                            modifier = Modifier
                                .padding(8.dp, 0.dp),
                            shape = RoundedCornerShape(50.dp),
                            colors = ButtonDefaults.buttonColors(backgroundColor = Orange500)
                        ) {
                            Text(
                                modifier = Modifier
                                    .padding(8.dp, 0.dp),
                                text = "Edit Information",
                                fontSize = 18.sp,
                                fontFamily = FontFamily(Font(R.font.opensans_bold)),
                                color = GreyishWhite
                            )
                        }
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                    ) {
                        Button(
                            shape = RoundedCornerShape(25.dp),
                            elevation = ButtonDefaults.elevation(8.dp),
                            modifier = Modifier
                                .wrapContentSize(align = Alignment.Center)
                                .padding(0.dp, 32.dp),
                            onClick = {
                                navController.navigate(Screen.OwnedDiscussion.route)
                            },
                            contentPadding = PaddingValues(0.dp),
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = Color.White
                            )
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier
                                    .padding(18.dp, 20.dp),
                                verticalArrangement = Arrangement.Center
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.discussionicon),
                                    contentDescription = "Back",
                                    modifier = Modifier
                                        .padding(0.dp, 0.dp, 0.dp, 10.dp)
                                        .height(40.dp)
                                )
                                Text(
                                    text = "Discussions",
                                    textAlign = TextAlign.Center,
                                    fontSize = 18.sp,
                                    color = Orange500,
                                    fontFamily = FontFamily(Font(R.font.opensans_semibold)),
                                )
                            }
                        }
                        Button(
                            shape = RoundedCornerShape(25.dp),
                            elevation = ButtonDefaults.elevation(8.dp),
                            modifier = Modifier
                                .wrapContentSize(align = Alignment.Center)
                                .padding(0.dp, 32.dp),
                            onClick = {
                                navController.navigate(Screen.OwnedCommunity.route)
                            },
                            contentPadding = PaddingValues(0.dp),
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = Color.White
                            )
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier
                                    .padding(18.dp, 20.dp),
                                verticalArrangement = Arrangement.Center
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.ownedcommunity),
                                    contentDescription = "Back",
                                    modifier = Modifier
                                        .padding(0.dp, 0.dp, 0.dp, 10.dp)
                                        .height(40.dp)
                                )
                                Text(
                                    text = "Community",
                                    textAlign = TextAlign.Center,
                                    fontSize = 18.sp,
                                    color = Orange500,
                                    fontFamily = FontFamily(Font(R.font.opensans_semibold)),
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(72.dp))
                }
            }
        }
    }
}

fun countChar(text: String): String {
    var count = 0
    var result = ""

    for (Char in text) {
        count++
    }

    while (count > 0) {
        result += "*"
        count--
    }

    return result
}
