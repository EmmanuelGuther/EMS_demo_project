package com.emmanuelguther.presentation.features.detail

import android.util.Log
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.emmanuelguther.core_presentation.ui.theme.boxShapeDefault
import com.emmanuelguther.core_presentation.ui.utils.LinkViewModelLifecycle
import com.emmanuelguther.core_presentation.ui.utils.ViewModelGenericError
import com.emmanuelguther.core_presentation.ui.utils.ViewModelState
import com.emmanuelguther.features.R
import com.emmanuelguther.presentation.components.AnimatedText
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlin.math.min

@ExperimentalCoroutinesApi
@ExperimentalComposeUiApi
@Composable
fun DetailScreen(viewModel: DetailViewModel, onBack: () -> Unit) {
    LinkViewModelLifecycle(viewModel)
    val state by viewModel.state.collectAsState(initial = ViewModelState.initialState)
    Content(state, onBack)
}

@ExperimentalCoroutinesApi
@Composable
private fun Content(state: ViewModelState<out DetailViewModel.State, ViewModelGenericError>, onBack: () -> Unit) {
    val scaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Open))

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(title = { Text("TopAppBar") },
                navigationIcon = {
                    IconButton(onClick = { onBack.invoke() }) {
                        Icon(Icons.Filled.ArrowBack, "backIcon")
                    }
                }, backgroundColor = MaterialTheme.colors.secondary
            )
        },
        content = {
            Surface(Modifier.fillMaxSize()) {
                when {
                    state.loading() -> Log.i("LOADING", "LOADING")
                    state is ViewModelState.Loaded -> {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                        ) {
                            Column(
                                modifier = Modifier
                                    .align(Alignment.TopCenter)
                                    .padding(8.dp)
                                    .clip(boxShapeDefault)
                                    .background(MaterialTheme.colors.primary.copy(alpha = 0.1f))
                            ) {
                                AnimatedText(
                                    stringResource(R.string.percent_chart),
                                    Modifier
                                        .padding(top = 8.dp)
                                        .align(Alignment.CenterHorizontally), 700,
                                    Color.Black, MaterialTheme.typography.body2.copy(fontWeight = FontWeight.Bold)
                                )
                                BarCharts(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(150.dp)
                                        .padding(8.dp),
                                    yAxisValues = listOf(state.content.solar, state.content.grid, state.content.quasar)
                                )
                                Row(
                                    Modifier
                                        .fillMaxWidth()
                                        .padding(4.dp), horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(
                                        modifier = Modifier
                                            .align(Alignment.CenterVertically),
                                        text = """${stringResource(R.string.solar)} ${state.content.solar} kwh""",
                                        style = MaterialTheme.typography.body2,
                                        color = Color.Black
                                    )
                                    Text(
                                        modifier = Modifier
                                            .align(Alignment.CenterVertically),
                                        text = """${stringResource(R.string.grid)} ${state.content.grid} kwh""",
                                        style = MaterialTheme.typography.body2,
                                        color = Color.Black
                                    )
                                    Text(
                                        modifier = Modifier
                                            .align(Alignment.CenterVertically),
                                        text = """${stringResource(R.string.quasar)} ${state.content.quasar} kwh""",
                                        style = MaterialTheme.typography.body2,
                                        color = Color.Black
                                    )
                                }
                            }
                        }
                    }
                    state is ViewModelState.Error -> Log.e("ERROR", "ERROR")
                }
            }
        },
    )


}


@Composable
fun BarCharts(
    modifier: Modifier = Modifier,
    barColors: List<Color> = listOf(MaterialTheme.colors.primary, MaterialTheme.colors.primary),
    barWidth: Float = 20f,
    yAxisValues: List<Float>,
    shouldAnimate: Boolean = true
) {
    val x = remember { Animatable(0f) }
    val yValues = remember { yAxisValues }
    val xTarget = (yValues.size - 1).toFloat()
    LaunchedEffect(Unit) {
        x.animateTo(
            targetValue = xTarget,
            animationSpec = tween(
                durationMillis = if (shouldAnimate) 500 else 0,
                easing = LinearEasing
            ),
        )
    }

    Canvas(modifier = modifier.padding(horizontal = 8.dp)) {
        val xBounds = Pair(0f, xTarget)
        val yBounds = getBounds(yValues)
        val scaleX = size.width / (xBounds.second - xBounds.first)
        val scaleY = size.height / (yBounds.second - yBounds.first)
        val yMove = yBounds.first * scaleY

        (0..min(yValues.size - 1, x.value.toInt())).forEach { value ->
            val xOffset = value * scaleX
            val yOffset = size.height - (yValues[value] * scaleY) + yMove
            drawBar(
                topLeft = Offset(xOffset, yOffset),
                width = barWidth,
                height = size.height - yOffset,
                barColors
            )
        }
    }
}


fun DrawScope.drawBar(topLeft: Offset, width: Float, height: Float, colors: List<Color>) {
    drawRect(
        topLeft = topLeft,
        brush = Brush.linearGradient(colors),
        size = Size(width, height)
    )
}


fun getBounds(list: List<Float>): Pair<Float, Float> {
    var min = Float.MAX_VALUE
    var max = -Float.MAX_VALUE
    list.forEach {
        min = min.coerceAtMost(it)
        max = max.coerceAtLeast(it)
    }
    return Pair(min, max)
}
