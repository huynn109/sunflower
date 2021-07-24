/*
 * Copyright 2018 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.samples.apps.sunflower

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.google.accompanist.pager.*
import com.google.samples.apps.sunflower.compose.main.MyGardenScreen
import com.google.samples.apps.sunflower.compose.main.PlanListScreen
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

typealias ComposableFun = @Composable () -> Unit

sealed class TabItem(var icon: Int, var title: Int, var screen: ComposableFun) {
    object Garden :
        TabItem(R.drawable.ic_my_garden_inactive, R.string.my_garden_title, { MyGardenScreen() })

    @ExperimentalFoundationApi
    object PlanList :
        TabItem(R.drawable.ic_plant_list_inactive, R.string.plant_list_title, { PlanListScreen() })
}

@AndroidEntryPoint
class GardenActivity : AppCompatActivity() {

    @ExperimentalFoundationApi
    @ExperimentalMaterialApi
    @ExperimentalPagerApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainScreen()
        }
    }
}

@ExperimentalFoundationApi
@ExperimentalMaterialApi
@ExperimentalPagerApi
@Composable
fun MainScreen() {
    val tabs = listOf(TabItem.Garden, TabItem.PlanList)
    val pagerState = rememberPagerState(pageCount = tabs.size)
    Scaffold(
        topBar = { TopBar() },
    ) { innerPadding ->
        BodyContent(
            modifier = Modifier
                .padding(innerPadding),
            tabs,
            pagerState,
        )
    }
}

@ExperimentalPagerApi
@ExperimentalMaterialApi
@Composable
fun BodyContent(modifier: Modifier, tabs: List<TabItem>, pagerState: PagerState) {
    Column(modifier) {
        Tabs(tabs = tabs, pagerState = pagerState)
        TabsContent(tabs = tabs, pagerState = pagerState)
    }
}

@ExperimentalPagerApi
@Composable
fun TabsContent(tabs: List<TabItem>, pagerState: PagerState) {
    HorizontalPager(state = pagerState) { page ->
        tabs[page].screen()
    }
}

@ExperimentalFoundationApi
@ExperimentalMaterialApi
@ExperimentalPagerApi
@Preview(showBackground = true)
@Composable
fun TabsContentPreview() {
    val tabs = listOf(
        TabItem.Garden,
        TabItem.PlanList,
    )
    val pagerState = rememberPagerState(pageCount = tabs.size)
    TabsContent(tabs = tabs, pagerState = pagerState)
}

@ExperimentalMaterialApi
@ExperimentalPagerApi
@Composable
fun Tabs(tabs: List<TabItem>, pagerState: PagerState) {
    val scope = rememberCoroutineScope()
    // OR ScrollableTabRow()
    TabRow(
        // Our selected tab is our current page
        selectedTabIndex = pagerState.currentPage,
        // Override the indicator, using the provided pagerTabIndicatorOffset modifier
        modifier = Modifier.zIndex(8f),
        backgroundColor = colorResource(id = R.color.sunflower_green_500),
        ) {
        // Add tabs for all of our pages
        tabs.forEachIndexed { index, tab ->
            // OR Tab()
            LeadingIconTab(
                icon = {
                    Icon(
                        painter = painterResource(id = tab.icon),
                        contentDescription = null
                    )
                },
                text = { Text(stringResource(tab.title)) },
                selected = pagerState.currentPage == index,
                onClick = {
                    scope.launch {
                        pagerState.animateScrollToPage(index)
                    }
                },
            )
        }
    }
}

@ExperimentalFoundationApi
@ExperimentalMaterialApi
@ExperimentalPagerApi
@Preview(showBackground = true)
@Composable
fun TabsPreview() {
    val tabs = listOf(
        TabItem.Garden,
        TabItem.PlanList,
    )
    val pagerState = rememberPagerState(pageCount = tabs.size)
    Tabs(tabs = tabs, pagerState = pagerState)
}

@Composable
fun TopBar() {
    TopAppBar(
        title = { Text(text = stringResource(R.string.app_name), fontSize = 18.sp) },
        backgroundColor = colorResource(id = R.color.sunflower_green_500),
        elevation = 0.dp
    )
}

