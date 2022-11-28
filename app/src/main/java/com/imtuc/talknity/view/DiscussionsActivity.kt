package com.imtuc.talknity.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyVerticalGrid
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.insets.navigationBarsWithImePadding
import com.google.accompanist.insets.statusBarsPadding
import com.imtuc.talknity.R
import com.imtuc.talknity.components.CommunityCategoryCard
import com.imtuc.talknity.components.DiscussionCard
import com.imtuc.talknity.view.ui.theme.*

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
                    Discussions()
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Discussions() {
    var search = remember {
        mutableStateOf("")
    }

    ProvideWindowInsets(windowInsetsAnimationsEnabled = true) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .statusBarsPadding()
                    .navigationBarsWithImePadding()
                    .verticalScroll(rememberScrollState())
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
                    IconButton(
                        onClick = { /*TODO*/ },

                    ) {
                        Icon(
                            imageVector = Icons.Default.AddCircleOutline,
                            contentDescription = "Add Discussions",
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
                                        Image(
                                            painter = painterResource(id = R.drawable.ic_baseline_search_24),
                                            contentDescription = "Search",
                                            modifier = Modifier
                                                .height(32.dp)
                                        )
                                    }
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
            LazyColumn() {
                items(4) {
                    DiscussionCard()
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview2() {
    TalknityTheme {
        Discussions()
    }
}