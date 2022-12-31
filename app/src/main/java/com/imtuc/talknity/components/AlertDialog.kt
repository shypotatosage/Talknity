package com.imtuc.talknity.components

import android.content.Context
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.imtuc.talknity.R
import com.imtuc.talknity.view.prepareFilePart
import com.imtuc.talknity.view.ui.theme.*
import com.imtuc.talknity.viewmodel.PostViewModel
import okhttp3.RequestBody.Companion.toRequestBody

@Composable
fun CommentDialog(postViewModel: PostViewModel, post_id: String, user_id: String) {
    var commentContent = remember {
        mutableStateOf("")
    }

    var commentRequired = remember {
        mutableStateOf(false)
    }

    AlertDialog(
        modifier = Modifier
            .wrapContentHeight(),
        text = {
            Column(
                modifier = Modifier
                    .wrapContentHeight()
            ) {
                Text(
                    text = "Add Your Comment",
                    fontSize = 18.sp,
                    modifier = Modifier
                        .padding(0.dp, 0.dp, 0.dp, 12.dp),
                    fontFamily = FontFamily(Font(R.font.opensans_semibold))
                )
                androidx.compose.material3.Surface(
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
                                    androidx.compose.material3.Text(
                                        text = "Comment Content",
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
        }, buttons = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .offset(y = (-10).dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(onClick = {
                    if (commentContent.value.trim() != "") {
                        commentRequired.value = false
                    } else {
                        commentRequired.value = true
                    }

                    if (!commentRequired.value) {
                        postViewModel.createComment(commentContent.value.trim(), post_id, user_id)
                    }
                },
                    modifier = Modifier
                        .padding(8.dp, 0.dp),
                    shape = RoundedCornerShape(50.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Orange500)) {
                    androidx.compose.material3.Text(
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
        },
        shape = RoundedCornerShape(15.dp)
    )
}