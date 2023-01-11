package com.imtuc.talknity.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import com.imtuc.talknity.components.CommentCard
import com.imtuc.talknity.view.ui.theme.*

class IndividualDiscussionActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TalknityTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    IndividualDiscussion()
                }
            }
        }
    }
}

@Composable
fun IndividualDiscussion() {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(0.dp, 0.dp, 0.dp, 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .padding(20.dp, 24.dp, 0.dp, 0.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.arrow_back),
                    contentDescription = "Back",
                    modifier = Modifier
                        .height(28.dp)
                )
                Text(
                    text = "Back",
                    fontFamily = FontFamily(Font(R.font.robotoslab_regular)),
                    modifier = Modifier
                        .padding(20.dp, 0.dp, 0.dp, 0.dp),
                    fontSize = 24.sp,
                    color = SoftBlack
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
            Surface(
                modifier = Modifier
                    .wrapContentSize(),
                shape = RoundedCornerShape(100.dp),
                shadowElevation = 6.dp
            ) {
                Image(
                    painter = painterResource(id = R.drawable.talknitylogo),
                    contentDescription = "Profile Image",
                    modifier = Modifier
                        .height(80.dp)
                        .width(80.dp),
                    contentScale = ContentScale.Crop
                )
            }
            Text(
                text = "Jennie",
                fontSize = 16.sp,
                fontFamily = FontFamily(Font(R.font.opensans_bold)),
                color = Orange500,
                modifier = Modifier
                    .fillMaxWidth(0.4f)
                    .padding(0.dp, 12.dp, 0.dp, 8.dp),
                textAlign = TextAlign.Center
            )
            Text(
                text = "I Just Lost My Cat",
                fontSize = 24.sp,
                fontFamily = FontFamily(Font(R.font.robotoslab_semibold)),
                color = SoftBlack,
                modifier = Modifier
                    .fillMaxWidth(0.75f),
                textAlign = TextAlign.Center
            )
            Surface(
                modifier = Modifier
                    .padding(0.dp, 24.dp),
                shape = RoundedCornerShape(16.dp),
                shadowElevation = 8.dp
            ) {
                Image(
                    painter = painterResource(id = R.drawable.dummypict),
                    contentDescription = "Discussion Image",
                    modifier = Modifier
                        .height(175.dp)
                        .fillMaxWidth(0.7f),
                    contentScale = ContentScale.Crop
                )
            }
            Text(
                text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, " +
                        "sed do eiusmod tempor incididunt ut labore et dolore magna " +
                        "aliqua. Ut enim ad minim veniam, quis nostrud exercitation " +
                        "ullamco laboris nisi ut aliquip ex ea commodo consequat.",
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .padding(0.dp, 0.dp, 0.dp, 20.dp),
                fontSize = 16.sp,
                fontFamily = FontFamily(Font(R.font.opensans_regular))
            )
            Button(
                onClick = { /*TODO*/ },
                modifier = Modifier
                    .fillMaxWidth(0.85f)
                    .defaultMinSize(minWidth = 1.dp, minHeight = 1.dp),
                contentPadding = PaddingValues(),
                colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp),
                shape = RoundedCornerShape(25.dp),
                border = BorderStroke(
                    width = 0.25.dp,
                    color = GrayBorder
                )
            ) {
                Box(
                    modifier = Modifier
                        .padding(16.dp, 12.dp)
                        .fillMaxWidth()
                ) {
                    Text(
                        text = "Add Comment",
                        color = Gray300,
                        fontSize = 16.sp,
                        fontFamily = FontFamily(Font(R.font.opensans_regular))
                    )
                }
            }
        }
        LazyColumn() {
            items(4) {
                CommentCard()
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun IndividualDiscussionPreview() {
    TalknityTheme {
        IndividualDiscussion()
    }
}