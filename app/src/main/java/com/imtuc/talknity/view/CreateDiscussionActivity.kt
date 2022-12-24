package com.imtuc.talknity.view

import android.app.Activity
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
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
import com.imtuc.talknity.view.ui.theme.*

class CreateDiscussionActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TalknityTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    CreateDiscussion()
                }
            }
        }
    }
}

@Composable
fun CreateDiscussion() {

    var postTitle = remember {
        mutableStateOf("")
    }

    var postContent = remember {
        mutableStateOf("")
    }

    var anonymousChecked = remember {
        mutableStateOf(false)
    }

    var imageUri by remember {
        mutableStateOf<Uri?>(null)
    }

    val context = LocalContext.current

    val bitmap = remember {
        mutableStateOf<Bitmap?>(null)
    }

    val launcher = rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri: Uri? ->
        imageUri = uri
    }

    imageUri?.let {
        if (Build.VERSION.SDK_INT < 28) {
            bitmap.value = MediaStore.Images
                .Media.getBitmap(context.contentResolver, it)
        } else {
            val source = ImageDecoder.createSource(context.contentResolver, it)
            bitmap.value = ImageDecoder.decodeBitmap(source)
        }
    }

    ProvideWindowInsets(windowInsetsAnimationsEnabled = true) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .navigationBarsWithImePadding()
                .verticalScroll(rememberScrollState())
        ) {
            Row(
                modifier = Modifier
                    .padding(20.dp, 24.dp, 0.dp, 0.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = com.imtuc.talknity.R.drawable.arrow_back),
                    contentDescription = "Back",
                    modifier = Modifier
                        .height(28.dp)
                )
                Text(
                    text = "Back",
                    fontFamily = FontFamily(Font(com.imtuc.talknity.R.font.robotoslab_regular)),
                    modifier = Modifier
                        .padding(20.dp, 0.dp, 0.dp, 0.dp),
                    fontSize = 24.sp,
                    color = SoftBlack
                )
            }
            Row(
                modifier = Modifier
                    .padding(0.dp, 20.dp, 0.dp, 0.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Create A ",
                    fontFamily = FontFamily(Font(com.imtuc.talknity.R.font.robotoslab_bold)),
                    fontSize = 30.sp,
                    color = SoftBlack
                )
                Text(
                    text = "Discussion",
                    fontFamily = FontFamily(Font(com.imtuc.talknity.R.font.robotoslab_bold)),
                    fontSize = 30.sp,
                    color = Orange500
                )
            }

            if (bitmap.value == null) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(0.dp, 24.dp, 0.dp, 0.dp)
                        .clickable {
                            launcher.launch("image/*")
                        }
                ) {
                    Surface(
                        modifier = Modifier
                            .size(200.dp, 160.dp)
                            .background(color = Color.White)
                            .align(alignment = Alignment.CenterVertically)
                            .border(
                                width = 0.6.dp,
                                color = GrayBorder,
                                shape = RoundedCornerShape(16.dp)
                            ),
                        shape = RoundedCornerShape(16.dp),
                        color = GreyishWhite,
                        shadowElevation = 5.dp
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(color = Color.White),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Image(
                                painter = painterResource(id = com.imtuc.talknity.R.drawable.addimageicon),
                                contentDescription = "Add Image",
                                modifier = Modifier
                                    .height(20.dp)
                            )
                            Text(
                                text = "Add An Image",
                                fontFamily = FontFamily(Font(com.imtuc.talknity.R.font.opensans_regular)),
                                color = Gray300,
                                fontSize = 16.sp,
                                textAlign = TextAlign.Center,
                                modifier = Modifier
                                    .padding(8.dp, 8.dp, 8.dp, 0.dp)
                            )
                        }
                    }
                }
            } else {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(0.dp, 24.dp, 0.dp, 0.dp)
                        .clickable {
                            launcher.launch("image/*")
                        }
                ) {
                    Surface(
                        modifier = Modifier
                            .size(160.dp, 160.dp)
                            .background(color = GreyishWhite)
                            .align(alignment = Alignment.CenterVertically)
                            .border(
                                width = 0.6.dp,
                                color = GrayBorder,
                                shape = RoundedCornerShape(16.dp)
                            ),
                        shape = RoundedCornerShape(16.dp),
                        color = GreyishWhite
                    ) {
                        Image(
                            bitmap = bitmap.value!!.asImageBitmap(),
                            contentDescription = "Add Image",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }
            }

            Column(
                modifier = Modifier
                    .padding(24.dp, 0.dp)
            ) {
                Text(
                    text = "Title",
                    fontFamily = FontFamily(Font(com.imtuc.talknity.R.font.robotoslab_bold)),
                    fontSize = 24.sp,
                    color = SoftBlack,
                    modifier = Modifier
                        .padding(0.dp, 24.dp, 0.dp, 10.dp)
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
                        value = postTitle.value,
                        onValueChange = {
                            postTitle.value = it
                        },
                        enabled = true,
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(
                                BorderStroke(
                                    width = 0.7.dp,
                                    color = GrayBorder
                                ),
                                shape = RoundedCornerShape(25.dp)
                            )
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
                                if (postTitle.value.isEmpty()) {
                                    Text(
                                        text = "Title",
                                        color = Gray300,
                                        fontSize = 16.sp,
                                        fontFamily = FontFamily(Font(com.imtuc.talknity.R.font.opensans_regular))
                                    )
                                }
                                innerTextField()  //<-- Add this
                            }
                        },
                        textStyle = TextStyle(
                            fontFamily = FontFamily(Font(com.imtuc.talknity.R.font.opensans_regular)),
                            fontSize = 16.sp
                        )
                    )
                }
                Text(
                    text = "Content",
                    fontFamily = FontFamily(Font(com.imtuc.talknity.R.font.robotoslab_bold)),
                    fontSize = 24.sp,
                    color = SoftBlack,
                    modifier = Modifier
                        .padding(0.dp, 15.dp, 0.dp, 10.dp)
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
                        value = postContent.value,
                        onValueChange = {
                            postContent.value = it
                        },
                        enabled = true,
                        singleLine = false,
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(
                                BorderStroke(
                                    width = 0.7.dp,
                                    color = GrayBorder
                                ),
                                shape = RoundedCornerShape(25.dp)
                            )
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
                                if (postContent.value.isEmpty()) {
                                    Text(
                                        text = "Content",
                                        color = Gray300,
                                        fontSize = 16.sp,
                                        fontFamily = FontFamily(Font(com.imtuc.talknity.R.font.opensans_regular))
                                    )
                                }
                                innerTextField()  //<-- Add this
                            }
                        },
                        textStyle = TextStyle(
                            fontFamily = FontFamily(Font(com.imtuc.talknity.R.font.opensans_regular)),
                            fontSize = 16.sp
                        )
                    )
                }
                Row(
                    modifier = Modifier
                        .padding(0.dp, 24.dp, 0.dp, 0.dp)
                ) {
                    Card(
                        modifier = Modifier.background(Color.White),
                        elevation = 0.dp,
                        shape = RoundedCornerShape(6.dp),
                        border = BorderStroke(1.25.dp, color = if (anonymousChecked.value) Orange500 else  Gray300)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(25.dp)
                                .background(if (anonymousChecked.value) Orange500 else Color.White)
                                .clickable {
                                    anonymousChecked.value = !anonymousChecked.value
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            if(anonymousChecked.value)
                                Icon(Icons.Default.Check, contentDescription = "", tint = Color.White)
                        }
                    }

                    Text(
                        modifier = Modifier
                            .align(alignment = Alignment.CenterVertically)
                            .padding(start = 10.dp),
                        text = "Post Anonymously",
                        fontSize = 16.sp,
                        color = SoftBlack
                    )
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(0.dp, 24.dp, 0.dp, 0.dp)
                ) {
                    Image(
                        painter = painterResource(id = com.imtuc.talknity.R.drawable.createcommunityimg),
                        contentDescription = "Create Post Image",
                        modifier = Modifier
                            .padding(0.dp, 48.dp, 0.dp, 0.dp)
                    )

                    Row(horizontalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding()) {
                        Button(onClick = { /*TODO*/ },
                            modifier = Modifier
                                .padding(8.dp, 0.dp),
                            shape = RoundedCornerShape(50.dp),
                            colors = ButtonDefaults.buttonColors(backgroundColor = Orange500)) {
                            Text(
                                text = "Post Discussion",
                                fontSize = 20.sp,
                                fontFamily = FontFamily(Font(com.imtuc.talknity.R.font.opensans_bold)),
                                color = GreyishWhite
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CreatePostPreview() {
    TalknityTheme {
        CreateDiscussion()
    }
}