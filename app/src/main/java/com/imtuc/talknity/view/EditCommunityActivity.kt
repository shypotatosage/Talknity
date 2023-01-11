package com.imtuc.talknity.view

import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.insets.navigationBarsWithImePadding
import com.google.accompanist.insets.statusBarsPadding
import com.imtuc.talknity.R
import com.imtuc.talknity.helper.Const
import com.imtuc.talknity.model.CommunityCategory
import com.imtuc.talknity.view.ui.theme.*
import com.imtuc.talknity.viewmodel.CommunityViewModel
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody

@Composable
fun EditCommunity(
    community_id: String,
    communityViewModel: CommunityViewModel,
    lifecycleOwner: LifecycleOwner,
    navController: NavHostController
) {
    var selectedCategory = remember {
        mutableStateOf("")
    }

    var selectedCategoryName = remember {
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

    var communityLogo = remember {
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

    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri: Uri? ->
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

    var categories = remember {
        mutableStateListOf<CommunityCategory>()
    }

    communityViewModel.getCategory()

    communityViewModel.category.observe(lifecycleOwner, Observer { response ->
        if (response != null) {
            categories.clear()
            categories.addAll(response)
        }
    })

    communityViewModel.getCommunityUpdate(community_id)

    communityViewModel.getCommunityUpdateError.observe(lifecycleOwner, Observer { response ->
        if (response != "") {
            if (response == "Success") {
                communityName.value = communityViewModel.getCommunityUpdate.value!!.community_name
                communityDescription.value =
                    communityViewModel.getCommunityUpdate.value!!.community_description
                contactInformation.value =
                    communityViewModel.getCommunityUpdate.value!!.community_contact
                selectedCategory.value =
                    communityViewModel.getCommunityUpdate.value!!.community_category.category_id
                selectedCategoryName.value =
                    communityViewModel.getCommunityUpdate.value!!.community_category.category_name
                communityLogo.value = communityViewModel.getCommunityUpdate.value!!.community_logo
            } else {
                Toast.makeText(context, response, Toast.LENGTH_SHORT).show()
            }
        }
    })

    communityViewModel.communityUpdate.observe(lifecycleOwner, Observer { response ->
        if (response != "") {
            Toast.makeText(context, response, Toast.LENGTH_SHORT).show()

            if (response == "Community Successfully Updated") {
                communityViewModel.resetCommunityUpdate()
                navController.popBackStack()
            }
        }
    })

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
                ) {
                    Surface(
                        modifier = Modifier
                            .size(160.dp, 160.dp)
                            .background(color = GreyishWhite)
                            .align(alignment = Alignment.CenterVertically)
                            .clickable {
                                launcher.launch("image/*")
                            },
                        shape = RoundedCornerShape(16.dp),
                        color = GreyishWhite
                    ) {
                        Image(
                            painter = rememberAsyncImagePainter(Const.BASE_URL + "/" + communityLogo.value),
                            contentDescription = "Add Image",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }
            } else {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(0.dp, 24.dp, 0.dp, 0.dp)
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
                            modifier = Modifier
                                .fillMaxSize()
                                .clickable {
                                    launcher.launch("image/*")
                                }
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
                        maxLines = 1,
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(color = Color.White)
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
                if ((communityName.value.trim()
                        .isEmpty() || communityName.value.trim().length > 100)
                ) {
                    Text(
                        text =
                        if (communityName.value.trim().length <= 100) {
                            "Community Name is required"
                        } else {
                            "Max. 100 characters"
                        },
                        fontFamily = FontFamily(Font(R.font.opensans_regular)),
                        fontSize = 13.sp,
                        color = Red500,
                        modifier = Modifier
                            .padding(6.dp, 4.dp, 6.dp, 0.dp)
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
                            value = selectedCategoryName.value,
                            onValueChange = {},
                            enabled = false,
                            singleLine = true,
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(color = Color.White)
                                .border(
                                    BorderStroke(
                                        width = 0.7.dp,
                                        color = GrayBorder
                                    ),
                                    shape = RoundedCornerShape(25.dp)
                                )
                                .clickable {
                                    expanded = !expanded
                                },
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
                            .background(color = Color.White)
                            .fillMaxWidth(0.875f)
                            .align(alignment = Alignment.CenterHorizontally)
                    ) {
                        categories.forEach { category ->
                            if (category.category_id != "0") {
                                DropdownMenuItem(onClick = {
                                    selectedCategory.value = category.category_id
                                    selectedCategoryName.value = category.category_name
                                    expanded = false
                                }) {
                                    Text(
                                        text = category.category_name,
                                        modifier = Modifier.fillMaxWidth()
                                    )
                                }
                            }
                        }
                    }
                }
                if (selectedCategory.value.isEmpty()) {
                    Text(
                        text = "You must select a Category",
                        fontFamily = FontFamily(Font(R.font.opensans_regular)),
                        fontSize = 13.sp,
                        color = Red500,
                        modifier = Modifier
                            .padding(6.dp, 4.dp, 6.dp, 0.dp)
                    )
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
                            .background(color = Color.White)
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
                if ((communityDescription.value.trim().length < 25)) {
                    Text(
                        text =
                        if (communityDescription.value.trim().isEmpty()) {
                            "Community Description is required"
                        } else {
                            "Min. 25 characters"
                        },
                        fontFamily = FontFamily(Font(R.font.opensans_regular)),
                        fontSize = 13.sp,
                        color = Red500,
                        modifier = Modifier
                            .padding(6.dp, 4.dp, 6.dp, 0.dp)
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
                            .background(color = Color.White)
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
                if (contactInformation.value.trim().isEmpty()) {
                    Text(
                        text = "Contact Information is required",
                        fontFamily = FontFamily(Font(R.font.opensans_regular)),
                        fontSize = 13.sp,
                        color = Red500,
                        modifier = Modifier
                            .padding(6.dp, 4.dp, 6.dp, 0.dp)
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

                    Row(
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(0.dp, 6.dp, 0.dp, 0.dp)
                    ) {
                        Button(
                            onClick = {
                                if (!(contactInformation.value.trim().isEmpty() || (communityName.value.trim().isEmpty() || communityName.value.trim().length > 100) || selectedCategory.value.isEmpty() || (communityDescription.value.trim().length < 25))) {
                                    var community_logo: MultipartBody.Part?

                                    if (bitmap.value != null) {
                                        community_logo =
                                            prepareFilePart("community_logo", bitmap.value!!)
                                    } else {
                                        community_logo = null
                                    }

                                    communityViewModel.updateCommunity(
                                        community_logo = community_logo,
                                        community_name = communityName.value.trim()
                                            .toRequestBody(),
                                        community_description = communityDescription.value.trim()
                                            .toRequestBody(),
                                        category_id = selectedCategory.value.trim()
                                            .toRequestBody(),
                                        community_contact = contactInformation.value.trim()
                                            .toRequestBody(),
                                        community_id = community_id.toRequestBody()
                                    )
                                }
                            },
                            modifier = Modifier
                                .padding(8.dp, 0.dp),
                            shape = RoundedCornerShape(50.dp),
                            colors = ButtonDefaults.buttonColors(backgroundColor = Orange500)
                        ) {
                            Text(
                                text = "Update Community",
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