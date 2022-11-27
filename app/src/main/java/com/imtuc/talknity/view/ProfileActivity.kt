package com.imtuc.talknity.view

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
                    painter = painterResource(id = R.drawable.profilebackground),
                    contentScale = ContentScale.Crop
                )
                .fillMaxSize()
                .statusBarsPadding()
                .navigationBarsWithImePadding()
                .verticalScroll(rememberScrollState()),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp, 24.dp, 24.dp, 0.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Image(
                    painter = painterResource(id = com.imtuc.talknity.R.drawable.arrow_back),
                    contentDescription = "Back",
                    modifier = Modifier
                        .height(28.dp)
                )
                Image(
                    painter = painterResource(id = com.imtuc.talknity.R.drawable.logouticon),
                    contentDescription = "Back",
                    modifier = Modifier
                        .height(28.dp)
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Image(
                    painter = painterResource(id = R.drawable.dummypict),
                    contentDescription = "Back",
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier
                        .padding(0.dp, 40.dp, 0.dp, 0.dp)
                        .size(180.dp)
                        .clip(CircleShape)
                )
                Text(
                    text = "James Holand",
                    fontFamily = FontFamily(Font(R.font.robotoslab_bold)),
                    fontSize = 30.sp,
                    color = Color.Black,
                    modifier = Modifier
                        .padding(0.dp, 24.dp, 0.dp, 0.dp)
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp, 24.dp, 24.dp, 0.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    Column(
                        modifier = Modifier
                            .padding(0.dp, 10.dp, 0.dp, 0.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Image(
                            painter = painterResource(id = com.imtuc.talknity.R.drawable.posticon),
                            contentDescription = "Back",
                            modifier = Modifier
                                .size(70.dp)
                        )
                        Text(
                            text = "My Post",
                            fontFamily = FontFamily(Font(R.font.robotoslab_bold)),
                            fontSize = 18.sp,
                            color = Orange500,
                            modifier = Modifier
                                .padding(0.dp, 20.dp, 0.dp, 0.dp)
                        )
                    }

                    Column(
                        modifier = Modifier
                            .padding(0.dp, 10.dp, 0.dp, 0.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Image(
                            painter = painterResource(id = com.imtuc.talknity.R.drawable.mycommunity),
                            contentDescription = "Back",
                            modifier = Modifier
                                .size(70.dp)
                        )
                        Text(
                            text = "My Community",
                            fontFamily = FontFamily(Font(R.font.robotoslab_bold)),
                            fontSize = 18.sp,
                            color = Orange500,
                            modifier = Modifier
                                .padding(0.dp, 20.dp, 0.dp, 0.dp)
                        )
                    }
                }
                Column(
                    modifier = Modifier
                        .padding(0.dp, 10.dp, 0.dp, 0.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Image(
                        painter = painterResource(id = com.imtuc.talknity.R.drawable.mycommunity),
                        contentDescription = "Back",
                        modifier = Modifier
                            .size(70.dp)
                    )
                    Text(
                        text = "My Community",
                        fontFamily = FontFamily(Font(R.font.robotoslab_bold)),
                        fontSize = 18.sp,
                        color = Orange500,
                        modifier = Modifier
                            .padding(0.dp, 20.dp, 0.dp, 0.dp)
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
