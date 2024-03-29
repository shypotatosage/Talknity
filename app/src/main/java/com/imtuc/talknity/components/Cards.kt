package com.imtuc.talknity.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.imtuc.talknity.R
import com.imtuc.talknity.helper.Const
import com.imtuc.talknity.model.Community
import com.imtuc.talknity.model.CommunityCategory
import com.imtuc.talknity.model.Post
import com.imtuc.talknity.navigation.Screen
import com.imtuc.talknity.view.ui.theme.*

@Composable
fun CommunityCategoryCard(category: CommunityCategory, navController: NavHostController) {
    Surface(
        shape = RoundedCornerShape(16.dp),
        shadowElevation = 8.dp,
        modifier = Modifier
            .wrapContentSize(align = Alignment.Center)
            .padding(13.dp, 12.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth(0.95f)
                .height(150.dp)
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            Color(android.graphics.Color.parseColor("#" + category.category_color1)),
                            Color(android.graphics.Color.parseColor("#" + category.category_color2)),
                            Color(android.graphics.Color.parseColor("#" + category.category_color3)),
                        ),
                        start = Offset(0f, Float.POSITIVE_INFINITY),
                        end = Offset(Float.POSITIVE_INFINITY, 0f)
                    )
                )
                .clickable {
                           navController.navigate(
                               Screen.SelectedCommunityCategory.passParam(
                                   category.category_id,
                                   category.category_name
                               ))
                },
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = rememberAsyncImagePainter(Const.BASE_URL + "/" + category.category_logo),
                contentDescription = "Category Image",
                modifier = Modifier
                    .height(40.dp)
            )
            Text(
                text = category.category_name,
                textAlign = TextAlign.Center,
                fontSize = 20.sp,
                fontFamily = FontFamily(Font(R.font.opensans_semibold)),
                modifier = Modifier
                    .padding(12.dp, 16.dp, 12.dp, 0.dp)
            )
        }
    }
}

