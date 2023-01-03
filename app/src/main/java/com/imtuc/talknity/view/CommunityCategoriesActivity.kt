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
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
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
import com.imtuc.talknity.model.CommunityCategory
import com.imtuc.talknity.model.Post
import com.imtuc.talknity.navigation.BottomNavScreen
import com.imtuc.talknity.navigation.CustomBottomNavigation
import com.imtuc.talknity.navigation.Screen
import com.imtuc.talknity.view.ui.theme.Gray300
import com.imtuc.talknity.view.ui.theme.GrayBorder
import com.imtuc.talknity.view.ui.theme.SoftBlack
import com.imtuc.talknity.view.ui.theme.TalknityTheme
import com.imtuc.talknity.viewmodel.CommunityViewModel

class CommunityCategoriesActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TalknityTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
//                    CommunityCategories()
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun CommunityCategories(
    communityViewModel: CommunityViewModel,
    lifecycleOwner: LifecycleOwner,
    navController: NavHostController
) {
    var search = remember {
        mutableStateOf("")
    }

    val context = LocalContext.current

    var category = remember {
        mutableStateListOf<CommunityCategory>()
    }

    var categoryLoading = remember {
        mutableStateOf(true)
    }

    communityViewModel.getCategory()

    communityViewModel.category.observe(lifecycleOwner, Observer { response ->
        if (communityViewModel.categoryError.value == "Get Data Successful") {
            category.clear()
            category.addAll(communityViewModel.category.value!!)

            categoryLoading.value = false
            Log.e("Categories", category.toString())
        } else {
            categoryLoading.value = true
            Toast.makeText(context, communityViewModel.categoryError.value, Toast.LENGTH_SHORT)
                .show()
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
                            .fillMaxSize()
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .statusBarsPadding()
                                .navigationBarsWithImePadding()
//                                    .verticalScroll(rememberScrollState())
                        ) {
                            Row(
                                modifier = Modifier
                                    .padding(20.dp, 24.dp, 0.dp, 0.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "Community Category",
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

                                        if (it.trim().isNotEmpty()) {
                                            communityViewModel.searchCategory(it)
                                        } else {
                                            communityViewModel.getCategory()
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
                        if (categoryLoading.value) {
                            Spacer(modifier = Modifier.height(200.dp))
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center){
                                CircularProgressIndicator(
                                    color = SoftBlack
                                )
                            }
                            Spacer(modifier = Modifier.height(200.dp))
                        }
                    }
                }

                gridItems(
                    data = category,
                    columnCount = 2,
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.padding(16.dp, 0.dp)
                ) { item ->
                    CommunityCategoryCard(item, navController = navController)
                }
                
                items(1) {
                    Spacer(modifier = Modifier.height(25.dp))
                }
            }
        }
    }
}

fun <T> LazyListScope.gridItems(
    data: List<T>,
    columnCount: Int,
    modifier: Modifier,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.Start,
    itemContent: @Composable BoxScope.(T) -> Unit,
) {
    val size = data.count()
    val rows = if (size == 0) 0 else 1 + (size - 1) / columnCount
    items(rows, key = { it.hashCode() }) { rowIndex ->
        Row(
            horizontalArrangement = horizontalArrangement,
            modifier = modifier
        ) {
            for (columnIndex in 0 until columnCount) {
                val itemIndex = rowIndex * columnCount + columnIndex
                if (itemIndex < size) {
                    Box(
                        modifier = Modifier.weight(1F, fill = true),
                        propagateMinConstraints = true
                    ) {
                        itemContent(data[itemIndex])
                    }
                } else {
                    Spacer(Modifier.weight(1F, fill = true))
                }
            }
        }
    }
}