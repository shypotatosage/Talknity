package com.imtuc.talknity.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
//<<<<<<< HEAD
//import androidx.compose.foundation.Image
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.rememberScrollState
//import androidx.compose.foundation.verticalScroll
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.Surface
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.paint
//import androidx.compose.ui.layout.ContentScale
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.text.font.Font
//import androidx.compose.ui.text.font.FontFamily
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import com.imtuc.talknity.R
//import com.imtuc.talknity.view.ui.theme.Orange500
//import com.imtuc.talknity.view.ui.theme.SoftBlack
//=======
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.imtuc.talknity.navigation.SetupNavGraph
//>>>>>>> main
import com.imtuc.talknity.view.ui.theme.TalknityTheme
import com.imtuc.talknity.viewmodel.AuthViewModel
import com.imtuc.talknity.viewmodel.CommunityViewModel
import com.imtuc.talknity.viewmodel.HomeViewModel
import com.imtuc.talknity.viewmodel.PostViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    lateinit var navController: NavHostController
    private lateinit var authViewModel: AuthViewModel
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var communityViewModel: CommunityViewModel
    private lateinit var postViewModel: PostViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        authViewModel = ViewModelProvider(this)[AuthViewModel::class.java]
        homeViewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        communityViewModel = ViewModelProvider(this)[CommunityViewModel::class.java]
        postViewModel = ViewModelProvider(this)[PostViewModel::class.java]

        setContent {
            TalknityTheme {
                navController = rememberNavController()
                SetupNavGraph(
                    navController = navController,
                    authViewModel = authViewModel,
                    homeViewModel = homeViewModel,
                    communityViewModel = communityViewModel,
                    postViewModel = postViewModel,
                    lifecycleOwner = this
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SplashPreview2() {
    TalknityTheme {
//        TalknityTheme()
    }
}
