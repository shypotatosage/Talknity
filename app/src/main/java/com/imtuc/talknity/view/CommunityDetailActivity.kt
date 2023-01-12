package com.imtuc.talknity.view

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
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
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.insets.navigationBarsWithImePadding
import com.google.accompanist.insets.statusBarsPadding
import com.imtuc.talknity.R
import com.imtuc.talknity.components.CommunityCategoryCard
import com.imtuc.talknity.components.CommunityLeader
import com.imtuc.talknity.components.CommunityMember
import com.imtuc.talknity.components.IndividualCommunity
import com.imtuc.talknity.helper.Const
import com.imtuc.talknity.model.CommunityMember
import com.imtuc.talknity.view.ui.theme.*
import com.imtuc.talknity.viewmodel.CommunityViewModel

class CommunityDetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TalknityTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.White
                ) {
//                    CommunityDetail()
                }
            }
        }
    }

}

@Composable
fun CommunityDetail(
    community_id: String,
    communityViewModel: CommunityViewModel,
    lifecycleOwner: LifecycleOwner,
    navController: NavHostController
) {
    var displayContact = remember {
        mutableStateOf("")
    }

    var displayDesc = remember {
        mutableStateOf("")
    }

    var displayCategory = remember {
        mutableStateOf("")
    }

    var displayImage = remember {
        mutableStateOf("")
    }

    var displayName = remember {
        mutableStateOf("")
    }

    var members = remember {
        mutableStateListOf<CommunityMember>()
    }

    var userIsMember = remember {
        mutableStateOf("")
    }

    var userIsLeader = remember {
        mutableStateOf(false)
    }

    var loading = remember {
        mutableStateOf(true)
    }

    val context = LocalContext.current

    communityViewModel.getCommunityMembers(community_id)

    communityViewModel.communityMembers.observe(lifecycleOwner, Observer { response ->
        userIsLeader.value = false
        userIsMember.value = ""

        if (communityViewModel.communityMembersError.value == "Get Data Successful") {
            displayName.value = response.community_name
            displayDesc.value = response.community_description
            displayContact.value = response.community_contact
            displayImage.value = response.community_logo
            displayCategory.value = response.community_category.category_name

            members.clear()
            members.add(CommunityMember("0", community_id, response.leader))
            members.addAll(response.members)

            val preferences = context.getSharedPreferences("user", Context.MODE_PRIVATE)

            if (response.leader.user_id == preferences.getInt("user_id", -1).toString()) {
                userIsLeader.value = true
            } else {
                for (item in response.members) {
                    if (item.user.user_id == preferences.getInt("user_id", -1).toString()) {
                        userIsMember.value = "1"
                    }
                }

                if (userIsMember.value == "") {
                    userIsMember.value = "0"
                }
            }

            loading.value = false
        } else if (communityViewModel.communityMembersError.value?.trim() != null) {
            loading.value = true
            Toast.makeText(
                context,
                communityViewModel.communityMembersError.value,
                Toast.LENGTH_SHORT
            ).show()
        }
    })

    communityViewModel.communityMemberJoin.observe(lifecycleOwner, Observer { response ->
        if (response != "") {
            Toast.makeText(context, response, Toast.LENGTH_SHORT).show()

            if (response == "Successfully Joined The Community") {
                communityViewModel.getCommunityMembers(community_id)
                communityViewModel.resetMemberJoin()
            }
        }
    })

    communityViewModel.communityMemberOut.observe(lifecycleOwner, Observer { response ->
        if (response != "") {
            Toast.makeText(context, response, Toast.LENGTH_SHORT).show()

            if (response == "Successfully Signed Out From The Community") {
                communityViewModel.getCommunityMembers(community_id)
                communityViewModel.resetMemberOut()
                navController.popBackStack()
            }
        }
    })

    if (loading.value) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Spacer(modifier = Modifier.fillMaxHeight(0.475f))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center){
                CircularProgressIndicator(
                    color = SoftBlack
                )
            }
            Spacer(modifier = Modifier.fillMaxHeight(0.475f))
        }
    } else {
        ProvideWindowInsets(windowInsetsAnimationsEnabled = true) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .scrollable(
                        state = rememberScrollState(),
                        orientation = Orientation.Vertical,
                        enabled = true
                    )
                    .background(color = GreyishWhite),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = GreyishWhite),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    items(1) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .statusBarsPadding()
                                .background(color = GreyishWhite)
