package com.imtuc.talknity.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.imtuc.talknity.R
import com.imtuc.talknity.view.ui.theme.Orange500
import com.imtuc.talknity.view.ui.theme.SoftBlack
import com.imtuc.talknity.view.ui.theme.TalknityTheme

class IndividualPostActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TalknityTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    IndividualPost()
                }
            }
        }
    }
}

@Composable
fun IndividualPost() {
//    Column(
//        modifier = Modifier
//            .paint(
//                painter = painterResource(id = R.drawable.bg1),
//                contentScale = ContentScale.Crop
//            )
//            .fillMaxSize()
//    ) {
//
//    }

    Column(
        modifier = Modifier
            .fillMaxSize()
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
                .padding(32.dp, 20.dp)
                .fillMaxWidth()
        ) {
            Surface(
                modifier = Modifier
                    .fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                shadowElevation = 5.dp
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp, 16.dp)
                        .fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.talknitylogo),
                            contentDescription = "Back",
                            modifier = Modifier
                                .height(60.dp)
                                .width(60.dp)
                                .clip(shape = RoundedCornerShape(100.dp))
                        )
                        Column(
                            modifier = Modifier
                                .fillMaxWidth(0.6f)
                                .padding(12.dp, 0.dp)
                        ) {
                            Text(
                                text = "Jennie",
                                color = Orange500,
                                fontSize = 16.sp,
                                fontFamily = FontFamily(Font(R.font.opensans_bold))
                            )
                            Text(
                                text = "I Just Lost My Cat",
                                color = Orange500,
                                fontSize = 16.sp,
                                fontFamily = FontFamily(Font(R.font.opensans_bold))
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview2() {
    TalknityTheme {
        IndividualPost()
    }
}