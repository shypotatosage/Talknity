package com.imtuc.talknity.components

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.imtuc.talknity.R
import com.imtuc.talknity.helper.Const
import com.imtuc.talknity.model.*
import com.imtuc.talknity.navigation.Screen
import com.imtuc.talknity.view.ui.theme.*
import com.imtuc.talknity.viewmodel.CommunityViewModel
import com.imtuc.talknity.viewmodel.PostViewModel

@Composable
fun CommunityCategoryCard(category: CommunityCategory, navController: NavHostController) {
    Surface(
        shape = RoundedCornerShape(16.dp),
        shadowElevation = 6.dp,
        modifier = Modifier
            .wrapContentSize(align = Alignment.Center)
            .padding(13.dp, 13.dp)
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
                        )
                    )
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
fun DiscussionCard(post: Post, navController: NavHostController) {
    Surface(
        shape = RoundedCornerShape(16.dp),
        shadowElevation = 6.dp,
        modifier = Modifier
            .wrapContentSize(align = Alignment.Center)
            .padding(0.dp, 16.dp),
        color = Color.White,
        contentColor = Color.Black
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
                        if (post.post_title.trim().length > 32) {
                            post.post_title.substring(0, 31).trim() + "..."
                        } else {
                            post.post_title.trim()
                        },
                        fontSize = 22.sp,
                        fontFamily = FontFamily(Font(R.font.robotoslab_semibold)),
                        modifier = Modifier
                            .padding(0.dp, 0.dp, 4.dp, 0.dp),
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
                        text = post.comment_count + " Comments",
                        fontSize = 14.sp,
                        fontFamily = FontFamily(Font(R.font.opensans_semibold)),
                        modifier = Modifier,
                        color = Orange500
                    )
                }
                Button(
                    onClick = {
                        navController.navigate(
                            Screen.IndividualDiscussion.passParam(
                                post_id = post.post_id
                            )
                        )
                    },
                    modifier = Modifier,
                    shape = RoundedCornerShape(50.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Orange500)
                ) {
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

@Composable
fun OwnedDiscussionCard(post: Post, navController: NavHostController, postViewModel: PostViewModel, lifecycleOwner: LifecycleOwner) {
    val context = LocalContext.current

    postViewModel.postDelete.observe(lifecycleOwner, Observer { response ->
        if (response != "") {
            Toast.makeText(context, response, Toast.LENGTH_SHORT).show()

            if (response == "Discussion Successfully Deleted") {
                postViewModel.resetPostDelete()

                val preferences = context.getSharedPreferences("user", Context.MODE_PRIVATE)

                postViewModel.getOwnedPosts(preferences.getInt("user_id", -1).toString())
            }
        }
    })
    Surface(
        shape = RoundedCornerShape(16.dp),
        shadowElevation = 6.dp,
        modifier = Modifier
            .wrapContentSize(align = Alignment.Center)
            .padding(0.dp, 16.dp),
        color = Color.White,
        contentColor = Color.Black
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
                        if (post.post_title.trim().length > 32) {
                            post.post_title.substring(0, 31).trim() + "..."
                        } else {
                            post.post_title.trim()
                        },
                        fontSize = 22.sp,
                        fontFamily = FontFamily(Font(R.font.robotoslab_semibold)),
                        modifier = Modifier
                            .padding(0.dp, 0.dp, 4.dp, 0.dp),
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
                        text = post.comment_count + " Comments",
                        fontSize = 14.sp,
                        fontFamily = FontFamily(Font(R.font.opensans_semibold)),
                        modifier = Modifier,
                        color = Orange500
                    )
                }
                Row(
                    modifier = Modifier
                        .wrapContentWidth()
                ) {
                    Button(
                        onClick = {
                            navController.navigate(
                                Screen.EditDiscussion.passParam(
                                    post_id = post.post_id
                                )
                            )
                        },
                        modifier = Modifier,
                        shape = RoundedCornerShape(50.dp),
                        colors = ButtonDefaults.buttonColors(backgroundColor = Orange500)
                    ) {
                        Text(
                            text = "Edit",
                            fontSize = 16.sp,
                            fontFamily = FontFamily(Font(R.font.opensans_semibold)),
                            color = GreyishWhite
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(
                        onClick = {
                            postViewModel.deletePost(post.post_id)
                        },
                        modifier = Modifier,
                        shape = RoundedCornerShape(50.dp),
                        colors = ButtonDefaults.buttonColors(backgroundColor = Red500)
                    ) {
                        Text(
                            text = "Delete",
                            fontSize = 16.sp,
                            fontFamily = FontFamily(Font(R.font.opensans_semibold)),
                            color = GreyishWhite
                        )
                    }
                }
            }
        }
    }
}

