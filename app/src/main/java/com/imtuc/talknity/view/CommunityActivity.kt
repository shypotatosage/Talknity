package com.imtuc.talknity.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.imtuc.talknity.R
import com.imtuc.talknity.view.ui.theme.GreyishWhite
import com.imtuc.talknity.view.ui.theme.Orange500
import com.imtuc.talknity.view.ui.theme.SoftBlack
import com.imtuc.talknity.view.ui.theme.TalknityTheme

class CommunityActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TalknityTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    EnterCommunity()
                }
            }
        }
    }
}

@Composable
fun EnterCommunity() {
    Column(modifier = Modifier
        .paint(
            painter = painterResource(id = R.drawable.bg1),
            contentScale = ContentScale.Crop
        )
        .fillMaxSize()) {

    }

    Column(modifier = Modifier
        .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                text = "Community ",
                fontFamily = FontFamily(Font(R.font.robotoslab_bold)),
                fontSize = 30.sp,
                color = Orange500,
                modifier = Modifier
                    .padding(0.dp, 15.dp, 0.dp, 10.dp)
            )
            Text(
                text = "Corner",
                fontFamily = FontFamily(Font(R.font.robotoslab_bold)),
                fontSize = 30.sp,
                color = SoftBlack,
                modifier = Modifier
                    .padding(0.dp, 15.dp, 0.dp, 10.dp)
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth(1f)
                .padding(0.dp, 16.dp, 0.dp, 0.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(
                modifier = Modifier
                    .fillMaxWidth(0.375f)
                    .defaultMinSize(minWidth = 1.dp, minHeight = 1.dp),
                contentPadding = PaddingValues(),
                shape = RoundedCornerShape(16.dp),
                onClick = {},
                colors = ButtonDefaults.buttonColors(containerColor = Orange500),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 8.dp)
            ) {
                Column(
                    modifier = Modifier
                        .padding(0.dp, 32.dp, 0.dp, 0.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.findcommunity),
                        contentDescription = "Back",
                        modifier = Modifier
                            .height(80.dp)
                    )
                    Text(
                        text = "Find Community",
                        fontFamily = FontFamily(Font(R.font.opensans_bold)),
                        fontSize = 20.sp,
                        color = GreyishWhite,
                        modifier = Modifier
                            .padding(12.dp, 16.dp, 12.dp, 24.dp),
                        textAlign = TextAlign.Center
                    )
                }
            }
            Button(
                modifier = Modifier
                    .fillMaxWidth(0.625f)
                    .defaultMinSize(minWidth = 1.dp, minHeight = 1.dp),
                contentPadding = PaddingValues(),
                shape = RoundedCornerShape(16.dp),
                onClick = {},
                colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 8.dp)
            ) {
                Column(
                    modifier = Modifier
                        .padding(0.dp, 32.dp, 0.dp, 0.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ownedcommunity),
                        contentDescription = "Back",
                        modifier = Modifier
                            .height(80.dp)
                    )
                    Text(
                        text = "Owned Community",
                        fontFamily = FontFamily(Font(R.font.opensans_bold)),
                        fontSize = 20.sp,
                        color = Orange500,
                        modifier = Modifier
                            .padding(12.dp, 16.dp, 12.dp, 24.dp),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview3() {
    TalknityTheme {
        EnterCommunity()
    }
}