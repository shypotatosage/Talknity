package com.imtuc.talknity.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.imtuc.talknity.R
import com.imtuc.talknity.model.CommunityCategory

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CommunityCategoryCard() {
    Surface(
        shape = RoundedCornerShape(16.dp),
        shadowElevation = 8.dp,
        modifier = Modifier
            .wrapContentSize(align = Alignment.Center)
            .padding(13.dp, 0.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .height(150.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.ownedcommunity),
                contentDescription = "Back",
                modifier = Modifier
                    .height(40.dp)
            )
            Text(
                text = "All Categories",
                textAlign = TextAlign.Center,
                fontSize = 20.sp,
                fontFamily = FontFamily(Font(R.font.opensans_semibold)),
                modifier = Modifier
                    .padding(12.dp, 16.dp, 12.dp, 0.dp)
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun IndividualCommunity() {
    Surface(
        shape = RoundedCornerShape(16.dp),
        shadowElevation = 8.dp,
        modifier = Modifier
            .wrapContentSize(align = Alignment.Center)
            .padding(0.dp, 13.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .height(150.dp)
                .padding(10.dp, 13.dp),
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.Start
        ) {
            Column(
                horizontalAlignment = Alignment.Start,
                modifier = Modifier
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.Top,
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ownedcommunity),
                    contentDescription = "Back",
                    modifier = Modifier
                        .size(70.dp)
                )
            }
            Column(
                horizontalAlignment = Alignment.Start,
                modifier = Modifier
                    .padding(10.dp, 0.dp, 0.dp, 0.dp)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Top,
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "BasCom",
                        textAlign = TextAlign.Start,
                        fontSize = 20.sp,
                        fontFamily = FontFamily(Font(R.font.robotoslab_semibold)),
                    )
                    Image(
                        painter = painterResource(id = R.drawable.ownedcommunity),
                        contentDescription = "Back",
                        modifier = Modifier
                            .size(30.dp)
                            .padding(5.dp,0.dp)
                    )
                    Text(
                        text = "50 Members",
                        textAlign = TextAlign.Start,
                        fontSize = 13.sp,
                        fontFamily = FontFamily(Font(R.font.robotoslab_semibold)),
                    )
                }
                Text(
                    text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor",
                    modifier = Modifier
                        .padding(0.dp, 5.dp)
                        .fillMaxHeight(),
                    textAlign = TextAlign.Start,
                    fontSize = 13.sp,
                    fontFamily = FontFamily(Font(R.font.opensans_semibold)),
                )
            }
        }
    }
}