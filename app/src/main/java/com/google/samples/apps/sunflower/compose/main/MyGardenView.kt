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

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonColors
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.samples.apps.sunflower.R
import com.google.samples.apps.sunflower.viewmodels.GardenPlantingListViewModel
import org.intellij.lang.annotations.JdkConstants

@Composable
fun MyGardenScreen(gardenPlantingListViewModel: GardenPlantingListViewModel = viewModel()) {
    val gardenPlantingList by gardenPlantingListViewModel.plantAndGardenPlantings.observeAsState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.sunflower_green_500))
            .wrapContentSize(Alignment.Center)
    ) {
        when {
            gardenPlantingList.isNullOrEmpty().not() -> MyGardenList()
            else -> EmptyGarden()
        }
    }
}

@Composable
fun MyGardenList() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.sunflower_green_500))
            .wrapContentSize(Alignment.Center)
    ) {
        Text(
            text = "Music View",
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(CenterHorizontally),
            textAlign = TextAlign.Center,
            fontSize = 25.sp
        )
    }
}

@Composable
fun EmptyGarden() {
    Column(horizontalAlignment = CenterHorizontally) {
        Text(stringResource(id = R.string.garden_empty), fontSize = 25.sp)
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = { /*TODO*/ }, colors = ButtonDefaults.buttonColors(
                backgroundColor = colorResource(id = R.color.sunflower_yellow_500),
            )
        ) {
            Text(text = stringResource(id = R.string.add_plant).uppercase())
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MyGardenScreenPreview() {
    MyGardenScreen()
}

@Preview(showBackground = true)
@Composable
fun EmptyGardenPreview() {
    EmptyGarden()
}