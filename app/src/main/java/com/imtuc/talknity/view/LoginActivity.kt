package com.imtuc.talknity.view

import android.content.ContentValues
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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.lifecycle.ViewModelProvider
import com.imtuc.talknity.viewmodel.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : ComponentActivity() {

    private lateinit var authViewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        authViewModel = ViewModelProvider(this)[AuthViewModel::class.java]

        setContent {
            TalknityTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Login(authViewModel)
                }
            }
        }
    }

}

@Composable
fun Login(authViewModel: AuthViewModel) {
    val context = LocalContext.current

    var loginUserOrEmail = remember {
        mutableStateOf("")
    }

    var password = remember {
        mutableStateOf("")
    }

    var passwordNoStar = remember {
        mutableStateOf("")
    }

    var passwordStar = countChar(passwordNoStar.value)

    var passwordValue = remember {
        mutableStateOf("")
    }

    var passwordVisible = remember {
        mutableStateOf(false)
    }

    val image = if (passwordVisible.value) {
        Icons.Filled.Visibility
    } else {
        Icons.Filled.VisibilityOff
    }

    LaunchedEffect(key1 =  authViewModel.login.value){
        Log.d(ContentValues.TAG, "Login Message: ${authViewModel.login.value}")
        if(authViewModel.login.value != null){
            if (authViewModel.login.value == "Login Successful") {
                context.startActivity(Intent(context, HomeActivity::class.java))
            } else {
                Toast.makeText(context, authViewModel.login.value, Toast.LENGTH_SHORT).show()
            }
        }
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
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Image(
                painter = painterResource(id = R.drawable.talknitylogo),
                contentDescription = "Back",
                modifier = Modifier
                    .padding(0.dp, 100.dp, 0.dp, 0.dp)
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
                    .padding(24.dp, 30.dp),
            ) {

                BasicTextField(
                    value = password.value,
                    onValueChange = {
                        password.value = it
                        passwordNoStar.value = it
                        passwordValue.value = it
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
                            textColor = SoftBlack,
                        )
                        Box(
                            modifier = Modifier
                                .padding(16.dp, 0.dp),
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(0.dp, 0.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                if (password.value.isEmpty()) {
                                    Text(
                                        text = "Password",
                                        color = Gray300,
                                        fontSize = 16.sp,
                                        fontFamily = FontFamily(Font(R.font.opensans_regular))
                                    )
                                    IconButton(onClick = {
                                        passwordVisible.value = !passwordVisible.value
                                    }) {
                                        Icon(imageVector = image, "")
                                    }
                                } else {
                                    Row(
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        modifier = Modifier.fillMaxSize(),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        innerTextField()
                                        IconButton(onClick = {
                                            passwordVisible.value = !passwordVisible.value
                                            if (passwordVisible.value) {
                                                password.value = passwordNoStar.value
                                            } else {
                                                password.value = passwordStar
                                            }
                                        }) {
                                            Icon(imageVector = image, "")
                                        }
                                    }
                                }
                            }
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
                    onClick = {
                        authViewModel.loginUser(loginUserOrEmail.value, passwordValue.value, context)
                    },
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
                    .padding(0.dp, 30.dp)
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
                Button(
                    onClick = {
                        context.startActivity(Intent(context, HomeActivity::class.java))
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.White)
                )
                {
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