//@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DiscussionCard2(post: Post, navController: NavHostController) {
    Surface(
        shape = RoundedCornerShape(16.dp),
        shadowElevation = 6.dp,
        modifier = Modifier
            .wrapContentSize(align = Alignment.Center)
            .padding(10.dp, 8.dp)
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
                        if (post.post_title.trim().length > 32) {
                            post.post_title.substring(0, 31).trim() + "..."
                        } else {
                            post.post_title.trim()
                        },
                        fontSize = 22.sp,
                        fontFamily = FontFamily(Font(R.font.robotoslab_semibold)),
                        modifier = Modifier
                            .padding(0.dp, 0.dp, 4.dp, 0.dp),
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
                        text = post.comment_count + " Comments",
                        fontSize = 14.sp,
                        fontFamily = FontFamily(Font(R.font.opensans_semibold)),
                        modifier = Modifier,
                        color = Orange500
                    )
                }
                Button(
                    onClick = {
                        navController.navigate(
                            Screen.IndividualDiscussion.passParam(
                                post_id = post.post_id
                            )
                        )
                    },
                    modifier = Modifier,
                    shape = RoundedCornerShape(50.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Orange500)
                ) {
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

@Composable
fun CommentCard(comment: Comment) {
    Surface(
        shape = RoundedCornerShape(16.dp),
        shadowElevation = 6.dp,
        modifier = Modifier
            .wrapContentSize(align = Alignment.Center)
            .padding(0.dp, 12.dp),
        color = Color.White,
        contentColor = Color.Black
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .padding(16.dp, 16.dp)
        ) {
            Image(
                painter =
                if (comment.user.user_image == "") {
                    painterResource(id = R.drawable.defaultprofilepicture)
                } else {
                    rememberAsyncImagePainter(Const.BASE_URL + "/" + comment.user.user_image)
                },
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
                    text = comment.user.user_displayname,
                    fontSize = 18.sp,
                    fontFamily = FontFamily(Font(R.font.robotoslab_semibold)),
                    modifier = Modifier
                )
                Text(
                    text = comment.content,
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(R.font.opensans_regular)),
                    modifier = Modifier
                )
                Text(
                    text = comment.created_at,
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
fun IndividualCommunity(community: Community, navController: NavHostController) {
    Surface(
        shape = RoundedCornerShape(16.dp),
        shadowElevation = 6.dp,
        modifier = Modifier
            .wrapContentSize(align = Alignment.Center)
            .width(350.dp)
            .padding(0.dp, 10.dp)
    ) {
        Surface(
            modifier = Modifier
                .clickable {
                    navController.navigate(Screen.CommunityDetail.passParam(community.community_id))
                }
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
                                .size(45.dp),
                            contentScale = ContentScale.Crop
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
                                text = community.member_count + " Members",
                                textAlign = TextAlign.Start,
                                fontSize = 13.sp,
                                fontFamily = FontFamily(Font(R.font.robotoslab_regular)),
                            )
                        }
                    }
                    Text(
                        text = if (community.community_description.length > 75) {
                            community.community_description.substring(0, 74)
                        } else {
                            community.community_description
                        },
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
}

@Composable
fun OwnedCommunityCard(community: Community, navController: NavHostController, communityViewModel: CommunityViewModel, lifecycleOwner: LifecycleOwner) {
    val context = LocalContext.current

    communityViewModel.communityDelete.observe(lifecycleOwner, Observer { response ->
        if (response != "") {
            Toast.makeText(context, response, Toast.LENGTH_SHORT).show()

            if (response == "Community Successfully Deleted") {
                communityViewModel.resetPostDelete()

                val preferences = context.getSharedPreferences("user", Context.MODE_PRIVATE)

                communityViewModel.getOwnedCommunities(preferences.getInt("user_id", -1).toString())
            }
        }
    })

    Surface(
        shape = RoundedCornerShape(16.dp),
        shadowElevation = 6.dp,
        modifier = Modifier
            .wrapContentSize(align = Alignment.Center)
            .width(350.dp)
            .padding(0.dp, 10.dp)
    ) {
        Surface(
            modifier = Modifier
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
                                .size(45.dp),
                            contentScale = ContentScale.Crop
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
                                text = community.member_count + " Members",
                                textAlign = TextAlign.Start,
                                fontSize = 13.sp,
                                fontFamily = FontFamily(Font(R.font.robotoslab_regular)),
                            )
                        }
                    }
                    Text(
                        text = if (community.community_description.length > 75) {
                            community.community_description.substring(0, 74)
                        } else {
                            community.community_description
                        },
                        modifier = Modifier
                            .padding(0.dp, 5.dp)
                            .fillMaxHeight(),
                        textAlign = TextAlign.Start,
                        fontSize = 14.sp,
                        fontFamily = FontFamily(Font(R.font.opensans_regular)),
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Button(
                            onClick = {
                                      navController.navigate(
                                          Screen.EditCommunity.passParam(
                                              community.community_id
                                          )
                                      )
                            },
                            modifier = Modifier,
                            shape = RoundedCornerShape(50.dp),
                            colors = ButtonDefaults.buttonColors(backgroundColor = Orange500)
                        ) {
                            Text(
                                text = "Edit",
                                fontSize = 16.sp,
                                fontFamily = FontFamily(Font(R.font.opensans_semibold)),
                                color = GreyishWhite
                            )
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Button(
                            onClick = {
                                      communityViewModel.deleteCommunity(community.community_id)
                            },
                            modifier = Modifier,
                            shape = RoundedCornerShape(50.dp),
                            colors = ButtonDefaults.buttonColors(backgroundColor = Red500)
                        ) {
                            Text(
                                text = "Delete",
                                fontSize = 16.sp,
                                fontFamily = FontFamily(Font(R.font.opensans_semibold)),
                                color = GreyishWhite
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CommunityMember(communityMember: CommunityMember, isLeader: Boolean, communityViewModel: CommunityViewModel, lifecycleOwner: LifecycleOwner) {
    var openDialog = remember {
        mutableStateOf(false)
    }

    val context = LocalContext.current

    communityViewModel.communityMemberRemove.observe(lifecycleOwner, Observer { response ->
        if (response != "") {
            Toast.makeText(context, response, Toast.LENGTH_SHORT).show()

            if (response == "Member Successfully Removed") {
                communityViewModel.resetMemberRemove()
                communityViewModel.getCommunityMembers(communityMember.community_id)
                openDialog.value = false
            }
        }
    })

    if (openDialog.value) {
        AlertDialog(
            modifier = Modifier
                .wrapContentHeight(),
            text = {
                Column(
                    modifier = Modifier
                        .wrapContentHeight()
                        .fillMaxWidth()
                ) {
                    androidx.compose.material.Text(
                        text = "Are You Sure You Want To Remove " + communityMember.user.user_displayname + "?",
                        fontSize = 18.sp,
                        modifier = Modifier
                            .padding(0.dp, 0.dp, 0.dp, 0.dp)
                            .fillMaxWidth(),
                        fontFamily = FontFamily(Font(R.font.opensans_semibold)),
                        textAlign = TextAlign.Center
                    )
                }
            }, buttons = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .offset(y = (-10).dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Button(
                        onClick = {
                            communityViewModel.removeMember(communityMember.id)
                            communityViewModel.getCommunityMembers(communityMember.community_id)
                        },
                        modifier = Modifier
                            .padding(8.dp, 0.dp),
                        shape = RoundedCornerShape(50.dp),
                        colors = ButtonDefaults.buttonColors(backgroundColor = Red500)
                    ) {
                        Text(
                            text = "Remove",
                            fontSize = 18.sp,
                            fontFamily = FontFamily(Font(R.font.opensans_semibold)),
                            color = GreyishWhite
                        )
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                }

            }, onDismissRequest = {
                openDialog.value = false
            },
            shape = RoundedCornerShape(15.dp)
        )
    }

    Surface(
        modifier = Modifier
            .wrapContentSize(align = Alignment.Center)
            .background(color = GreyishWhite)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .background(color = GreyishWhite)
        ) {

            Surface(
                modifier = Modifier
                    .padding(0.dp, 10.dp, 0.dp, 3.dp)
                    .wrapContentSize(),
                shape = RoundedCornerShape(20.dp),
                shadowElevation = 6.dp,
            ) {
                Image(
                    painter =
                    if (communityMember.user.user_image != "") {
                        rememberAsyncImagePainter(Const.BASE_URL + "/" + communityMember.user.user_image)
                    } else {
                        painterResource(id = R.drawable.defaultprofilepicture)
                    },
                    contentDescription = "Member Profile",
                    modifier = Modifier
                        .height(60.dp)
                        .width(60.dp),
                    contentScale = ContentScale.Crop
                )
            }
            Text(
                text = communityMember.user.user_displayname,
                textAlign = TextAlign.Center,
                fontSize = 15.sp,
                fontFamily = FontFamily(Font(R.font.robotoslab_semibold)),
                lineHeight = 15.sp,
                modifier = Modifier
                    .padding(0.dp, 4.dp, 0.dp, 0.dp)
            )
            Text(
                text =
                if (communityMember.id != "0") {
                    "Member"
                } else {
                    "Leader"
                },
                textAlign = TextAlign.Center,
                fontSize = 12.sp,
                fontFamily = FontFamily(Font(R.font.opensans_semibold)),
                modifier = Modifier
                    .padding(0.dp, 2.dp)
            )
            if (isLeader && communityMember.id != "0") {
                Text(
                    text = "Delete",
                    textAlign = TextAlign.Center,
                    fontSize = 12.sp,
                    fontFamily = FontFamily(Font(R.font.opensans_semibold)),
                    color = Red500,
                    modifier = Modifier
                        .clickable {
                            openDialog.value = true
                        }
                )
            }
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
        }
    }

}