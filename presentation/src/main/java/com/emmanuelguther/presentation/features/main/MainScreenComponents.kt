package com.emmanuelguther.presentation.features.main

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.twotone.DateRange
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.emmanuelguther.core_presentation.ui.theme.boxShapeDefault
import com.emmanuelguther.core_presentation.ui.theme.elevationDefault
import com.emmanuelguther.core_presentation.ui.utils.LinkViewModelLifecycle
import com.emmanuelguther.core_presentation.ui.utils.ViewModelGenericError
import com.emmanuelguther.core_presentation.ui.utils.ViewModelState
import com.emmanuelguther.features.R
import com.emmanuelguther.presentation.components.CircularIcon
import com.emmanuelguther.presentation.components.ErrorAlert
import com.emmanuelguther.presentation.components.FullScreenLoading
import com.emmanuelguther.presentation.components.LoadingText
import com.emmanuelguther.presentation.model.DayEnergyHistoric
import com.emmanuelguther.presentation.model.DaysEnergyHistoric
import com.emmanuelguther.presentation.model.HourEnergyHistoric
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest

@ExperimentalCoroutinesApi
@ExperimentalComposeUiApi
@Composable
fun MainScreen(viewModel: MainViewModel, onNavigateToDetail: () -> Unit) {
    LinkViewModelLifecycle(viewModel)

    val state by viewModel.state.collectAsState(initial = ViewModelState.initialState)
    Content(state, viewModel)
    Effects(viewModel, onNavigateToDetail)
}

@ExperimentalCoroutinesApi
@Composable
private fun Content(state: ViewModelState<out MainViewModel.State, ViewModelGenericError>, viewModel: MainViewModel) {
    Surface(Modifier.fillMaxSize()) {
        when {
            state.loading() -> FullScreenLoading()
            state is ViewModelState.Loaded -> {
                RenderContent(state.content)
            }
            state is ViewModelState.Error -> {
                ErrorAlert(state.error.toString(),
                    stringResource(R.string.retry), action = { viewModel.setEvent(MainViewModel.Event.OnErrorRetry) }
                )
            }
        }
    }
}

@ExperimentalCoroutinesApi
@Composable
private fun Effects(viewModel: MainViewModel, onNavigateToMain: () -> Unit) {
    LaunchedEffect(key1 = Unit) {
        viewModel.effect.collectLatest { value ->
            when (value) {
                MainViewModel.Effect.NavigateToDetail -> onNavigateToMain()
            }
        }
    }
}

@ExperimentalCoroutinesApi
@Composable
private fun RenderContent(content: MainViewModel.State) {
    val selectedDayState = remember { mutableStateOf(content.daysEnergyHistoric.first()) }

    Column(Modifier.fillMaxSize()) {
        DaysTab(content.daysEnergyHistoric) { selectedDayState.value = it }
        HoursHorizontalList(Modifier, selectedDayState.value.hourEnergyHistoric) { Dashboard(it) }
    }
}

