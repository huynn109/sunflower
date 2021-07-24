/*
 * Copyright 2021 Google LLC
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

package com.google.samples.apps.sunflower.compose.main

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.samples.apps.sunflower.R
import com.google.samples.apps.sunflower.compose.plantdetail.PlantImage
import com.google.samples.apps.sunflower.data.Plant
import com.google.samples.apps.sunflower.viewmodels.PlantListViewModel

@ExperimentalFoundationApi
@Composable
fun PlanListScreen(plantListViewModel: PlantListViewModel = viewModel()) {

    val plans: List<Plant> by plantListViewModel.plants.observeAsState(listOf())
    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(4.dp)
    ) {
        item {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = stringResource(id = R.string.available),
                style = MaterialTheme.typography.h6.copy(fontSize = 18.sp),
                modifier = Modifier.padding(start = 16.dp),
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
        items(plans.windowed(2, 2, true)) { subList ->
            Row(Modifier.fillMaxWidth()) {
                subList.forEach { item ->
                    PlanCard(
                        plan = item,
                        modifier = Modifier
                            .height(120.dp)
                            .padding(4.dp)
                            .background(Color.Yellow)
                            .fillParentMaxWidth(.5f)
                    )
                }
            }
        }
    }
}

@ExperimentalFoundationApi
@Preview(showBackground = true)
@Composable
fun PlanListScreenPreview() {
    PlanListScreen()
}

@ExperimentalFoundationApi
@Composable
fun PlanList(plans: List<Plant>) {
    LazyVerticalGrid(
        cells = GridCells.Fixed(2),
    ) {
        items(plans) { plan ->
            PlanCard(
                plan, Modifier
                    .height(120.dp)
                    .padding(4.dp)
                    .background(Color.Yellow)
                    .fillParentMaxWidth(.5f)
            )
        }
    }
}

@Composable
fun PlanCard(plan: Plant, modifier: Modifier) {
    Card(
        modifier = modifier
            .clickable { },
        elevation = 4.dp,
        shape = RoundedCornerShape(
            topStart = 0.dp,
            topEnd = 8.dp,
            bottomStart = 8.dp,
            bottomEnd = 0.dp
        ),
    ) {
        Column(
        ) {
            PlantImage(
                imageUrl = plan.imageUrl,
                height = dimensionResource(R.dimen.plant_item_image_height)
            )
            Text(
                text = plan.name,
                modifier = Modifier
                    .padding(top = 16.dp, bottom = 16.dp),
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
            )
        }
    }
}
