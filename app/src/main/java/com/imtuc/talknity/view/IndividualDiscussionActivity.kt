package com.imtuc.talknity.view

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.imtuc.talknity.R
import com.imtuc.talknity.components.CommentCard
import com.imtuc.talknity.helper.Const
import com.imtuc.talknity.model.Comment
import com.imtuc.talknity.view.ui.theme.*
import com.imtuc.talknity.viewmodel.PostViewModel

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
//                    IndividualDiscussion()
                }
            }
        }
    }
}

@Composable
fun IndividualDiscussion(post_id: String, postViewModel: PostViewModel, lifecycleOwner: LifecycleOwner, navController: NavHostController) {
    val context = LocalContext.current

    var post_title = remember {
        mutableStateOf("")
    }

    var post_content = remember {
        mutableStateOf("")
    }

    var post_image = remember {
        mutableStateOf("")
    }

    var user_displayname = remember {
        mutableStateOf("")
    }

    var user_image = remember {
        mutableStateOf("")
    }

    var comments = remember {
        mutableStateListOf<Comment>()
    }

    var discussionLoading = remember {
        mutableStateOf(true)
    }

    postViewModel.getPost(post_id)

    postViewModel.postComment.observe(lifecycleOwner, Observer {
            response ->
        if (postViewModel.postCommentError.value == "Success") {
            post_title.value = response.post_title
            post_content.value = response.post_content
            post_image.value = response.post_image
            user_displayname.value = response.creator.user_displayname
            user_image.value = response.creator.user_image

            comments.clear()
            comments.addAll(response.comments)

            discussionLoading.value = false
        } else if (postViewModel.postCommentError.value?.trim() != null) {
            discussionLoading.value = true
            Toast.makeText(context, postViewModel.postCommentError.value, Toast.LENGTH_SHORT).show()
        }
    })

    var commentContent = remember {
        mutableStateOf("")
    }

    var commentRequired = remember {
        mutableStateOf(false)
    }

    var alertDialog = remember {
        mutableStateOf(false)
    }

    if (alertDialog.value) {
        androidx.compose.material.AlertDialog(
            modifier = Modifier
                .wrapContentHeight(),
            text = {
                Column(
                    modifier = Modifier
                        .wrapContentHeight()
                ) {
                    androidx.compose.material.Text(
                        text = "Add Your Comment",
                        fontSize = 18.sp,
                        modifier = Modifier
                            .padding(0.dp, 0.dp, 0.dp, 12.dp),
                        fontFamily = FontFamily(Font(R.font.opensans_semibold))
                    )
                    Surface(
                        border = BorderStroke(
                            width = 0.5.dp,
                            color = GrayBorder
                        ),
                        shape = RoundedCornerShape(25.dp),
                        shadowElevation = 4.dp,
                        modifier = Modifier
                            .wrapContentHeight()
                    ) {
                        Spacer(modifier = Modifier.height(10.dp))
                        BasicTextField(
                            value = commentContent.value,
                            onValueChange = {
                                commentContent.value = it
                            },
                            enabled = true,
                            singleLine = false,
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentHeight()
                                .border(
                                    BorderStroke(
                                        width = 0.7.dp,
                                        color = GrayBorder
                                    ),
                                    shape = RoundedCornerShape(25.dp)
                                )
                                .background(color = Color.White)
                                .wrapContentHeight(),
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
                                    if (commentContent.value.isEmpty()) {
                                        Text(
                                            text = "Comment",
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
                    if (commentRequired.value && commentContent.value.trim() == "") {
                        Text(
                            text = "You must fill this field before submitting",
                            fontFamily = FontFamily(Font(R.font.opensans_regular)),
                            fontSize = 13.sp,
                            color = Red500,
                            modifier = Modifier
                                .padding(6.dp, 4.dp, 6.dp, 0.dp)
                        )
                    }
                }
            }, buttons = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .offset(y = (-10).dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    androidx.compose.material.Button(
                        onClick = {
                            if (commentContent.value.trim() != "") {
                                commentRequired.value = false
                            } else {
                                commentRequired.value = true
                            }

                            if (!commentRequired.value) {
                                val preferences = context.getSharedPreferences("user", Context.MODE_PRIVATE)

                                postViewModel.createComment(
                                    commentContent.value.trim(),
                                    post_id,
                                    preferences.getInt("user_id", -1).toString()
                                )

                                postViewModel.getPost(post_id)
                                alertDialog.value = false
                            }
                        },
                        modifier = Modifier
                            .padding(8.dp, 0.dp),
                        shape = RoundedCornerShape(50.dp),
                        colors = androidx.compose.material.ButtonDefaults.buttonColors(
                            backgroundColor = Orange500
                        )
                    ) {
                        Text(
                            text = "Submit",
                            fontSize = 18.sp,
                            fontFamily = FontFamily(Font(R.font.opensans_semibold)),
                            color = GreyishWhite
                        )
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                }

            }, onDismissRequest = {
                commentContent.value = ""
                alertDialog.value = false
            },
            shape = RoundedCornerShape(15.dp)
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .scrollable(
                state = rememberScrollState(),
                orientation = Orientation.Vertical,
                enabled = true
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(1) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
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
                                .clickable {
                                    navController.popBackStack()
                                }
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
                    if (discussionLoading.value) {
                        Spacer(modifier = Modifier.height(350.dp))
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center){
                            CircularProgressIndicator(
                                color = SoftBlack
                            )
                        }
                        Spacer(modifier = Modifier.height(350.dp))
                    } else {
                        Spacer(modifier = Modifier.height(20.dp))
                        Surface(
                            modifier = Modifier
                                .wrapContentSize(),
                            shape = RoundedCornerShape(100.dp),
                            shadowElevation = 6.dp
                        ) {
                            Image(
                                painter =
                                if (user_image.value != "") {
                                    rememberAsyncImagePainter(Const.BASE_URL + "/" + user_image.value)
                                } else {
                                    painterResource(id = R.drawable.defaultprofilepicture)
                                },
                                contentDescription = "Profile Image",
                                modifier = Modifier
                                    .height(80.dp)
                                    .width(80.dp),
                                contentScale = ContentScale.Crop
                            )
                        }
                        Text(
                            text = user_displayname.value,
                            fontSize = 16.sp,
                            fontFamily = FontFamily(Font(R.font.opensans_bold)),
                            color = Orange500,
                            modifier = Modifier
                                .fillMaxWidth(0.4f)
                                .padding(0.dp, 12.dp, 0.dp, 8.dp),
                            textAlign = TextAlign.Center
                        )
                        Text(
                            text = post_title.value,
                            fontSize = 24.sp,
                            fontFamily = FontFamily(Font(R.font.robotoslab_semibold)),
                            color = SoftBlack,
                            modifier = Modifier
                                .fillMaxWidth(0.75f),
                            textAlign = TextAlign.Center
                        )
                        if (post_image.value != "") {
                            Surface(
                                modifier = Modifier
                                    .padding(0.dp, 24.dp, 0.dp, 0.dp),
                                shape = RoundedCornerShape(16.dp),
                                shadowElevation = 8.dp
                            ) {
                                Image(
                                    painter = rememberAsyncImagePainter(Const.BASE_URL + "/" + post_image.value),
                                    contentDescription = "Discussion Image",
                                    modifier = Modifier
                                        .height(175.dp)
                                        .fillMaxWidth(0.7f),
                                    contentScale = ContentScale.Crop
                                )
                            }
                        }
                        Text(
                            text = post_content.value,
                            modifier = Modifier
                                .fillMaxWidth(0.8f)
                                .padding(0.dp, 24.dp, 0.dp, 20.dp),
                            fontSize = 16.sp,
                            fontFamily = FontFamily(Font(R.font.opensans_regular))
                        )
                        Button(
                            onClick = {
                                alertDialog.value = true
                            },
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
                }
            }

            itemsIndexed(comments) { index, item ->
                CommentCard(item)
            }
            
            items(1) {
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}