package com.imtuc.talknity.view

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
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.AddCircleOutline
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.navigation.NavHostController
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.insets.navigationBarsWithImePadding
import com.google.accompanist.insets.statusBarsPadding
import com.imtuc.talknity.R
import com.imtuc.talknity.components.CommunityCategoryCard
import com.imtuc.talknity.components.DiscussionCard
import com.imtuc.talknity.model.Post
import com.imtuc.talknity.navigation.Screen
import com.imtuc.talknity.view.ui.theme.*
import com.imtuc.talknity.viewmodel.PostViewModel

class DiscussionsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TalknityTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
//                    Discussions()
                }
            }
        }
    }
}

@Composable
fun Discussions(postViewModel: PostViewModel, lifecycleOwner: LifecycleOwner, navController: NavHostController) {
    val context = LocalContext.current

    var post = remember {
        mutableStateListOf<Post>()
    }

    var search = remember {
        mutableStateOf("")
    }

    postViewModel.getPosts()

    postViewModel.posts.observe(lifecycleOwner, Observer {
            response ->
        if (postViewModel.posterror.value == "Get Data Successful") {
            post.clear()
            post.addAll(postViewModel.posts.value!!)

            Log.e("Discussions", post.toString())
        } else {
            Toast.makeText(context, postViewModel.posterror.value, Toast.LENGTH_SHORT).show()
        }
    })

    ProvideWindowInsets(windowInsetsAnimationsEnabled = true) {
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
                            .statusBarsPadding()
                            .navigationBarsWithImePadding()
                    ) {
                        Row(
                            modifier = Modifier
                                .padding(20.dp, 24.dp, 20.dp, 0.dp)
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "Discussions",
                                fontFamily = FontFamily(Font(R.font.robotoslab_bold)),
                                fontSize = 28.sp,
                                color = SoftBlack
                            )
                            Button(
                                shape = RoundedCornerShape(50.dp),
                                modifier = Modifier
                                    .padding(0.dp)
                                    .defaultMinSize(minWidth = 1.dp, minHeight = 1.dp),
                                colors = ButtonDefaults.buttonColors(
                                    backgroundColor = Orange500
                                ),
                                onClick = {
                                    navController.navigate(Screen.CreateDiscussion.route)
                                },
                                contentPadding = PaddingValues(0.dp, 0.dp)
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.addicon),
                                    contentDescription = "Add Discussions",
                                    modifier = Modifier
                                        .size(40.dp, 40.dp)
                                        .padding(6.dp),
                                    tint = Color.White
                                )
                            }
                        }
                        Surface(
                            modifier = Modifier
                                .padding(20.dp, 20.dp),
                            shadowElevation = 4.dp,
                            border = BorderStroke(
                                width = 0.4.dp,
                                color = GrayBorder
                            ),
                            shape = RoundedCornerShape(25.dp)
                        ) {
                            BasicTextField(
                                value = search.value,
                                onValueChange = {
                                    search.value = it

                                    if (it.isNotEmpty()) {
                                        postViewModel.searchPosts(it)
                                    } else {
                                        postViewModel.getPosts()
                                    }
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
                                            .padding(16.dp, 4.dp),
                                        contentAlignment = Alignment.CenterStart
                                    ) {
                                        if (search.value.isEmpty()) {
                                            Row(
                                                horizontalArrangement = Arrangement.SpaceBetween,
                                                modifier = Modifier.fillMaxWidth(),
                                                verticalAlignment = Alignment.CenterVertically
                                            ) {
                                                Text(
                                                    text = "Search",
                                                    color = Gray300,
                                                    fontSize = 16.sp,
                                                    fontFamily = FontFamily(Font(R.font.opensans_regular))
                                                )
                                                androidx.compose.material3.Icon(
                                                    painter = painterResource(id = R.drawable.ic_baseline_search_24),
                                                    contentDescription = "Search",
                                                    modifier = Modifier
                                                        .height(32.dp)
                                                )
                                            }

                                            Row(
                                                horizontalArrangement = Arrangement.SpaceBetween,
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .padding(0.dp, 5.dp),
                                                verticalAlignment = Alignment.CenterVertically
                                            ) {
                                                innerTextField()
                                            }
                                        } else {
                                            Row(
                                                horizontalArrangement = Arrangement.SpaceBetween,
                                                modifier = Modifier.fillMaxWidth(),
                                                verticalAlignment = Alignment.CenterVertically
                                            ) {
                                                innerTextField()
                                                Image(
                                                    painter = painterResource(id = R.drawable.ic_baseline_search_24),
                                                    contentDescription = "Search",
                                                    modifier = Modifier
                                                        .height(32.dp)
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
                    }
                }

                itemsIndexed(post) { index, item ->
                    DiscussionCard(item)
                }
            }
        }
    }
}