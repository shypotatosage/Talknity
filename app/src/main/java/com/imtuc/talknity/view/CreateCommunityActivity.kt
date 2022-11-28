package com.imtuc.talknity.view

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.fontResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.insets.navigationBarsWithImePadding
import com.google.accompanist.insets.statusBarsPadding
import com.imtuc.talknity.R
import com.imtuc.talknity.model.CommunityCategory
import com.imtuc.talknity.view.ui.theme.*

class CreateCommunityActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TalknityTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    CreateCommunity()
                }
            }
        }
    }
}

@Composable
fun CreateCommunity() {
    var selectedCategory = remember {
        mutableStateOf("")
    }

    var communityName = remember {
        mutableStateOf("")
    }

    var communityDescription = remember {
        mutableStateOf("")
    }

    var contactInformation = remember {
        mutableStateOf("")
    }

    var expanded by remember {
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

    var categoryList = arrayListOf<CommunityCategory>(CommunityCategory("1", "All Categories"), CommunityCategory("2", "Art"))


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
            Row(
                modifier = Modifier
                    .padding(0.dp, 20.dp, 0.dp, 0.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Create A ",
                    fontFamily = FontFamily(Font(R.font.robotoslab_bold)),
                    fontSize = 30.sp,
                    color = SoftBlack
                )
                Text(
                    text = "Community",
                    fontFamily = FontFamily(Font(R.font.robotoslab_bold)),
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
                            .size(160.dp, 160.dp)
                            .background(color = Color.White)
                            .align(alignment = Alignment.CenterVertically)
                            .border(width = 0.6.dp, color = GrayBorder, shape = RoundedCornerShape(16.dp)),
                        shape = RoundedCornerShape(16.dp),
                        color = Color.White,
                        shadowElevation = 5.dp
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.addimageicon),
                                contentDescription = "Add Image",
                                modifier = Modifier
                                    .height(20.dp)
                            )
                            Text(
                                text = "Upload Community's Logo",
                                fontFamily = FontFamily(Font(R.font.opensans_regular)),
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
                            .align(alignment = Alignment.CenterVertically),
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
                    text = "Community's Name",
                    fontFamily = FontFamily(Font(R.font.robotoslab_bold)),
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
                        value = communityName.value,
                        onValueChange = {
                            communityName.value = it
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
                                if (communityName.value.isEmpty()) {
                                    Text(
                                        text = "Community's Name",
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
                Column {
                    Text(
                        text = "Category",
                        fontFamily = FontFamily(Font(R.font.robotoslab_bold)),
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
                            value = selectedCategory.value,
                            onValueChange = {
                                selectedCategory.value = it
                            },
                            enabled = false,
                            singleLine = true,
                            modifier = Modifier
                                .fillMaxWidth()
                                .border(
                                    BorderStroke(
                                        width = 0.7.dp,
                                        color = GrayBorder
                                    ),
                                    shape = RoundedCornerShape(25.dp)
                                ),
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
                                        .fillMaxWidth()
                                        .fillMaxHeight()
                                        .align(alignment = Alignment.CenterHorizontally),
                                    contentAlignment = Alignment.Center
                                ) {
                                    if (selectedCategory.value.isEmpty()) {
                                        Row(
                                            horizontalArrangement = Arrangement.SpaceBetween,
                                            modifier = Modifier.fillMaxWidth(),
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Text(
                                                text = "Select Category",
                                                color = Gray300,
                                                fontSize = 16.sp,
                                                fontFamily = FontFamily(Font(R.font.opensans_regular))
                                            )
                                            Image(
                                                painter = painterResource(id = R.drawable.dropdown),
                                                contentDescription = "Dropdown",
                                                modifier = Modifier
                                                    .height(12.dp)
                                                    .clickable {
                                                        expanded = !expanded
                                                    }
                                            )
                                        }
                                        innerTextField()
                                    } else {
                                        Row(
                                            horizontalArrangement = Arrangement.SpaceBetween,
                                            modifier = Modifier.fillMaxSize(),
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            innerTextField()
                                            Image(
                                                painter = painterResource(id = R.drawable.dropdown),
                                                contentDescription = "Dropdown",
                                                modifier = Modifier
                                                    .width(24.dp)
                                                    .clickable {
                                                        expanded = !expanded
                                                    }
                                            )
                                        }
                                    }
                                }
                            },
                            textStyle = TextStyle(
                                fontFamily = FontFamily(Font(R.font.opensans_regular)),
                                fontSize = 16.sp
                            )
                        )
                    }
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                        modifier = Modifier
                            .background(color = GreyishWhite)
                            .fillMaxWidth(0.875f)
                            .align(alignment = Alignment.CenterHorizontally)
                    ) {
                        categoryList.forEach { category ->
                            DropdownMenuItem(onClick = {
                                selectedCategory.value = category.name.toString()
                                expanded = false
                            }) {
                                Text(text = category.name, modifier = Modifier.fillMaxWidth())
                            }
                        }
                    }
                }
                Text(
                    text = "Description",
                    fontFamily = FontFamily(Font(R.font.robotoslab_bold)),
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
                        value = communityDescription.value,
                        onValueChange = {
                            communityDescription.value = it
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
                                if (communityDescription.value.isEmpty()) {
                                    Text(
                                        text = "Description",
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
                    text = "Contact Information",
                    fontFamily = FontFamily(Font(R.font.robotoslab_bold)),
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
                        value = contactInformation.value,
                        onValueChange = {
                            contactInformation.value = it
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
                                if (contactInformation.value.isEmpty()) {
                                    Text(
                                        text = "Contact Information",
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
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(0.dp, 24.dp, 0.dp, 0.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.createcommunityimg),
                        contentDescription = "Create Community Image",
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
                                text = "Create Community",
                                fontSize = 20.sp,
                                fontFamily = FontFamily(Font(R.font.opensans_bold)),
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
fun CreateCommunityPreview() {
    TalknityTheme {
        CreateCommunity()
    }
}