//                .verticalScroll(rememberScrollState())
                                .navigationBarsWithImePadding(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Top,
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(Orange500)
                            ) {
                                Row(
                                    modifier = Modifier
                                        .padding(20.dp, 24.dp, 0.dp, 0.dp)
                                        .fillMaxWidth()
                                        .background(color = Orange500),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Image(
                                        painter = painterResource(id = R.drawable.arrow_back_white),
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
                                        color = Color.White
                                    )
                                }
                                Text(
                                    text = displayName.value,
                                    fontFamily = FontFamily(Font(R.font.robotoslab_bold)),
                                    fontSize = 40.sp,
                                    color = Color.White,
                                    modifier = Modifier
                                        .padding(20.dp, 24.dp, 0.dp, 0.dp)
                                )
                                Text(
                                    text = displayCategory.value + " Community",
                                    fontFamily = FontFamily(Font(R.font.robotoslab_bold)),
                                    fontSize = 30.sp,
                                    color = Color.White,
                                    modifier = Modifier
                                        .padding(20.dp, 0.dp, 0.dp, 0.dp)
                                )

                            }
                            Row(
                                modifier = Modifier
                                    .background(Orange500)
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.Center,
                            ) {
                                Surface(
                                    shape = RoundedCornerShape(16.dp),
                                    shadowElevation = 8.dp,
                                    modifier = Modifier
                                        .wrapContentSize(align = Alignment.Center)
                                        .padding(0.dp, 20.dp)
                                ) {
                                    Image(
                                        painter = rememberAsyncImagePainter(Const.BASE_URL + "/" + displayImage.value),
                                        contentDescription = "Community Logo",
                                        modifier = Modifier
                                            .width(200.dp)
                                            .height(200.dp)
                                    )
                                }
                                if ((userIsMember.value != "1") && !userIsLeader.value) {
                                    Button(
                                        onClick = {
                                            val preferences = context.getSharedPreferences(
                                                "user",
                                                Context.MODE_PRIVATE
                                            )

                                            communityViewModel.joinCommunity(
                                                community_id,
                                                preferences.getInt("user_id", -1).toString()
                                            )
                                        },
                                        modifier = Modifier
                                            .padding(13.dp, 63.dp, 0.dp, 0.dp),
                                        shape = RoundedCornerShape(50.dp),
                                        colors = ButtonDefaults.buttonColors(backgroundColor = Red500)
                                    ) {
                                        Text(
                                            modifier = Modifier
                                                .padding(13.dp, .5.dp),
                                            text = "Apply",
                                            fontSize = 25.sp,
                                            fontFamily = FontFamily(Font(R.font.opensans_bold)),
                                            color = GreyishWhite
                                        )
                                    }
                                } else if (userIsMember.value == "1" && !userIsLeader.value) {
                                    Button(
                                        onClick = {
                                            val preferences = context.getSharedPreferences(
                                                "user",
                                                Context.MODE_PRIVATE
                                            )

                                            communityViewModel.signoutCommunity(
                                                community_id,
                                                preferences.getInt("user_id", -1).toString()
                                            )
                                        },
                                        modifier = Modifier
                                            .padding(13.dp, 63.dp, 0.dp, 0.dp),
                                        shape = RoundedCornerShape(50.dp),
                                        colors = ButtonDefaults.buttonColors(backgroundColor = Red500)
                                    ) {
                                        Text(
                                            modifier = Modifier
                                                .padding(13.dp, .5.dp),
                                            text = "Signout",
                                            fontSize = 25.sp,
                                            fontFamily = FontFamily(Font(R.font.opensans_bold)),
                                            color = GreyishWhite
                                        )
                                    }
                                }
                            }
                            Row(
                                modifier = Modifier
                                    .padding(24.dp, 0.dp)
//                    .background(Color.White)
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.Center,
                            ) {
                                Column() {
                                    Text(
                                        text = "Contact",
                                        fontFamily = FontFamily(Font(R.font.robotoslab_bold)),
                                        fontSize = 24.sp,
                                        color = SoftBlack,
                                        modifier = Modifier
                                            .padding(0.dp, 28.dp, 0.dp, 10.dp)
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
                                            value = displayContact.value,
                                            onValueChange = {},
                                            enabled = false,
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .wrapContentHeight()
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
                                                    if (displayContact.value.isEmpty()) {
                                                        Text(
                                                            text = "Display Contact",
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
                                        text = "Description",
                                        fontFamily = FontFamily(Font(R.font.robotoslab_bold)),
                                        fontSize = 24.sp,
                                        color = SoftBlack,
                                        modifier = Modifier
                                            .padding(0.dp, 20.dp, 0.dp, 10.dp)
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
                                            value = displayDesc.value,
                                            onValueChange = {},
                                            enabled = false,
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .wrapContentHeight()
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
                                                    if (displayDesc.value.isEmpty()) {
                                                        Text(
                                                            text = "Display Description",
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

                            }
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(24.dp, 20.dp, 24.dp, 10.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically

                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Image(
                                        painter = painterResource(id = R.drawable.ownedcommunity),
                                        contentDescription = "Back",
                                        modifier = Modifier
                                            .size(40.dp)
                                            .padding(0.dp, 0.dp, 8.dp, 0.dp)
                                    )
                                    Text(
                                        text = (members.size - 1).toString() + " Members",
                                        textAlign = TextAlign.Start,
                                        fontSize = 20.sp,
                                        fontFamily = FontFamily(Font(R.font.robotoslab_semibold)),
                                    )
                                }
                            }
                        }
                    }

                    gridItems(
                        data = members,
                        columnCount = 3,
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier
                            .padding(16.dp, 0.dp, 16.dp, 20.dp)
                    ) { item ->
                        CommunityMember(
                            communityMember = item,
                            isLeader = userIsLeader.value,
                            communityViewModel = communityViewModel,
                            lifecycleOwner = lifecycleOwner
                        )
                    }
                }
            }
        }
    }
}

//Column(
//    modifier = Modifier
//        .fillMaxSize()
//        .background(Color.White)
//) {
//
//}
//        }
//    }
//}