@Composable
fun DiscussionCard(post: Post) {
    Surface(
        shape = RoundedCornerShape(16.dp),
        shadowElevation = 6.dp,
        modifier = Modifier
            .wrapContentSize(align = Alignment.Center)
            .padding(0.dp, 16.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp, 20.dp)
                .fillMaxWidth(0.85f)
        ) {
            Row() {
                Surface(
                    modifier = Modifier
                        .wrapContentSize(),
                    shape = RoundedCornerShape(100.dp),
                    shadowElevation = 6.dp
                ) {
                    Image(
                        painter =
                        if (post.creator.user_image.isNotEmpty() && !post.anonymous) {
                            rememberAsyncImagePainter(Const.BASE_URL + "/" + post.creator.user_image)
                        } else {
                            painterResource(id = R.drawable.defaultprofilepicture)
                               },
                        contentDescription = "Discussion Maker Profile",
                        modifier = Modifier
                            .height(60.dp)
                            .width(60.dp),
                        contentScale = ContentScale.Crop
                    )
                }
                Column(
                    modifier = Modifier
                        .padding(16.dp, 0.dp, 0.dp, 0.dp)
                        .fillMaxWidth()
                ) {
                    Text(
                        text =
                        if (!post.anonymous) {
                            post.creator.user_displayname
                        } else {
                               "Unknown"
                               },
                        fontSize = 16.sp,
                        fontFamily = FontFamily(Font(R.font.opensans_bold)),
                        modifier = Modifier,
                        color = Orange500
                    )
                    Text(
                        text =
                        if (post.post_title.length > 35) {
                            post.post_title.trim().substring(0, 34) + "..."
                        } else {
                            post.post_title
                        },
                        fontSize = 22.sp,
                        fontFamily = FontFamily(Font(R.font.robotoslab_semibold)),
                        modifier = Modifier,
                        color = SoftBlack
                    )
                }
            }

            if (post.post_image.isNotEmpty()) {
                Surface(
                    modifier = Modifier
                        .padding(0.dp, 20.dp, 0.dp, 0.dp),
                    shape = RoundedCornerShape(25.dp),
                    shadowElevation = 6.dp
                ) {
                    Image(
                        painter = rememberAsyncImagePainter(Const.BASE_URL + "/" + post.post_image),
                        contentDescription = "Discussion Picture",
                        modifier = Modifier
                            .height(150.dp)
                            .fillMaxWidth(),
                        contentScale = ContentScale.Crop
                    )
                }
            }

            Text(
                text =
                if (post.post_content.trim().length > 125) {
                    post.post_content.trim().substring(0, 124) + "..."
                } else {
                    post.post_content
                },
                fontSize = 16.sp,
                fontFamily = FontFamily(Font(R.font.opensans_regular)),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(0.dp, 20.dp, 0.dp, 12.dp)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    modifier = Modifier
                        .wrapContentWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
//                    Box(
//                        modifier = Modifier
//                            .wrapContentWidth()
//                    ) {
//                        Surface(
//                            modifier = Modifier
//                                .wrapContentSize(),
//                            shape = RoundedCornerShape(100.dp),
//                            shadowElevation = 6.dp
//                        ) {
//                            Image(
//                                painter = painterResource(id = R.drawable.dummypict),
//                                contentDescription = "Commentator 1 Profile",
//                                modifier = Modifier
//                                    .height(36.dp)
//                                    .width(36.dp),
//                                contentScale = ContentScale.Crop
//                            )
//                        }
//                        Surface(
//                            modifier = Modifier
//                                .wrapContentSize()
//                                .padding(start = 20.dp),
//                            shape = RoundedCornerShape(100.dp),
//                            shadowElevation = 6.dp
//                        ) {
//                            Image(
//                                painter = painterResource(id = R.drawable.dummypict),
//                                contentDescription = "Commentator 2 Profile",
//                                modifier = Modifier
//                                    .height(36.dp)
//                                    .width(36.dp),
//                                contentScale = ContentScale.Crop
//                            )
//                        }
//                        Surface(
//                            modifier = Modifier
//                                .wrapContentSize()
//                                .padding(start = 40.dp),
//                            shape = RoundedCornerShape(100.dp),
//                            shadowElevation = 6.dp
//                        ) {
//                            Image(
//                                painter = painterResource(id = R.drawable.dummypict),
//                                contentDescription = "Commentator 3 Profile",
//                                modifier = Modifier
//                                    .height(36.dp)
//                                    .width(36.dp),
//                                contentScale = ContentScale.Crop
//                            )
//                        }
//                    }
                    Text(
                        text = "20+ Comments",
                        fontSize = 14.sp,
                        fontFamily = FontFamily(Font(R.font.opensans_semibold)),
                        modifier = Modifier
                            .padding(start = 8.dp),
                        color = Orange500
                    )
                }
                Button(onClick = { /*TODO*/ },
                    modifier = Modifier,
                    shape = RoundedCornerShape(50.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Orange500)) {
                    Text(
                        text = "View",
                        fontSize = 16.sp,
                        fontFamily = FontFamily(Font(R.font.opensans_semibold)),
                        color = GreyishWhite
                    )
                }
            }
        }
    }
}
//@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DiscussionCard2() {
    Surface(
        shape = RoundedCornerShape(16.dp),
        shadowElevation = 6.dp,
        modifier = Modifier
            .wrapContentSize(align = Alignment.Center)
            .padding(5.dp, 8.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp, 24.dp)
                .width(300.dp)
        ) {
            Row() {
                Surface(
                    modifier = Modifier
                        .wrapContentSize(),
                    shape = RoundedCornerShape(100.dp),
                    shadowElevation = 6.dp
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.dummypict),
                        contentDescription = "Discussion Maker Profile",
                        modifier = Modifier
                            .height(60.dp)
                            .width(60.dp),
                        contentScale = ContentScale.Crop
                    )
                }
                Column(
                    modifier = Modifier
                        .padding(16.dp, 0.dp, 0.dp, 0.dp)
                        .fillMaxWidth()
                ) {
                    Text(
                        text = "Jennie Swift",
                        fontSize = 16.sp,
                        fontFamily = FontFamily(Font(R.font.opensans_bold)),
                        modifier = Modifier,
                        color = Orange500
                    )
                    Text(
                        text = "I Just Lost My Cat",
                        fontSize = 22.sp,
                        fontFamily = FontFamily(Font(R.font.robotoslab_semibold)),
                        modifier = Modifier,
                        color = SoftBlack
                    )
                }
            }
            Surface(
                modifier = Modifier
                    .padding(0.dp, 20.dp),
                shape = RoundedCornerShape(25.dp),
                shadowElevation = 6.dp
            ) {
                Image(
                    painter = painterResource(id = R.drawable.dummypict),
                    contentDescription = "Discussion Picture",
                    modifier = Modifier
                        .height(150.dp)
                        .fillMaxWidth(),
                    contentScale = ContentScale.Crop
                )
            }
            Text(
                text = "Lorem ipsum dolor sit amet, consectetur adipiscing" +
                        " elit, sed do eiusmod tempor incididunt ut " +
                        "labore et dolore magna aliqua.....",
                fontSize = 16.sp,
                fontFamily = FontFamily(Font(R.font.opensans_regular)),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(0.dp, 0.dp, 0.dp, 12.dp)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    modifier = Modifier
                        .wrapContentWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .wrapContentWidth()
                    ) {
                        Surface(
                            modifier = Modifier
                                .wrapContentSize(),
                            shape = RoundedCornerShape(100.dp),
                            shadowElevation = 6.dp
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.dummypict),
                                contentDescription = "Commentator 1 Profile",
                                modifier = Modifier
                                    .height(36.dp)
                                    .width(36.dp),
                                contentScale = ContentScale.Crop
                            )
                        }
                        Surface(
                            modifier = Modifier
                                .wrapContentSize()
                                .padding(start = 20.dp),
                            shape = RoundedCornerShape(100.dp),
                            shadowElevation = 6.dp
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.dummypict),
                                contentDescription = "Commentator 2 Profile",
                                modifier = Modifier
                                    .height(36.dp)
                                    .width(36.dp),
                                contentScale = ContentScale.Crop
                            )
                        }
                        Surface(
                            modifier = Modifier
                                .wrapContentSize()
                                .padding(start = 40.dp),
                            shape = RoundedCornerShape(100.dp),
                            shadowElevation = 6.dp
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.dummypict),
                                contentDescription = "Commentator 3 Profile",
                                modifier = Modifier
                                    .height(36.dp)
                                    .width(36.dp),
                                contentScale = ContentScale.Crop
                            )
                        }
                    }
                    Text(
                        text = "20+ Comments",
                        fontSize = 14.sp,
                        fontFamily = FontFamily(Font(R.font.opensans_semibold)),
                        modifier = Modifier
                            .padding(start = 8.dp),
                        color = Orange500
                    )
                }
                Button(onClick = { /*TODO*/ },
                    modifier = Modifier,
                    shape = RoundedCornerShape(50.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Orange500)) {
                    Text(
                        text = "View",
                        fontSize = 18.sp,
                        fontFamily = FontFamily(Font(R.font.opensans_semibold)),
                        color = GreyishWhite
                    )
                }
            }
        }
    }
}

