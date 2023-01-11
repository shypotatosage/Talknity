package com.imtuc.talknity.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.insets.navigationBarsWithImePadding
import com.google.accompanist.insets.statusBarsPadding
import com.imtuc.talknity.R
import com.imtuc.talknity.components.CommunityLeader
import com.imtuc.talknity.components.CommunityMember
import com.imtuc.talknity.components.IndividualCommunity
import com.imtuc.talknity.view.ui.theme.*

class CommunityDetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TalknityTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.White
                ) {
                    CommunityDetail()
                }
            }
        }
    }

}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CommunityDetail() {
    var displayContact = remember {
        mutableStateOf("")
    }
    var displayDesc = remember {
        mutableStateOf("")
    }
    val context = LocalContext.current
    ProvideWindowInsets(windowInsetsAnimationsEnabled = true) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .background(Color.White)
//                .verticalScroll(rememberScrollState())
                .navigationBarsWithImePadding(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Orange500)
                    .padding(20.dp, 58.dp, 0.dp, 0.dp)
            ) {
                Text(
                    text = "AThree",
                    fontFamily = FontFamily(Font(R.font.robotoslab_bold)),
                    fontSize = 40.sp,
                    color = Color.White,
//                    modifier = Modifier
//                        .padding(0.dp, 32.dp, 0.dp, 10.dp)
                )
                Text(
                    text = "Sports Community",
                    fontFamily = FontFamily(Font(R.font.robotoslab_bold)),
                    fontSize = 30.sp,
                    color = Color.White,
                )

            }
            Row(
                modifier = Modifier
                    .background(Orange500)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
            ) {
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    shadowElevation = 8.dp,
                    modifier = Modifier
                        .wrapContentSize(align = Alignment.Center)
                        .width(200.dp)
                        .height(200.dp)
                        .padding(0.dp, 13.dp)
                        .shadow(10.dp)
                ) {
                    Image(

<<<<<<< HEAD
                            }
                            Row(
                                modifier = Modifier
                                    .background(Orange500)
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.Center,
                            ) {
                                Surface(
                                    shape = RoundedCornerShape(16.dp),
                                    shadowElevation = 8.dp,
                                    modifier = Modifier
                                        .wrapContentSize(align = Alignment.Center)
                                        .padding(0.dp, 20.dp)
                                ) {
                                    Image(
                                        painter = rememberAsyncImagePainter(Const.BASE_URL + "/" + displayImage.value),
                                        contentDescription = "Community Logo",
                                        modifier = Modifier
                                            .width(200.dp)
                                            .height(200.dp)
                                    )
                                }
                                if ((userIsMember.value != "1") && !userIsLeader.value) {
                                    Button(
                                        onClick = {
                                            if (communityViewModel.communityMembers.value!!.member_count == "100") {
                                                Toast.makeText(context, "Oops, cannot join this community. Maximum member (100) has been reached", Toast.LENGTH_LONG).show()
                                            } else {
                                                val preferences = context.getSharedPreferences(
                                                    "user",
                                                    Context.MODE_PRIVATE
                                                )

                                                communityViewModel.joinCommunity(
                                                    community_id,
                                                    preferences.getInt("user_id", -1).toString()
                                                )
                                            }
                                        },
                                        modifier = Modifier
                                            .padding(13.dp, 63.dp, 0.dp, 0.dp),
                                        shape = RoundedCornerShape(50.dp),
                                        colors = ButtonDefaults.buttonColors(backgroundColor = Red500)
                                    ) {
                                        Text(
                                            modifier = Modifier
                                                .padding(13.dp, .5.dp),
                                            text = "Apply",
                                            fontSize = 25.sp,
                                            fontFamily = FontFamily(Font(R.font.opensans_bold)),
                                            color = GreyishWhite
                                        )
                                    }
                                } else if (userIsMember.value == "1") {
                                    Button(
                                        onClick = {
                                            val preferences = context.getSharedPreferences(
                                                "user",
                                                Context.MODE_PRIVATE
                                            )

                                            communityViewModel.signoutCommunity(
                                                community_id,
                                                preferences.getInt("user_id", -1).toString()
                                            )
                                        },
                                        modifier = Modifier
                                            .padding(13.dp, 63.dp, 0.dp, 0.dp),
                                        shape = RoundedCornerShape(50.dp),
                                        colors = ButtonDefaults.buttonColors(backgroundColor = Red500)
                                    ) {
                                        Text(
                                            modifier = Modifier
                                                .padding(13.dp, .5.dp),
                                            text = "Signout",
                                            fontSize = 25.sp,
                                            fontFamily = FontFamily(Font(R.font.opensans_bold)),
                                            color = GreyishWhite
                                        )
                                    }
                                }
                            }
                            Row(
                                modifier = Modifier
                                    .padding(24.dp, 0.dp)
//                    .background(Color.White)
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.Center,
                            ) {
                                Column() {
                                    Text(
                                        text = "Contact",
                                        fontFamily = FontFamily(Font(R.font.robotoslab_bold)),
                                        fontSize = 24.sp,
                                        color = SoftBlack,
                                        modifier = Modifier
                                            .padding(0.dp, 28.dp, 0.dp, 10.dp)
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
                                            value = displayContact.value,
                                            onValueChange = {},
                                            enabled = false,
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .wrapContentHeight()
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
                                                    if (displayContact.value.isEmpty()) {
                                                        Text(
                                                            text = "Display Contact",
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
                                        text = "Description",
                                        fontFamily = FontFamily(Font(R.font.robotoslab_bold)),
                                        fontSize = 24.sp,
                                        color = SoftBlack,
                                        modifier = Modifier
                                            .padding(0.dp, 20.dp, 0.dp, 10.dp)
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
                                            value = displayDesc.value,
                                            onValueChange = {},
                                            enabled = false,
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .wrapContentHeight()
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
                                                    if (displayDesc.value.isEmpty()) {
                                                        Text(
                                                            text = "Display Description",
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
                                }

                            }
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(24.dp, 20.dp, 24.dp, 10.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically

                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Image(
                                        painter = painterResource(id = R.drawable.ownedcommunity),
                                        contentDescription = "Back",
                                        modifier = Modifier
                                            .size(40.dp)
                                            .padding(0.dp, 0.dp, 8.dp, 0.dp)
                                    )
                                    Text(
                                        text = (members.size - 1).toString() + " Members",
                                        textAlign = TextAlign.Start,
                                        fontSize = 20.sp,
                                        fontFamily = FontFamily(Font(R.font.robotoslab_semibold)),
                                    )
                                }
                            }
                        }
                    }

                    gridItems(
                        data = members,
                        columnCount = 3,
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
=======
                        painter = painterResource(id = R.drawable.dummypict),
                        contentDescription = "Profile Picture",
>>>>>>> parent of abe74f7 (Upd)
                        modifier = Modifier
                            .fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                }
                Button(onClick = { /*TODO*/ },
                    modifier = Modifier
                        .padding(13.dp, 63.dp,0.dp, 0.dp),
                    shape = RoundedCornerShape(50.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Red500)) {
                    Text(
                        modifier = Modifier
                            .padding(13.dp, .5.dp),
                        text = "Apply",
                        fontSize = 25.sp,
                        fontFamily = FontFamily(Font(R.font.opensans_bold)),
                        color = GreyishWhite
                    )
                }
            }
            Row(
                modifier = Modifier
                    .padding(24.dp, 0.dp)
//                    .background(Color.White)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
            ){
                Column() {
                    Text(
                        text = "Contact",
                        fontFamily = FontFamily(Font(R.font.robotoslab_bold)),
                        fontSize = 24.sp,
                        color = SoftBlack,
                        modifier = Modifier
                            .padding(0.dp, 28.dp, 0.dp, 10.dp)
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
                            value = displayContact.value,
                            onValueChange = {
                                displayContact.value = it
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
                                    if (displayContact.value.isEmpty()) {
                                        Text(
                                            text = "Display Contact",
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
                            text = "Description",
                            fontFamily = FontFamily(Font(R.font.robotoslab_bold)),
                            fontSize = 24.sp,
                            color = SoftBlack,
                            modifier = Modifier
                                .padding(0.dp, 28.dp, 0.dp, 10.dp)
                        )
                        Surface(
                            modifier = Modifier
                                .wrapContentSize(align = Alignment.Center)
                                .width(500.dp)
                                .height(200.dp),
                            border = BorderStroke(
                                width = 0.5.dp,
                                color = GrayBorder,
                            ),
                            shape = RoundedCornerShape(25.dp),
                            shadowElevation = 4.dp
                        ) {
                            BasicTextField(
                                value = displayDesc.value,
                                onValueChange = {
                                    displayDesc.value = it
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
                                            .width(500.dp)
                                            .height(200.dp)
                                            .padding(16.dp, 12.dp)
                                    ) {
                                        if (displayDesc.value.isEmpty()) {
                                            Text(
                                                text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.",
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
                }

            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp, 20.dp),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically

                ){
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ownedcommunity),
                        contentDescription = "Back",
                        modifier = Modifier
                            .size(50.dp)
                            .padding(5.dp, 0.dp)
                    )
                    Text(
                        text = "50 Members",
                        textAlign = TextAlign.Start,
                        fontSize = 24.sp,
                        fontFamily = FontFamily(Font(R.font.robotoslab_semibold)),
                    )
                }
                Text(
                    text = "Manage Member",
                    textAlign = TextAlign.Start,
                    fontSize = 13.sp,
                    fontFamily = FontFamily(Font(R.font.robotoslab_semibold)),
                    color = Orange500
                )
            }
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                content = {
                    items(1) {
                        CommunityLeader()
                    }
                    items(10) {
                        CommunityMember()
                    }
                })


        }
    }
}

//Column(
//    modifier = Modifier
//        .fillMaxSize()
//        .background(Color.White)
//) {
//
//}
//        }
//    }
//}

@Preview(showBackground = true)
@Composable
fun CommunityDetailPreview() {
    TalknityTheme {
        CommunityDetail()
    }
}