package com.imtuc.talknity.view

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.insets.navigationBarsWithImePadding
import com.google.accompanist.insets.statusBarsPadding
import com.imtuc.talknity.R
import com.imtuc.talknity.helper.Const
import com.imtuc.talknity.navigation.Screen
import com.imtuc.talknity.view.ui.theme.*
import com.imtuc.talknity.viewmodel.AuthViewModel
import okhttp3.RequestBody.Companion.toRequestBody
import java.util.regex.Pattern

class EditProfileActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TalknityTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
//                    EditProfile()
                }
            }
        }
    }
}

@Composable
fun EditProfile(authViewModel: AuthViewModel, user_displayname: String, user_username: String, user_email: String, user_image: String, navController: NavHostController, lifecycleOwner: LifecycleOwner) {
    var displayName = remember {
        mutableStateOf(user_displayname)
    }

    var username = remember {
        mutableStateOf(user_username)
    }

    var email = remember {
        mutableStateOf(user_email)
    }

    var profileImage = remember {
        mutableStateOf(user_image)
    }

    var passwordRequired = remember {
        mutableStateOf(false)
    }

    var passwordNotMatch = remember {
        mutableStateOf(false)
    }

    var passwordVisibility = remember {
        mutableStateOf(false)
    }

    var confirmPassword = remember {
        mutableStateOf("")
    }

    val image = if (passwordVisibility.value) {
        Icons.Filled.Visibility
    } else {
        Icons.Filled.VisibilityOff
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

    val launcherPermission = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            // Permission Accepted: Do something
            Log.d("ExampleScreen","PERMISSION GRANTED")
        } else {
            // Permission Denied: Do something
            Log.d("ExampleScreen","PERMISSION DENIED")
        }
    }

    authViewModel.profileUpdate.observe(lifecycleOwner, Observer { response ->
        if (response == "Profile Successfully Updated") {
            authViewModel.resetProfileUpdate()
            navController.popBackStack()
        } else if (response != "") {
            if (response == "Password does not match!") {
                passwordNotMatch.value = true
            } else {
                Toast.makeText(context, "Failed To Update Profile\n" + response, Toast.LENGTH_SHORT)
                    .show()
            }
        }
    })

    ProvideWindowInsets(windowInsetsAnimationsEnabled = true) {
        Column(
            modifier = Modifier
                .paint(
                    painter = painterResource(id = R.drawable.editprofilebgtheme),
                    contentScale = ContentScale.Crop
                )
                .fillMaxSize()
                .statusBarsPadding()
                .navigationBarsWithImePadding()
                .verticalScroll(rememberScrollState())
        ) {
            if (bitmap.value == null) {
                Image(
                    painter =
                    if (profileImage.value == "empty") {
                        painterResource(id = R.drawable.defaultprofilepicture)
                    } else {
                           rememberAsyncImagePainter(Const.BASE_URL + "/images/user/" + profileImage.value)
                           },
                    contentDescription = "Profile Picture",
                    modifier = Modifier
                        .height(210.dp)
                        .fillMaxWidth()
                        .clickable {
                            when (PackageManager.PERMISSION_GRANTED) {
                                ContextCompat.checkSelfPermission(
                                    context,
                                    Manifest.permission.READ_EXTERNAL_STORAGE
                                ) -> {
                                    Log.d("ExampleScreen", "Code requires permission")
                                }
                                else -> {
                                    // Asking for permission
                                    launcherPermission.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
                                }
                            }

                            when (PackageManager.PERMISSION_GRANTED) {
                                ContextCompat.checkSelfPermission(
                                    context,
                                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                                ) -> {
                                    // Some works that require permission
                                    launcher.launch("image/*")
                                    Log.d("ExampleScreen", "Code requires permission")
                                }
                                else -> {
                                    // Asking for permission
                                    launcherPermission.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                }
                            }
                        },
                    contentScale = ContentScale.Crop
                )
            } else {
                Image(
                    bitmap = bitmap.value!!.asImageBitmap(),
                    contentDescription = "Profile Picture",
                    modifier = Modifier
                        .height(210.dp)
                        .fillMaxWidth()
                        .clickable {
                            when (PackageManager.PERMISSION_GRANTED) {
                                ContextCompat.checkSelfPermission(
                                    context,
                                    Manifest.permission.READ_EXTERNAL_STORAGE
                                ) -> {
                                    Log.d("ExampleScreen", "Code requires permission")
                                }
                                else -> {
                                    // Asking for permission
                                    launcherPermission.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
                                }
                            }

                            when (PackageManager.PERMISSION_GRANTED) {
                                ContextCompat.checkSelfPermission(
                                    context,
                                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                                ) -> {
                                    // Some works that require permission
                                    launcher.launch("image/*")
                                    Log.d("ExampleScreen", "Code requires permission")
                                }
                                else -> {
                                    // Asking for permission
                                    launcherPermission.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                }
                            }
                        },
                    contentScale = ContentScale.Crop
                )
            }
//            Row(
//                modifier = Modifier
//                    .padding(0.dp, 28.dp, 0.dp, 28.dp)
//                    .fillMaxWidth(),
//                verticalAlignment = Alignment.CenterVertically,
//                horizontalArrangement = Arrangement.Center
//            ) {
//                Text(
//                    text = "Edit Your ",
//                    fontFamily = FontFamily(Font(R.font.robotoslab_bold)),
//                    fontSize = 30.sp,
//                    color = SoftBlack
//                )
//                Text(
//                    text = "Profile",
//                    fontFamily = FontFamily(Font(R.font.robotoslab_bold)),
//                    fontSize = 30.sp,
//                    color = Orange500
//                )
//            }
            Column(
                modifier = Modifier
                    .padding(24.dp, 0.dp)
            ) {
                Text(
                    text = "Display Name",
                    fontFamily = FontFamily(Font(R.font.robotoslab_bold)),
                    fontSize = 24.sp,
                    color = SoftBlack,
                    modifier = Modifier
                        .padding(0.dp, 32.dp, 0.dp, 10.dp)
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
                        value = displayName.value,
                        onValueChange = {
                            displayName.value = it
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
                                if (displayName.value.isEmpty()) {
                                    Text(
                                        text = "Display Name",
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
                if (displayName.value.trim().isEmpty()) {
                    Text(
                        text = "Display Name is required",
                        fontFamily = FontFamily(Font(R.font.opensans_regular)),
                        fontSize = 13.sp,
                        color = Red500,
                        modifier = Modifier
                            .padding(6.dp, 4.dp, 6.dp, 0.dp)
                    )
                }
                Text(
                    text = "Username",
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
                        value = username.value,
                        onValueChange = {
                            username.value = it
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
                                if (username.value.isEmpty()) {
                                    Text(
                                        text = "Username",
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
                if (!Pattern.compile("^(?=.{8,20}\$)(?![_.])(?!.*[_.]{2})[a-zA-Z0-9._]+(?<![_.])\$")
                        .matcher(username.value.trim()).matches()) {
                    Text(
                        text = "Must be 8-20 characters, only . and _ allowed but may not end/start with . or _",
                        fontFamily = FontFamily(Font(R.font.opensans_regular)),
                        fontSize = 13.sp,
                        color = Red500,
                        modifier = Modifier
                            .padding(6.dp, 4.dp, 6.dp, 0.dp)
                    )
                }
                Text(
                    text = "Email",
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
                        value = email.value,
                        onValueChange = {
                            email.value = it
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
                                if (email.value.isEmpty()) {
                                    Text(
                                        text = "Email",
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
                if (!Patterns.EMAIL_ADDRESS.matcher(email.value.trim())
                        .matches()
                ) {
                    Text(
                        text = "Email is not valid",
                        fontFamily = FontFamily(Font(R.font.opensans_regular)),
                        fontSize = 13.sp,
                        color = Red500,
                        modifier = Modifier
                            .padding(6.dp, 4.dp, 6.dp, 0.dp)
                    )
                }
                Text(
                    text = "Confirm Password",
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
                        value = confirmPassword.value,
                        onValueChange = {
                            confirmPassword.value = it
                        },
                        visualTransformation = if (passwordVisibility.value) VisualTransformation.None else PasswordVisualTransformation(),
                        enabled = true,
                        singleLine = true,
                        maxLines = 1,
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(
                                BorderStroke(
                                    width = 0.7.dp,
                                    color = Gray300
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
                                textColor = SoftBlack,
                            )
                            Box(
                                modifier = Modifier
                                    .padding(16.dp, 0.dp),
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(0.dp, 0.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    if (confirmPassword.value.isEmpty()) {
                                        Text(
                                            text = "Password",
                                            color = Gray300,
                                            fontSize = 16.sp,
                                            fontFamily = FontFamily(Font(R.font.opensans_regular))
                                        )
                                        IconButton(onClick = {
                                            passwordVisibility.value = !passwordVisibility.value
                                        }) {
                                            Icon(imageVector = image, "")
                                        }
                                    } else {
                                        Row(
                                            horizontalArrangement = Arrangement.SpaceBetween,
                                            modifier = Modifier.fillMaxSize(),
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            innerTextField()
                                            IconButton(onClick = {
                                                passwordVisibility.value = !passwordVisibility.value
                                            }) {
                                                Icon(imageVector = image, "")
                                            }
                                        }
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
                if (passwordRequired.value) {
                    Text(
                        text = "You must confirm your password",
                        fontFamily = FontFamily(Font(R.font.opensans_regular)),
                        fontSize = 13.sp,
                        color = Red500,
                        modifier = Modifier
                            .padding(6.dp, 4.dp, 6.dp, 0.dp)
                    )
                }
                if (passwordNotMatch.value) {
                    Text(
                        text = "Password does not match",
                        fontFamily = FontFamily(Font(R.font.opensans_regular)),
                        fontSize = 13.sp,
                        color = Red500,
                        modifier = Modifier
                            .padding(6.dp, 4.dp, 6.dp, 0.dp)
                    )
                }
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(0.dp, 32.dp, 0.dp, 60.dp)) {
                    Button(onClick = {
                            navController.popBackStack()
                        },
                        modifier = Modifier
                            .padding(8.dp, 0.dp),
                        shape = RoundedCornerShape(50.dp),
                        colors = ButtonDefaults.buttonColors(backgroundColor = Red500)) {
                        Text(
                            text = "Cancel",
                            fontSize = 20.sp,
                            fontFamily = FontFamily(Font(R.font.opensans_bold)),
                            color = GreyishWhite
                        )
                    }
                    Spacer(modifier = Modifier.width(4.dp))
                    Button(onClick = {
                            if (confirmPassword.value == "") {
                                passwordRequired.value = true
                            } else {
                                passwordRequired.value = false
                            }

                            if (!(passwordRequired.value || displayName.value.trim().isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email.value.trim()).matches() || !Pattern.compile("^(?=.{8,20}\$)(?![_.])(?!.*[_.]{2})[a-zA-Z0-9._]+(?<![_.])\$")
                                    .matcher(username.value.trim()).matches())) {
                                val preferences = context.getSharedPreferences("user", Context.MODE_PRIVATE)

                                if (bitmap.value == null) {
                                    authViewModel.updateProfile(
                                        user_image = null,
                                        user_username = username.value.toRequestBody(),
                                        user_displayname = displayName.value.toRequestBody(),
                                        user_email = email.value.toRequestBody(),
                                        user_password = confirmPassword.value.toRequestBody(),
                                        user_id = preferences.getInt("user_id", -1).toString()
                                            .toRequestBody()
                                    )
                                } else {
                                    var user_img = prepareFilePart("user_image", bitmap.value!!)

                                    authViewModel.updateProfile(
                                        user_image = user_img,
                                        user_username = username.value.toRequestBody(),
                                        user_displayname = displayName.value.toRequestBody(),
                                        user_email = email.value.toRequestBody(),
                                        user_password = confirmPassword.value.toRequestBody(),
                                        user_id = preferences.getInt("user_id", -1).toString()
                                            .toRequestBody()
                                    )
                                }
                            }
                        },
                        modifier = Modifier
                            .padding(8.dp, 0.dp),
                        shape = RoundedCornerShape(50.dp),
                        colors = ButtonDefaults.buttonColors(backgroundColor = Orange500)) {
                        Text(
                            text = "Save Changes",
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