@Composable
fun CommentCard() {
    Surface(
        shape = RoundedCornerShape(16.dp),
        shadowElevation = 6.dp,
        modifier = Modifier
            .wrapContentSize(align = Alignment.Center)
            .padding(0.dp, 8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .padding(16.dp, 16.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.dummypict),
                contentDescription = "Comment Profile",
                modifier = Modifier
                    .height(60.dp)
                    .width(60.dp)
                    .clip(RoundedCornerShape(50.dp)),
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier
                    .padding(16.dp, 0.dp, 0.dp, 0.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = "User Name",
                    fontSize = 18.sp,
                    fontFamily = FontFamily(Font(R.font.robotoslab_semibold)),
                    modifier = Modifier
                )
                Text(
                    text = "Comment Content",
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(R.font.opensans_regular)),
                    modifier = Modifier
                )
                Text(
                    text = "1 hour ago",
                    fontSize = 14.sp,
                    fontFamily = FontFamily(Font(R.font.opensans_regular)),
                    modifier = Modifier
                        .fillMaxWidth(),
                    textAlign = TextAlign.Right,
                    color = Gray300
                )
            }
        }
    }
}

@Composable
fun IndividualCommunity(community: Community) {
    Surface(
        shape = RoundedCornerShape(16.dp),
        shadowElevation = 6.dp,
        modifier = Modifier
            .wrapContentSize(align = Alignment.Center)
            .width(350.dp)
            .padding(0.dp, 16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .width(500.dp)
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
                Surface(
                    shape = RoundedCornerShape(100.dp)
                ) {
                    Image(
                        painter = rememberAsyncImagePainter(Const.BASE_URL + "/" + community.community_logo),
                        contentDescription = "Community Logo",
                        modifier = Modifier
                            .size(45.dp)
                    )
                }
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
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = community.community_name,
                        textAlign = TextAlign.Start,
                        fontSize = 22.sp,
                        fontFamily = FontFamily(Font(R.font.robotoslab_semibold)),
                        modifier = Modifier
                            .fillMaxWidth(0.55f)
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ownedcommunity),
                            contentDescription = "Members Count",
                            modifier = Modifier
                                .size(30.dp)
                                .padding(5.dp, 0.dp)
                        )
                        Text(
                            text = "50 Members",
                            textAlign = TextAlign.Start,
                            fontSize = 13.sp,
                            fontFamily = FontFamily(Font(R.font.robotoslab_regular)),
                        )
                    }
                }
                Text(
                    text =  if (community.community_description.length > 75) { community.community_description.substring(0, 74) } else { community.community_description },
                    modifier = Modifier
                        .padding(0.dp, 5.dp)
                        .fillMaxHeight(),
                    textAlign = TextAlign.Start,
                    fontSize = 14.sp,
                    fontFamily = FontFamily(Font(R.font.opensans_regular)),
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CommunityMember() {
    Surface(
        modifier = Modifier
            .wrapContentSize(align = Alignment.Center)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Surface(
                modifier = Modifier
                    .padding(0.dp, 10.dp, 0.dp, 3.dp)
                    .wrapContentSize(),
                shape = RoundedCornerShape(20.dp),
                shadowElevation = 6.dp,
            ) {
                Image(
                    painter = painterResource(id = R.drawable.dummypict),
                    contentDescription = "Discussion Maker Profile",
                    modifier = Modifier
                        .height(60.dp)
                        .width(60.dp),
                    contentScale = ContentScale.Crop
                )
            }
            Text(
                text = "John",
                textAlign = TextAlign.Start,
                fontSize = 15.sp,
                fontFamily = FontFamily(Font(R.font.robotoslab_semibold)),
            )
            Text(
                text = "Leader",
                textAlign = TextAlign.Start,
                fontSize = 10.sp,
                fontFamily = FontFamily(Font(R.font.robotoslab_semibold)),
            )
            ClickableText(text = AnnotatedString( text="Delete", SpanStyle(
                    color = Color.Red,
                fontSize = 10.sp,
                fontFamily = FontFamily(Font(R.font.robotoslab_semibold)),
            )),
                onClick = {

                }

            )

        }
    }

}

fun ClickableText(text: AnnotatedString) {

}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CommunityLeader() {
    Surface(
        modifier = Modifier
            .wrapContentSize(align = Alignment.Center)
    ){
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Surface(
                modifier = Modifier

                    .padding(0.dp, 10.dp, 0.dp, 3.dp)
                    .wrapContentSize(),

                shape = RoundedCornerShape(20.dp),
                shadowElevation = 6.dp,
            ) {
                Image(
                    painter = painterResource(id = R.drawable.dummypict),
                    contentDescription = "Discussion Maker Profile",
                    modifier = Modifier
                        .height(60.dp)
                        .width(60.dp),
                    contentScale = ContentScale.Crop
                )
            }
            Text(
                text = "John",
                textAlign = TextAlign.Start,
                fontSize = 15.sp,
                fontFamily = FontFamily(Font(R.font.robotoslab_semibold)),
            )
            Text(
                text = "Leader",
                textAlign = TextAlign.Start,
                fontSize = 10.sp,
                fontFamily = FontFamily(Font(R.font.robotoslab_semibold)),
            )
        }
    }

}