@file:OptIn(ExperimentalFoundationApi::class)

package com.imtuc.talknity.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavHostController
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.insets.navigationBarsWithImePadding
import com.google.accompanist.insets.statusBarsPadding
import com.imtuc.talknity.R
import com.imtuc.talknity.components.DiscussionCard2
import com.imtuc.talknity.components.IndividualCommunity
import com.imtuc.talknity.model.Community
import com.imtuc.talknity.model.Post
import com.imtuc.talknity.navigation.BottomNavScreen
import com.imtuc.talknity.navigation.CustomBottomNavigation
import com.imtuc.talknity.view.ui.theme.Orange500
import com.imtuc.talknity.view.ui.theme.SoftBlack
import com.imtuc.talknity.view.ui.theme.TalknityTheme
import com.imtuc.talknity.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : ComponentActivity() {

    private lateinit var homeViewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        homeViewModel = ViewModelProvider(this)[HomeViewModel::class.java]

        setContent {
            TalknityTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
//                    HomeScreen(homeViewModel, this)
                }
            }
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(homeViewModel: HomeViewModel, lifecycleOwner: LifecycleOwner, navController: NavHostController) {
    val context = LocalContext.current

    var community = remember {
        mutableStateListOf<Community>()
    }

    var communityLoading = remember {
        mutableStateOf(true)
    }

    var discussionsLoading = remember {
        mutableStateOf(true)
    }

    homeViewModel.communities.observe(lifecycleOwner, Observer {
            response ->
        if (homeViewModel.communityerror.value == "Get Data Successful") {
            community.clear()
            community.addAll(homeViewModel.communities.value!!)
            communityLoading.value = false
        } else {
            communityLoading.value = true
            Toast.makeText(context, homeViewModel.communityerror.value, Toast.LENGTH_SHORT).show()
        }
    })

    var post = remember {
        mutableStateListOf<Post>()
    }

    homeViewModel.posts.observe(lifecycleOwner, Observer {
            response ->
        if (homeViewModel.posterror.value == "Get Data Successful") {
            post.clear()
            post.addAll(homeViewModel.posts.value!!)
            discussionsLoading.value = false
        } else {
            discussionsLoading.value = true
            Toast.makeText(context, homeViewModel.posterror.value, Toast.LENGTH_SHORT).show()
        }
    })

    LaunchedEffect(key1 = true) {
        homeViewModel.getHomeCommunities()
        homeViewModel.getHomePosts()
    }

    val currentScreen = remember {
        mutableStateOf<BottomNavScreen>(BottomNavScreen.Home)
    }

    ProvideWindowInsets(windowInsetsAnimationsEnabled = true) {
        Scaffold(
            bottomBar = {
                CustomBottomNavigation(currentScreenRoute = currentScreen.value.route) {
                    navController.popBackStack()
                    navController.navigate(it.route)
                }
            }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .scrollable(
                        state = rememberScrollState(),
                        orientation = Orientation.Vertical,
                        enabled = true
                    ),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    items(1) {
                        Row(
                            modifier = Modifier
                                .padding(20.dp, 20.dp, 0.dp, 0.dp)
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.Start
                        ) {
                            Text(
                                text = "Whats",
                                fontFamily = FontFamily(Font(R.font.robotoslab_bold)),
                                fontSize = 30.sp,
                                color = SoftBlack
                            )
                            Text(
                                text = " New?",
                                fontFamily = FontFamily(Font(R.font.robotoslab_bold)),
                                fontSize = 30.sp,
                                color = Orange500
                            )
                        }
                        Row(
                            modifier = Modifier
                                .padding(20.dp, 20.dp, 0.dp, 0.dp)
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.Start
                        ) {
                            Text(
                                text = "Top",
                                fontFamily = FontFamily(Font(R.font.opensans_bold)),
                                fontSize = 22.sp,
                                color = SoftBlack
                            )
                            Text(
                                text = " Discussions",
                                fontFamily = FontFamily(Font(R.font.opensans_bold)),
                                fontSize = 22.sp,
                                color = Orange500
                            )
                        }
                        if (discussionsLoading.value) {
                            Spacer(modifier = Modifier.height(100.dp))
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center){
                                CircularProgressIndicator(
                                    color = SoftBlack
                                )
                            }
                            Spacer(modifier = Modifier.height(100.dp))
                        } else {
                            LazyRow(
                                modifier = Modifier
                                    .padding(20.dp, 5.dp, 0.dp, 5.dp),
                                content = {
                                    itemsIndexed(post) { index, item ->
                                        DiscussionCard2(post = item, navController = navController)
                                    }
                                },
                                verticalAlignment = Alignment.CenterVertically
                            )
                        }
                        Row(
                            modifier = Modifier
                                .padding(20.dp, 12.dp, 0.dp, 10.dp)
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.Start
                        ) {
                            Text(
                                text = "Top",
                                fontFamily = FontFamily(Font(R.font.opensans_bold)),
                                fontSize = 22.sp,
                                color = SoftBlack
                            )
                            Text(
                                text = " Community",
                                fontFamily = FontFamily(Font(R.font.opensans_bold)),
                                fontSize = 22.sp,
                                color = Orange500
                            )
                        }
                        if (communityLoading.value) {
                            Spacer(modifier = Modifier.height(100.dp))
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center){
                                CircularProgressIndicator(
                                    color = SoftBlack
                                )
                            }
                            Spacer(modifier = Modifier.height(100.dp))
                        }
                    }

                    itemsIndexed(community) { index, item ->
                        IndividualCommunity(community = item, navController = navController)
                        
                        if (index == community.size - 1) {
                            Spacer(modifier = Modifier.height(84.dp))
                        }
                    }

                }

            }

        }

    }

//    LazyColumn(
//        modifier = Modifier
//            .fillMaxWidth(),
//        verticalArrangement = Arrangement.Center,
//        horizontalAlignment = Alignment.CenterHorizontally,
//        content = {
//            itemsIndexed(community) { index, item ->
//                IndividualCommunity(community = item)
//            }
//        }
//    )
}