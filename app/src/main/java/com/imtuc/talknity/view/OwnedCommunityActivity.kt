package com.imtuc.talknity.view

import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.DropdownMenu
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.Icon
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
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.insets.navigationBarsWithImePadding
import com.google.accompanist.insets.statusBarsPadding
import com.imtuc.talknity.R
import com.imtuc.talknity.components.DiscussionCard
import com.imtuc.talknity.components.IndividualCommunity
import com.imtuc.talknity.model.Community
import com.imtuc.talknity.view.ui.theme.Orange500
import com.imtuc.talknity.view.ui.theme.SoftBlack
import com.imtuc.talknity.viewmodel.CommunityViewModel

@Composable
fun OwnedCommunity(communityViewModel: CommunityViewModel, lifecycleOwner: LifecycleOwner, navController: NavHostController) {
    val context = LocalContext.current

    var community = remember {
        mutableStateListOf<Community>()
    }

    val preferences = context.getSharedPreferences("user", Context.MODE_PRIVATE)

    communityViewModel.getOwnedCommunities(preferences.getInt("user_id", -1).toString())

    communityViewModel.ownedCommunities.observe(lifecycleOwner, Observer {
            response ->
        if (communityViewModel.ownedCommunitiesError.value == "Get Data Successful") {
            community.clear()
            community.addAll(communityViewModel.ownedCommunities.value!!)

            Log.d("Owned Communities", community.toString())
        } else {
            Toast.makeText(context, communityViewModel.ownedCommunitiesError.value, Toast.LENGTH_SHORT).show()
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
                OwnedCommunityTop(navController)
            }

            itemsIndexed(items = community) { index, item ->
                IndividualCommunity(community = item)

                if (index == community.size - 1) {
                    Spacer(modifier = Modifier.height(24.dp))
                }
            }
        }
    }
}

@Composable
fun OwnedCommunityTop(navController: NavHostController) {
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
                text = "Community",
                fontFamily = FontFamily(Font(R.font.robotoslab_bold)),
                fontSize = 30.sp,
                color = Orange500
            )
        }
    }
}