package com.imtuc.talknity.view

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
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
import com.imtuc.talknity.components.IndividualCommunity
import com.imtuc.talknity.model.Community
import com.imtuc.talknity.view.ui.theme.*
import com.imtuc.talknity.viewmodel.CommunityViewModel

class SelectedCategoryCommunityListActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TalknityTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
//                    SelectedCategoryCommunityList()
                }
            }
        }
    }
}

@Composable
fun SelectedCategoryCommunityList(category_id: String, category_name: String, communityViewModel: CommunityViewModel, lifecycleOwner: LifecycleOwner, navController: NavHostController) {
    val context = LocalContext.current

    var search = remember {
        mutableStateOf("")
    }

    var community = remember {
        mutableStateListOf<Community>()
    }

    communityViewModel.getCommunitiesCategory(category_id)

    communityViewModel.categoryCommunities.observe(lifecycleOwner, Observer {
            response ->
        if (communityViewModel.categoryCommunitiesError.value == "Get Data Successful") {
            community.clear()
            community.addAll(communityViewModel.categoryCommunities.value!!)

            Log.d("Selected Category Communities", community.toString())
        } else {
            Toast.makeText(context, communityViewModel.categoryCommunitiesError.value, Toast.LENGTH_SHORT).show()
        }
    })

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(1) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .statusBarsPadding()
                        .navigationBarsWithImePadding()
                ) {
                    Row(
                        modifier = Modifier
                            .padding(20.dp, 24.dp, 0.dp, 0.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Communities",
                            fontFamily = FontFamily(Font(R.font.robotoslab_bold)),
                            fontSize = 28.sp,
                            color = SoftBlack
                        )
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

                                if (it.trim().isEmpty()) {
                                    communityViewModel.getCommunitiesCategory(category_id)
                                } else {
                                    communityViewModel.searchCommunitiesCategory(category_id, it.trim())
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
                                        .padding(16.dp, 4.dp)
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
                                            Icon(
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
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(0.dp, 0.dp, 0.dp, 6.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row() {
                        Text(
                            text = category_name,
                            fontFamily = FontFamily(Font(R.font.robotoslab_bold)),
                            fontSize = 28.sp,
                            color = Orange500
                        )
                        Text(
                            text = " Section",
                            fontFamily = FontFamily(Font(R.font.robotoslab_bold)),
                            fontSize = 28.sp,
                            color = SoftBlack
                        )
                    }
                }

            }
        }

        itemsIndexed(items = community) { index, item ->
            IndividualCommunity(community = item)
        }
    }
}