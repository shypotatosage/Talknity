package com.imtuc.talknity.view

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
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
import androidx.compose.ui.graphics.colorspace.WhitePoint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavHostController
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.insets.navigationBarsWithImePadding
import com.google.accompanist.insets.statusBarsPadding
import com.imtuc.talknity.R
import com.imtuc.talknity.navigation.Screen
import com.imtuc.talknity.view.ui.theme.*
import com.imtuc.talknity.viewmodel.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.regex.Pattern

@AndroidEntryPoint
class RegisterActivity : ComponentActivity() {

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
//                    Register(authViewModel, this)
                }
            }
        }
    }

}

@Composable
fun Register(
    authViewModel: AuthViewModel,
    lifecycleOwner: LifecycleOwner,
    navController: NavHostController
) {
    val context = LocalContext.current

    var email = remember {
        mutableStateOf("")
    }

    var username = remember {
        mutableStateOf("")
    }

    var password = remember {
        mutableStateOf("")
    }

    var passwordValue = remember {
        mutableStateOf("")
    }

    var passwordVisible = remember {
        mutableStateOf(false)
    }

    var emailRequired = remember {
        mutableStateOf(false)
    }

    var usernameRequired = remember {
        mutableStateOf(false)
    }

    var passwordRequired = remember {
        mutableStateOf(false)
    }

    var usernameExists = remember {
        mutableStateOf(false)
    }

    var emailExists = remember {
        mutableStateOf(false)
    }

    var registerSuccess = remember {
        mutableStateOf(false)
    }

    val image = if (passwordVisible.value) {
        Icons.Filled.Visibility
    } else {
        Icons.Filled.VisibilityOff
    }

    authViewModel.register.observe(lifecycleOwner, Observer { response ->
        if (response != null) {
            if (response == "User Successfully Registered") {
                authViewModel.resetRegister()
                Toast.makeText(context, response, Toast.LENGTH_SHORT).show()
                registerSuccess.value = true
            } else {
                if (response == "Bad Gateway") {
                    usernameExists.value = true
                } else if (response == "Bad Request") {
                    emailExists.value = true
                } else {
                    Toast.makeText(context, response, Toast.LENGTH_SHORT).show()
                    authViewModel.resetRegister()
                }
            }
        }
    })

    if (registerSuccess.value) {
        registerSuccess.value = false
        navController.popBackStack()
        navController.navigate(Screen.Login.route)
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
                    .padding(0.dp, 50.dp, 0.dp, 0.dp)
                    .size(300.dp)
            )
            Column(
                modifier = Modifier
                    .padding(24.dp, 0.dp)
            ) {
                BasicTextField(
                    value = email.value,
                    onValueChange = {
                        email.value = it
                    },
                    enabled = true,
                    singleLine = true,
                    maxLines = 1,
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
                            if (email.value.isEmpty()) {
                                Text(
                                    text = "Email",
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
                if (emailRequired.value && !Patterns.EMAIL_ADDRESS.matcher(email.value.trim())
                        .matches()
                ) {
                    Text(
                        text = "Email is not valid",
                        fontFamily = FontFamily(Font(R.font.opensans_regular)),
                        fontSize = 13.sp,
                        color = Red500,
                        modifier = Modifier
                            .padding(6.dp, 4.dp, 6.dp, 0.dp)
                    )
                }
            }
            Column(
                modifier = Modifier
                    .padding(24.dp, 30.dp, 24.dp, 0.dp)
            ) {
                BasicTextField(
                    value = username.value,
                    onValueChange = {
                        username.value = it
                    },
                    enabled = true,
                    singleLine = true,
                    maxLines = 1,
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
                            if (username.value.isEmpty()) {
                                Text(
                                    text = "Username",
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
                if (usernameRequired.value && !Pattern.compile("^(?=.{8,20}\$)(?![_.])(?!.*[_.]{2})[a-zA-Z0-9._]+(?<![_.])\$")
                        .matcher(username.value.trim()).matches()) {
                    Text(
                        text = "Must be 8-20 characters, only . and _ allowed but may not end/start with . or _",
                        fontFamily = FontFamily(Font(R.font.opensans_regular)),
                        fontSize = 13.sp,
                        color = Red500,
                        modifier = Modifier
                            .padding(6.dp, 4.dp, 6.dp, 0.dp)
                    )
                }
                if (usernameExists.value) {
                    Text(
                        text = "Username already exists",
                        fontFamily = FontFamily(Font(R.font.opensans_regular)),
                        fontSize = 13.sp,
                        color = Red500,
                        modifier = Modifier
                            .padding(6.dp, 4.dp, 6.dp, 0.dp)
                    )
                }
            }
            Column(
                modifier = Modifier
                    .padding(24.dp, 30.dp),
            ) {
                BasicTextField(
                    value = passwordValue.value,
                    onValueChange = {
                        passwordValue.value = it
                    },
                    visualTransformation = if (passwordVisible.value) VisualTransformation.None else PasswordVisualTransformation(),
                    enabled = true,
                    singleLine = true,
                    maxLines = 1,
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
                                if (passwordValue.value.isEmpty()) {
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
                if (passwordRequired.value && !Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#\$%^&+=])[^-\\s]{8,}\$")
                        .matcher(passwordValue.value).matches()
                ) {
                    Text(
                        text = "Min. 8 characters, 1 uppercase and lowercase letter, 1 number, 1 special character, and no whitespaces",
                        fontFamily = FontFamily(Font(R.font.opensans_regular)),
                        fontSize = 13.sp,
                        color = Red500,
                        modifier = Modifier
                            .padding(6.dp, 4.dp, 6.dp, 0.dp)
                    )
                }
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding()
            ) {
                Button(
                    onClick = {
                        usernameExists.value = false
                        emailExists.value = false

                        if (!Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#\$%^&+=])[^-\\s]{8,}\$")
                                .matcher(passwordValue.value).matches()
                        ) {
                            passwordRequired.value = true
                        } else {
                            passwordRequired.value = false
                        }

                        if (!Patterns.EMAIL_ADDRESS.matcher(email.value.trim())
                                .matches()
                        ) {
                            emailRequired.value = true
                        } else {
                            emailRequired.value = false
                        }

                        if (!Pattern.compile("^(?=.{8,20}\$)(?![_.])(?!.*[_.]{2})[a-zA-Z0-9._]+(?<![_.])\$")
                                .matcher(username.value.trim()).matches()
                        ) {
                            usernameRequired.value = true
                        } else {
                            usernameRequired.value = false
                        }

                        if (!(usernameRequired.value || emailRequired.value || passwordRequired.value)) {
                            authViewModel.registerUser(
                                username.value,
                                email.value,
                                passwordValue.value
                            )

                        }
                    },
                    modifier = Modifier
                        .padding(8.dp, 0.dp),
                    shape = RoundedCornerShape(50.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Orange500)
                ) {
                    Text(
                        modifier = Modifier
                            .padding(8.dp, 0.dp),
                        text = "Register",
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
                    text = "Already Have an Account?",
                    fontFamily = FontFamily(Font(R.font.robotoslab_bold)),
                    fontSize = 18.sp,
                    color = SoftBlack
                )
                Text(
                    text = "Login Here",
                    fontFamily = FontFamily(Font(R.font.robotoslab_bold)),
                    fontSize = 18.sp,
                    color = Orange500,
                    modifier = Modifier
                        .clickable {
                            navController.popBackStack()
                            navController.navigate(Screen.Login.route)
                        }
                        .padding(0.dp, 4.dp)
                )
            }
        }
    }
}