package com.imtuc.talknity.view

import android.content.Intent
import android.os.Bundle
import android.text.style.UnderlineSpan
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
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign

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
                    Profile()
                }
            }
        }
    }

}

@Composable
fun Profile() {
    val context = LocalContext.current

    var loginUserOrEmail = remember {
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
        )
        {
            Column(
                modifier = Modifier
                    .padding(0.dp, 0.dp, 0.dp, 0.dp)
                    .fillMaxWidth()
                    .height(250.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.dummypict),
                    contentDescription = "Back",
                    contentScale = ContentScale.Crop,
                )
            }

            Surface(
                shape = RoundedCornerShape(50.dp),
                shadowElevation = 8.dp,
                modifier = Modifier
                    .wrapContentSize(align = Alignment.Center)
                    .offset(0.dp, -30.dp)
                    .padding(40.dp, 0.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                ) {
                    Text(
                        text = "James Bond",
                        textAlign = TextAlign.Center,
                        fontSize = 25.sp,
                        fontFamily = FontFamily(Font(R.font.opensans_bold)),
                        modifier = Modifier
                            .padding(35.dp, 16.dp)
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
                        fontSize = 28.sp,
                        color = SoftBlack
                    )
                    Text(
                        text = "Information",
                        fontFamily = FontFamily(Font(R.font.robotoslab_semibold)),
                        fontSize = 28.sp,
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
                        fontFamily = FontFamily(Font(R.font.robotoslab_semibold)),
                        fontSize = 20.sp,
                        color = SoftBlack
                    )
                    Text(
                        text = "James Bond",
                        fontFamily = FontFamily(Font(R.font.robotoslab_semibold)),
                        fontSize = 20.sp,
                        color = Gray300
                    )
                }
                Row(
                    modifier = Modifier
                        .padding(0.dp, 20.dp, 0.dp, 0.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Email ",
                        fontFamily = FontFamily(Font(R.font.robotoslab_semibold)),
                        fontSize = 20.sp,
                        color = SoftBlack
                    )
                    Text(
                        text = "jamesbond@gmail.com",
                        fontFamily = FontFamily(Font(R.font.robotoslab_semibold)),
                        fontSize = 20.sp,
                        color = Gray300
                    )
                }
                Row(
                    modifier = Modifier
                        .padding(0.dp, 20.dp, 0.dp, 0.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Password",
                        fontFamily = FontFamily(Font(R.font.robotoslab_semibold)),
                        fontSize = 20.sp,
                        color = SoftBlack
                    )
                    Text(
                        text = "*********",
                        fontFamily = FontFamily(Font(R.font.robotoslab_semibold)),
                        fontSize = 20.sp,
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
                        context.startActivity(Intent(context, LoginActivity::class.java))
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
                        fontSize = 20.sp,
                        fontFamily = FontFamily(Font(R.font.opensans_semibold)),
                        color = GreyishWhite
                    )
                }
            }
            Surface(
                shape = RoundedCornerShape(25.dp),
                shadowElevation = 8.dp,
                modifier = Modifier
                    .wrapContentSize(align = Alignment.Center)
                    .padding(0.dp, 32.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .padding(15.dp, 15.dp),
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
                        text = "Your Discussions",
                        textAlign = TextAlign.Center,
                        fontSize = 20.sp,
                        color = Orange500,
                        fontFamily = FontFamily(Font(R.font.opensans_semibold)),
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfilePreview() {
    TalknityTheme {
        Profile()
    }
}
