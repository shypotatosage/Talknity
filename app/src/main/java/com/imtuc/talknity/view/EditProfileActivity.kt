package com.imtuc.talknity.view

import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.insets.navigationBarsWithImePadding
import com.google.accompanist.insets.statusBarsPadding
import com.imtuc.talknity.R
import com.imtuc.talknity.view.ui.theme.*

class EditProfileActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TalknityTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    EditProfile()
                }
            }
        }
    }
}

@Composable
fun EditProfile() {
    var displayName = remember {
        mutableStateOf("")
    }

    var username = remember {
        mutableStateOf("")
    }

    var email = remember {
        mutableStateOf("")
    }

    var confirmPasswordDisplay = remember {
        mutableStateOf("")
    }

    var passwordVisibility = remember {
        mutableStateOf(false)
    }

    var confirmPassword = remember {
        mutableStateOf("")
    }

    var passwordInvisible = passwordNotVisible(confirmPassword.value)

    val image = if (passwordVisibility.value) {
        Icons.Filled.Visibility
    } else {
        Icons.Filled.VisibilityOff
    }

    if (passwordVisibility.value) {
        confirmPasswordDisplay.value = passwordInvisible
    } else {
        confirmPasswordDisplay.value = confirmPassword.value
    }

    ProvideWindowInsets(windowInsetsAnimationsEnabled = true) {
        Column(
            modifier = Modifier
                .paint(
                    painter = painterResource(id = R.drawable.editprofilebgtheme),
                    contentScale = ContentScale.Crop
                )
                .fillMaxSize()
                .statusBarsPadding()
                .navigationBarsWithImePadding()
                .verticalScroll(rememberScrollState())
        ) {
                Image(

                    painter = painterResource(id = R.drawable.dummypict),
                    contentDescription = "Profile Picture",
                    modifier = Modifier
                        .height(210.dp)
//                        .clip(RoundedCornerShape(15.dp, 15.dp, 0.dp, 0.dp))
                        .fillMaxWidth(),

                    contentScale = ContentScale.Crop
                )
//            Row(
//                modifier = Modifier
//                    .padding(0.dp, 28.dp, 0.dp, 28.dp)
//                    .fillMaxWidth(),
//                verticalAlignment = Alignment.CenterVertically,
//                horizontalArrangement = Arrangement.Center
//            ) {
//                Text(
//                    text = "Edit Your ",
//                    fontFamily = FontFamily(Font(R.font.robotoslab_bold)),
//                    fontSize = 30.sp,
//                    color = SoftBlack
//                )
//                Text(
//                    text = "Profile",
//                    fontFamily = FontFamily(Font(R.font.robotoslab_bold)),
//                    fontSize = 30.sp,
//                    color = Orange500
//                )
//            }
            Column(
                modifier = Modifier
                    .padding(24.dp, 0.dp)
            ) {
                Text(
                    text = "Display Name",
                    fontFamily = FontFamily(Font(R.font.robotoslab_bold)),
                    fontSize = 24.sp,
                    color = SoftBlack,
                    modifier = Modifier
                        .padding(0.dp, 32.dp, 0.dp, 10.dp)
                )
                Surface(
                    border = BorderStroke(
                        width = 0.5.dp,
                        color = GrayBorder
                    ),
                    shape = RoundedCornerShape(25.dp),
                    shadowElevation = 4.dp
                ) {
                    BasicTextField(
                        value = displayName.value,
                        onValueChange = {
                            displayName.value = it
                        },
                        enabled = true,
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
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
                                if (displayName.value.isEmpty()) {
                                    Text(
                                        text = "Display Name",
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
                Text(
                    text = "Username",
                    fontFamily = FontFamily(Font(R.font.robotoslab_bold)),
                    fontSize = 24.sp,
                    color = SoftBlack,
                    modifier = Modifier
                        .padding(0.dp, 15.dp, 0.dp, 10.dp)
                )
                Surface(
                    border = BorderStroke(
                        width = 0.5.dp,
                        color = GrayBorder
                    ),
                    shape = RoundedCornerShape(25.dp),
                    shadowElevation = 4.dp
                ) {
                    BasicTextField(
                        value = username.value,
                        onValueChange = {
                            username.value = it
                        },
                        enabled = true,
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
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
                }
                Text(
                    text = "Email",
                    fontFamily = FontFamily(Font(R.font.robotoslab_bold)),
                    fontSize = 24.sp,
                    color = SoftBlack,
                    modifier = Modifier
                        .padding(0.dp, 15.dp, 0.dp, 10.dp)
                )
                Surface(
                    border = BorderStroke(
                        width = 0.5.dp,
                        color = GrayBorder
                    ),
                    shape = RoundedCornerShape(25.dp),
                    shadowElevation = 4.dp
                ) {
                    BasicTextField(
                        value = email.value,
                        onValueChange = {
                            email.value = it
                        },
                        enabled = true,
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
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
                }
                Text(
                    text = "Confirm Password",
                    fontFamily = FontFamily(Font(R.font.robotoslab_bold)),
                    fontSize = 24.sp,
                    color = SoftBlack,
                    modifier = Modifier
                        .padding(0.dp, 15.dp, 0.dp, 10.dp)
                )
                Surface(
                    border = BorderStroke(
                        width = 0.5.dp,
                        color = GrayBorder
                    ),
                    shape = RoundedCornerShape(25.dp),
                    shadowElevation = 4.dp
                ) {
                    BasicTextField(
                        value = confirmPasswordDisplay.value,
                        onValueChange = {
                            confirmPasswordDisplay.value = it
                            confirmPassword.value = it
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        enabled = true,
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
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
                                    .padding(16.dp, 0.dp),
                                contentAlignment = Alignment.CenterStart
                            ) {
                                if (confirmPasswordDisplay.value.isEmpty()) {
                                    Row(
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        modifier = Modifier.fillMaxWidth(),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            text = "Confirm Password",
                                            color = Gray300,
                                            fontSize = 16.sp,
                                            fontFamily = FontFamily(Font(R.font.opensans_regular))
                                        )
                                        IconButton(onClick = {
                                            passwordVisibility.value = !passwordVisibility.value
                                        }) {
                                            Icon(imageVector = image, "")
                                        }
                                    }
                                    innerTextField()
                                } else {
                                    Row(
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        modifier = Modifier.fillMaxSize(),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        innerTextField()
                                        IconButton(onClick = {
                                            passwordVisibility.value = !passwordVisibility.value
//                                            passwordStar.value = test
                                        }) {
                                            Icon(imageVector = image, "")
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
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(0.dp, 32.dp, 0.dp, 128.dp)) {
                    Button(onClick = { /*TODO*/ },
                        modifier = Modifier
                            .padding(8.dp, 0.dp),
                        shape = RoundedCornerShape(50.dp),
                        colors = ButtonDefaults.buttonColors(backgroundColor = Red500)) {
                        Text(
                            text = "Cancel",
                            fontSize = 20.sp,
                            fontFamily = FontFamily(Font(R.font.opensans_bold)),
                            color = GreyishWhite
                        )
                    }
                    Spacer(modifier = Modifier.width(4.dp))
                    Button(onClick = { /*TODO*/ },
                        modifier = Modifier
                            .padding(8.dp, 0.dp),
                        shape = RoundedCornerShape(50.dp),
                        colors = ButtonDefaults.buttonColors(backgroundColor = Orange500)) {
                        Text(
                            text = "Save Changes",
                            fontSize = 20.sp,
                            fontFamily = FontFamily(Font(R.font.opensans_bold)),
                            color = GreyishWhite
                        )
                    }
                }
            }
        }
    }
}

fun passwordNotVisible(text: String): String {
    var result = ""

    for (Char in text) {
        result += "*"
    }

    return result
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DefaultPreview() {
    TalknityTheme {
        EditProfile()
    }
}