package com.imtuc.talknity.view

import android.os.Bundle
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

class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TalknityTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Login()
                }
            }
        }
    }

}

@Composable
fun Login() {
    val context = LocalContext.current

    var loginUserOrEmail = remember {
        mutableStateOf("")
    }

    var password = remember {
        mutableStateOf("")
    }
    ProvideWindowInsets(windowInsetsAnimationsEnabled = true) {
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
        ) {
            Image(
                painter = painterResource(id = R.drawable.talknitylogo),
                contentDescription = "Back",
                modifier = Modifier
                    .padding(0.dp, 150.dp, 0.dp, 0.dp)
                    .size(300.dp)
            )
            Column(
                modifier = Modifier
                    .padding(24.dp, 0.dp)
            ) {
                BasicTextField(
                    value = loginUserOrEmail.value,
                    onValueChange = {
                        loginUserOrEmail.value = it
                    },
                    enabled = true,
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(
                            BorderStroke(
                                width = 0.7.dp,
                                color = Gray300
                            ),
                            shape = RoundedCornerShape(25.dp)
                        )
                        .navigationBarsWithImePadding(),
                    decorationBox = { innerTextField ->
                        TextFieldDefaults.textFieldColors(
                            backgroundColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent,
                            textColor = SoftBlack
                        )
                        Box(
                            modifier = Modifier
                                .padding(16.dp, 12.dp)
                        ) {
                            if (loginUserOrEmail.value.isEmpty()) {
                                Text(
                                    text = "Username / Email",
                                    color = Gray300,
                                    fontSize = 16.sp,
                                    fontFamily = FontFamily(Font(R.font.opensans_regular))
                                )
                            }
                            innerTextField()  //<-- Add this
                        }
                    },
                    textStyle = TextStyle(
                        fontFamily = FontFamily(Font(R.font.opensans_regular)),
                        fontSize = 16.sp
                    )
                )
            }
            Column(
                modifier = Modifier
                    .padding(24.dp, 30.dp)
            ) {
                BasicTextField(
                    value = password.value,
                    onValueChange = {
                        password.value = it
                    },
                    enabled = true,
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(
                            BorderStroke(
                                width = 0.7.dp,
                                color = Gray300
                            ),
                            shape = RoundedCornerShape(25.dp)
                        )
                        .navigationBarsWithImePadding(),
                    decorationBox = { innerTextField ->
                        TextFieldDefaults.textFieldColors(
                            backgroundColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent,
                            textColor = SoftBlack
                        )
                        Box(
                            modifier = Modifier
                                .padding(16.dp, 12.dp)
                        ) {
                            if (password.value.isEmpty()) {
                                Text(
                                    text = "Password",
                                    color = Gray300,
                                    fontSize = 16.sp,
                                    fontFamily = FontFamily(Font(R.font.opensans_regular))
                                )
                            }
                            innerTextField()  //<-- Add this
                        }
                    },
                    textStyle = TextStyle(
                        fontFamily = FontFamily(Font(R.font.opensans_regular)),
                        fontSize = 16.sp
                    )
                )
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding()
            ) {
                Button(
                    onClick = { /*TODO*/ },
                    modifier = Modifier
                        .padding(8.dp, 0.dp),
                    shape = RoundedCornerShape(50.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Orange500)
                ) {
                    Text(
                        modifier = Modifier
                            .padding(8.dp, 0.dp),
                        text = "Login",
                        fontSize = 20.sp,
                        fontFamily = FontFamily(Font(R.font.opensans_bold)),
                        color = GreyishWhite
                    )
                }
            }
            Column(
                modifier = Modifier
                    .clickable { CreatePostActivity() }
                    .padding(0.dp, 50.dp, 0.dp, 0.dp)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Don’t Have an Account Yet?",
                    fontFamily = FontFamily(Font(R.font.robotoslab_bold)),
                    fontSize = 18.sp,
                    color = SoftBlack
                )
                Text(
                    text = "Register Here",
                    fontFamily = FontFamily(Font(R.font.robotoslab_bold)),
                    fontSize = 18.sp,
                    color = Orange500,
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun LoginPreview() {
    TalknityTheme {
        Login()
    }
}
