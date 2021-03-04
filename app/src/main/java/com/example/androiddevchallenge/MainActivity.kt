/*
 * Copyright 2021 The Android Open Source Project
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
package com.example.androiddevchallenge

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.androiddevchallenge.ui.theme.MyTheme
import com.example.androiddevchallenge.ui.theme.purple200
import com.example.androiddevchallenge.ui.theme.purple500
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyTheme {
                MyApp()
            }
        }
    }
}

@Composable
fun MyApp() {
    Surface(color = MaterialTheme.colors.background, elevation = 2.dp) {
        Timer()
    }
}

@Composable
fun Timer() {
    var isCounting by remember { mutableStateOf(false) }
    var remainingTime by remember { mutableStateOf(30) }
    val time by remember { mutableStateOf(30) }

    val progress by animateFloatAsState(
        animationSpec = tween(durationMillis = 1000, easing = LinearEasing),
        targetValue = remainingTime.toFloat() / time
    )

    Column(
        modifier = Modifier.padding(14.dp)
    ) {
        Box(modifier = Modifier.aspectRatio(1f), contentAlignment = Alignment.Center) {
            CircularProgressIndicator(
                progress = progress,
                strokeWidth = 10.dp,
                color = purple500,
                modifier = Modifier
                    .padding(14.dp)
                    .fillMaxSize()
            )
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = remainingTime.toString(),
                    color = purple200,
                    fontFamily = FontFamily.Serif,
                    style = typography.h2
                )
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(R.drawable.ic_play),
                contentDescription = null,
                modifier = Modifier
                    .height(80.dp)
                    .width(80.dp)
                    .clickable(
                        onClick = {
                            if (!isCounting && remainingTime > 0) isCounting = true
                        }
                    ),
                contentScale = ContentScale.Inside
            )
            Image(
                painter = painterResource(R.drawable.ic_pause),
                contentDescription = null,
                contentScale = ContentScale.Inside,
                modifier = Modifier
                    .height(80.dp)
                    .width(80.dp)
                    .clickable(
                        onClick = {
                            if (isCounting) isCounting = false
                        }
                    )
            )
            Image(
                painter = painterResource(R.drawable.ic_stop),
                contentDescription = null,
                contentScale = ContentScale.Inside,
                modifier = Modifier
                    .height(80.dp)
                    .width(80.dp)
                    .clickable(
                        onClick = {
                            isCounting = false
                            remainingTime = time
                        }
                    )
            )
        }
    }

    if (isCounting) {
        LaunchedEffect("timer") {
            while (isActive) {
                if (isActive) {
                    remainingTime--
                    if (remainingTime == 0) isCounting = false
                }
                delay(1000)
            }
        }
    }
}

@Preview("Light Theme", widthDp = 360, heightDp = 640)
@Composable
fun LightPreview() {
    MyTheme {
        MyApp()
    }
}

@Preview("Dark Theme", widthDp = 360, heightDp = 640)
@Composable
fun DarkPreview() {
    MyTheme(darkTheme = true) {
        MyApp()
    }
}
