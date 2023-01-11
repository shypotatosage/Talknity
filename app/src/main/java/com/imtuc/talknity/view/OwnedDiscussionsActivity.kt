package com.imtuc.talknity.view

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.navigation.NavHostController
import com.google.accompanist.insets.navigationBarsWithImePadding
import com.google.accompanist.insets.statusBarsPadding
import com.imtuc.talknity.R
import com.imtuc.talknity.components.DiscussionCard
import com.imtuc.talknity.components.IndividualCommunity
import com.imtuc.talknity.components.OwnedDiscussionCard
import com.imtuc.talknity.model.Community
import com.imtuc.talknity.model.Post
import com.imtuc.talknity.view.ui.theme.Orange500
import com.imtuc.talknity.view.ui.theme.SoftBlack
import com.imtuc.talknity.viewmodel.PostViewModel

@Composable
fun OwnedDiscussions(postViewModel: PostViewModel, lifecycleOwner: LifecycleOwner, navController: NavHostController) {
    val context = LocalContext.current

    var discussion = remember {
        mutableStateListOf<Post>()
    }

    var discussionsLoading = remember {
        mutableStateOf(true)
    }

    val preferences = context.getSharedPreferences("user", Context.MODE_PRIVATE)

    postViewModel.getOwnedPosts(preferences.getInt("user_id", -1).toString())

    postViewModel.ownedPosts.observe(lifecycleOwner, Observer {
            response ->
        if (postViewModel.ownedPostsError.value == "Get Data Successful") {
            discussion.clear()
            discussion.addAll(postViewModel.ownedPosts.value!!)

            discussionsLoading.value = false
            Log.d("Owned Communities", discussion.toString())
        } else {
            discussionsLoading.value = true
            Toast.makeText(context, postViewModel.ownedPostsError.value, Toast.LENGTH_SHORT).show()
        }
    })

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
                OwnedDiscussionsTop(navController)

                if (discussionsLoading.value) {
                    Spacer(modifier = Modifier.height(250.dp))
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center){
                        CircularProgressIndicator(
                            color = SoftBlack
                        )
                    }
                    Spacer(modifier = Modifier.height(250.dp))
                }
            }

            itemsIndexed(items = discussion) { index, item ->
                OwnedDiscussionCard(post = item, navController = navController, postViewModel = postViewModel, lifecycleOwner = lifecycleOwner)

                if (index == discussion.size - 1) {
                    Spacer(modifier = Modifier.height(24.dp))
                }
            }
        }
    }
}

@Composable
fun OwnedDiscussionsTop(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .navigationBarsWithImePadding()
            .padding(0.dp, 0.dp, 0.dp, 20.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(20.dp, 24.dp, 0.dp, 0.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.arrow_back),
                contentDescription = "Back",
                modifier = Modifier
                    .height(28.dp)
                    .clickable {
                        navController.popBackStack()
                    },
                tint = Orange500
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
                text = "Owned ",
                fontFamily = FontFamily(Font(R.font.robotoslab_bold)),
                fontSize = 30.sp,
                color = SoftBlack
            )
            Text(
                text = "Discussion",
                fontFamily = FontFamily(Font(R.font.robotoslab_bold)),
                fontSize = 30.sp,
                color = Orange500
            )
        }
    }
}