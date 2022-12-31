package com.imtuc.talknity.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.imtuc.talknity.R
import com.imtuc.talknity.view.ui.theme.Orange500

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun CustomBottomNavigation(
    currentScreenRoute:String,
    onItemSelected:(BottomNavScreen)->Unit
) {
    val items = BottomNavScreen.Items.list

    Surface(
        shape = RoundedCornerShape(50.dp),
        modifier = Modifier
            .padding(20.dp, 0.dp, 20.dp, 16.dp)
    ) {
        Row(
            modifier = Modifier
                .background(color = Orange500)
                .padding(6.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {

            items.forEach { item ->

                CustomBottomNavigationItem(item = item, isSelected = item.route == currentScreenRoute) {
                    onItemSelected(item)
                }

            }

        }
    }

}

@ExperimentalAnimationApi
@Composable
fun CustomBottomNavigationItem(item: BottomNavScreen, isSelected: Boolean, onClick:() -> Unit){
    val background=if (isSelected) Color.White.copy(alpha = 0.1f) else Color.Transparent
    val contentColor=if (isSelected) Color.White else Color.White

    Box(
        modifier = Modifier
            .clip(CircleShape)
            .background(background)
            .clickable(onClick = onClick)
    ){
        Row(
            modifier= Modifier
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {

            Icon(
                imageVector = item.icon,
                contentDescription = null,
                tint = contentColor
            )

            AnimatedVisibility(visible = isSelected) {
                Text(
                    text = item.title,
                    color = contentColor,
                    fontFamily = FontFamily(Font(R.font.opensans_regular))
                )
            }

        }
    }


}


@Composable
@Preview
fun Prev1(){
    CustomBottomNavigation(currentScreenRoute = BottomNavScreen.Home.route) {

    }
}

@ExperimentalAnimationApi
@Composable
@Preview
fun Prev2() {
    CustomBottomNavigationItem(item = BottomNavScreen.Home, isSelected = true) {

    }
}