@Composable
private fun DaysTab(daysEnergyHistoric: DaysEnergyHistoric, selectedDay: (DayEnergyHistoric) -> Unit) {
    var tabIndex by remember { mutableStateOf(0) }
    val tabData = daysEnergyHistoric.toList()
    Card(
        elevation = elevationDefault, modifier = Modifier
            .padding(8.dp), shape = boxShapeDefault
    ) {
        Box(
            modifier = Modifier
                .clip(shape = boxShapeDefault)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            MaterialTheme.colors.primary,
                            MaterialTheme.colors.primary.copy(0.2f)
                        )
                    )
                )
        ) {
            TabRow(
                selectedTabIndex = tabIndex,
                backgroundColor = MaterialTheme.colors.primary.copy(alpha = 0.0f),
                indicator = {
                    Box(
                        Modifier
                            .tabIndicatorOffset(it[tabIndex])
                            .height(2.dp)
                            .border(1.dp, MaterialTheme.colors.primary)
                    )
                }) {
                tabData.forEachIndexed { index, item ->
                    val selected = tabIndex == index
                    Tab(selected = selected, onClick = {
                        selectedDay(tabData[index])
                        tabIndex = index
                    }) {

                        Row(
                            modifier = Modifier
                                .padding(8.dp)
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            CircularIcon(
                                Modifier.align(Alignment.CenterVertically),
                                if (selected) Icons.Filled.DateRange else Icons.TwoTone.DateRange,
                                "date icon"
                            )
                            Text(
                                text = item.readableDate,
                                style = MaterialTheme.typography.overline,
                                modifier = Modifier
                                    .align(Alignment.CenterVertically)
                                    .padding(start = 4.dp),
                                color = Color.DarkGray
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun HoursHorizontalList(
    modifier: Modifier = Modifier,
    data: List<HourEnergyHistoric>,
    renderDashboard: (@Composable (HourEnergyHistoric) -> Unit)
) {
    val selectedHourState = remember { mutableStateOf(0) }
    Card(
        elevation = elevationDefault,
        shape = boxShapeDefault,
        modifier = Modifier.padding(8.dp)
    ) {
        LazyRow(
            modifier = modifier.background(MaterialTheme.colors.secondary.copy(alpha = 0.4f)),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(horizontal = 8.dp)
        ) {

            items(data) {
                val selected = (selectedHourState.value == data.indexOf(it))
                Box(
                    modifier = Modifier
                        .padding(8.dp)
                        .clip(shape = boxShapeDefault)
                        .background(if (selected) MaterialTheme.colors.secondary else Color.White)
                        .clickable {
                            selectedHourState.value = data.indexOf(it)
                        },

                    ) {
                    Text(
                        modifier = modifier.padding(8.dp),
                        text = it.hour.toString(),
                        style = MaterialTheme.typography.overline,
                        color = if (selected) Color.White else Color.Black
                    )
                }
            }
        }
    }
    renderDashboard(data[selectedHourState.value])
}

@Composable
private fun Dashboard(hourEnergyHistoric: HourEnergyHistoric) {
    Column(Modifier.fillMaxSize()) {

        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            QuasarEnergy(
                modifier = Modifier
                    .padding(8.dp, 16.dp)
                    .clip(boxShapeDefault)
                    .background(MaterialTheme.colors.secondary.copy(alpha = 0.1f)),
                """${stringResource(R.string.quasar)} -""",
                stringResource(R.string.discharged_quasar),
                """${hourEnergyHistoric.dischargedFromQuasar} kwh"""
            )
            QuasarEnergy(
                modifier = Modifier
                    .padding(8.dp, 16.dp)
                    .clip(boxShapeDefault)
                    .background(MaterialTheme.colors.secondary.copy(alpha = 0.1f)),
                """${stringResource(R.string.quasar)} +""",
                stringResource(R.string.charged_quasar),
                """${hourEnergyHistoric.chargedFromQuasar} kwh"""
            )
        }

        Surface(
            modifier = Modifier
                .padding(8.dp)
                .clip(boxShapeDefault)
                .fillMaxWidth(),
            elevation = elevationDefault
        ) {
            Box(
                modifier = Modifier
                    .padding(8.dp)
                    .background(MaterialTheme.colors.secondaryVariant.copy(alpha = 0.1f))
                    .clip(boxShapeDefault)
            ) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    ItemEnergy(stringResource(R.string.from_solar), hourEnergyHistoric.solarPercent.toString(), "%")
                    ItemEnergy(stringResource(R.string.from_grid), hourEnergyHistoric.gridPercent.toString(), "%")
                    ItemEnergy(stringResource(R.string.from_quasar), hourEnergyHistoric.quasarPercent.toString(), "%")
                    ItemEnergy(stringResource(R.string.total), hourEnergyHistoric.buildingActivePower.toString(), "kwh")
                }
            }
        }
    }
}

@Composable
private fun QuasarEnergy(modifier: Modifier, title: String, subtitle: String, value: String) {

    Column(modifier = modifier) {
        Text(
            modifier = Modifier
                .padding(top = 8.dp)
                .align(Alignment.CenterHorizontally),
            text = title,
            style = MaterialTheme.typography.body2.copy(fontWeight = FontWeight.Bold),
            color = Color.Black
        )
        Text(
            modifier = Modifier.padding(8.dp, 0.dp),
            text = subtitle,
            style = MaterialTheme.typography.overline.copy(fontSize = 12.sp),
            color = Color.LightGray
        )

        Text(
            modifier = Modifier
                .padding(24.dp)
                .clip(shape = boxShapeDefault)
                .align(Alignment.CenterHorizontally)
                .border(BorderStroke(2.dp, MaterialTheme.colors.secondary))
                .padding(8.dp),
            text = value,
            style = MaterialTheme.typography.h5,
            color = Color.Black
        )
    }
}


@Composable
private fun ItemEnergy(title: String, value: String, symbol: String = "") {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            modifier = Modifier
                .align(Alignment.CenterVertically),
            text = title,
            style = MaterialTheme.typography.body2,
            color = Color.Black
        )
        Text(
            modifier = Modifier
                .clip(shape = boxShapeDefault)
                .align(Alignment.CenterVertically)
                .border(BorderStroke(2.dp, MaterialTheme.colors.secondary))
                .padding(8.dp),
            text = """$value $symbol""",
            style = MaterialTheme.typography.h5,
            color = Color.Black
        )
    }
}

@Preview(showBackground = true, device = Devices.NEXUS_5, showSystemUi = true)
@Composable
fun DefaultPreview() {
}