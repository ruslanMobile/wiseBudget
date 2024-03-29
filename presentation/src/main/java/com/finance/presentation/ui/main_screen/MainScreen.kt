package com.finance.presentation.ui.main_screen

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.exyte.animatednavbar.AnimatedNavigationBar
import com.exyte.animatednavbar.animation.indendshape.shapeCornerRadius
import com.finance.presentation.MainActivity
import com.finance.presentation.R
import com.finance.presentation.model.BottomNavItem
import com.finance.presentation.model.Screen
import com.finance.presentation.ui.categories.CategoriesScreen
import com.finance.presentation.ui.profile.ProfileScreen
import com.finance.presentation.ui.theme.GreenDark
import com.finance.presentation.ui.theme.Silver

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen(

) {

    val context = LocalContext.current
    BackHandler(enabled = true) {
        ActivityCompat.finishAffinity(context as MainActivity)
    }

    var selectedIndex = remember {
        mutableIntStateOf(0)
    }
    val navController = rememberNavController()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background,
        bottomBar = {
            AnimatedNavigationBar(
                modifier = Modifier
                    .height(dimensionResource(id = R.dimen.offset_62)),
                selectedIndex = selectedIndex.value,
                cornerRadius = shapeCornerRadius(
                    topLeft = dimensionResource(id = R.dimen.offset_20),
                    topRight = dimensionResource(id = R.dimen.offset_20),
                    bottomLeft = 0.dp, bottomRight = 0.dp
                ),
                barColor = GreenDark,
                ballColor = GreenDark
            ) {

                BottomNavItem.values().forEach { item ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .clickable {
                                selectedIndex.value = item.ordinal
                                navController.navigate(item.route)
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            contentDescription = null,
                            imageVector = item.icon,
                            tint = if (selectedIndex.value == item.ordinal) Color.White else Silver,
                            modifier = Modifier.size(dimensionResource(id = R.dimen.offset_26))
                        )
                    }
                }
            }
        }
    ) {
        Surface(
            modifier = Modifier.padding(
                bottom = it.calculateBottomPadding() - dimensionResource(id = R.dimen.offset_10)
            )
        ) {
            NavHost(
                navController = navController,
                startDestination = BottomNavItem.Categories.route
            ) {
                composable(Screen.Categories.route) {
                    CategoriesScreen(navController, hiltViewModel())
                }

                composable(Screen.Profile.route) {
                    ProfileScreen()
                }
            }
        }
    }

}