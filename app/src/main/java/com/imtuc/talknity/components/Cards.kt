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


@Composable
fun CommunityCategoryCard() {
    Surface(
        shape = RoundedCornerShape(16.dp),
        shadowElevation = 8.dp,
        modifier = Modifier
            .wrapContentSize(align = Alignment.Center)
            .padding(0.dp, 16.dp)
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
fun DiscussionCard() {
    Surface(
        shape = RoundedCornerShape(16.dp),
        shadowElevation = 8.dp,
        modifier = Modifier
            .wrapContentSize(align = Alignment.Center)
            .padding(0.dp, 16.dp)
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