package com.emmanuelguther.presentation.mapper

import com.emmanuelguther.domain.entity.HistoricEntity
import com.emmanuelguther.domain.entity.HistoricItemEntity
import com.emmanuelguther.presentation.model.DayEnergyHistoric
import com.emmanuelguther.presentation.model.DaysEnergyHistoric
import com.emmanuelguther.presentation.model.HourEnergyHistoric
import com.emmanuelguther.presentation.utils.getDateFromTimestamp
import com.emmanuelguther.presentation.utils.timeStampToDateTruncatedToHours


fun HistoricEntity.toDaysEnergyHistoric(): DaysEnergyHistoric {
    val energyGroupedYMDH = this.groupByYMDH()

    return energyGroupedYMDH.fold(mutableListOf()) { acc, item ->
        val actualDate = item.timestampYDMH.getDateFromTimestamp()

        if (acc.firstOrNull { it.day == actualDate.dayOfMonth } != null) {

            val actual = acc.firstOrNull { it.day == actualDate.dayOfMonth }

            actualDate?.let {
                val actualHourEnergyHistoric = actual?.hourEnergyHistoric?.toMutableList()
                actualHourEnergyHistoric?.add(
                    HourEnergyHistoric(
                        hour = actualDate.hour,
                        buildingActivePower = item.energy.buildingActivePower,
                        gridActivePower = item.energy.gridActivePower,
                        pvActivePower = item.energy.pvActivePower,
                        quasarsActivePower = item.energy.quasarsActivePower,
                        timestampYMDH = item.timestampYDMH
                    )
                )
                val updated = actualHourEnergyHistoric?.toList()?.let { it1 ->
                    actual.copy(hourEnergyHistoric = it1)
                }

                val index = acc.indexOfFirst { it.day == actualDate.dayOfMonth }

                if (updated != null) {
                    acc[index] = updated
                }
            }
        } else {
            actualDate?.let {
                acc.add(
                    DayEnergyHistoric(
                        year = actualDate.year,
                        month = actualDate.monthValue,
                        day = actualDate.dayOfMonth,
                        hourEnergyHistoric = listOf(
                            HourEnergyHistoric(
                                timestampYMDH = item.timestampYDMH,
                                hour = actualDate.hour,
                                buildingActivePower = item.energy.buildingActivePower,
                                gridActivePower = item.energy.gridActivePower,
                                pvActivePower = item.energy.pvActivePower,
                                quasarsActivePower = item.energy.quasarsActivePower
                            )
                        )
                    )
                )
            }

        }

        acc
    }
}

data class HistoricItemHelper(val timestampYDMH: Long, val energy: HistoricItemEntity)

/**
 * Group all energy values in year-month-day-hour objects, for example if we had values for the day 2022-1-1 T01 and objects for each minute of that hour (60) we add the 60 values and a co object is returned.
 * YMDH = year month day hour
 */
fun HistoricEntity.groupByYMDH(): List<HistoricItemHelper> {

    /**
     * Transform the data (we remove the minutes from the timestamp to group it by year-month-day-time) to a hashmap with key (year-month-day-time)
     */
    val ymdhMillisTimeStamp = this.ymdhInMillisTimeStamp()

    val groupDayHour = this.groupBy { it.timestamp.timeStampToDateTruncatedToHours() }

    //FOR TEST
    //val g2 = group.values.last().map { it.buildingActivePower }.sumOf { it } / group.values.last().size

    /**
     * Group by day-hour and in each iteration we add the energy values to each day-hour we already have in the list that matches its iterated value.
     */
    val historicDayHour = ymdhMillisTimeStamp.fold(mutableListOf<HistoricItemHelper>()) { acc, item ->
        item.forEach { it2 ->
            if (acc.firstOrNull { it.timestampYDMH == it2.key } != null) {
                val actual = acc.firstOrNull { it.timestampYDMH == it2.key }
                val updated = actual!!.energy.copy(
                    buildingActivePower = (actual.energy.buildingActivePower + it2.value.buildingActivePower),
                    gridActivePower = (actual.energy.gridActivePower + it2.value.gridActivePower),
                    pvActivePower = (actual.energy.pvActivePower + it2.value.pvActivePower),
                    quasarsActivePower = (actual.energy.quasarsActivePower + it2.value.quasarsActivePower)
                )
                val index = acc.indexOfFirst { it.timestampYDMH == it2.key }
                acc[index] = (HistoricItemHelper(it2.key, updated))
            } else {
                acc.add(HistoricItemHelper(it2.key, it2.value))
            }
        }
        acc
    }


    return transformToAveragedEnergyValues(historicDayHour, groupDayHour)
}

//Sum values in day-hour
private fun transformToAveragedEnergyValues(
    historicDayHour: MutableList<HistoricItemHelper>,
    groupDayHour: Map<Long, List<HistoricItemEntity>>
): List<HistoricItemHelper> {
    val average: (Double, Long) -> Double = { value, key -> value / (groupDayHour[key]?.count() ?: 0) }

    return historicDayHour.map {
        HistoricItemHelper(
            it.timestampYDMH, it.energy.copy(
                average(it.energy.buildingActivePower, it.timestampYDMH),
                average(it.energy.gridActivePower, it.timestampYDMH),
                average(it.energy.pvActivePower, it.timestampYDMH),
                average(it.energy.quasarsActivePower, it.timestampYDMH)
            )
        )
    }
}

/**
 * return hashmap of [HistoricItemEntity] grouped and with -y/m/d/h millis timestamp- key, from [HistoricEntity] removing minutes and seconds
 */
fun HistoricEntity.ymdhInMillisTimeStamp() = this.map { hashMapOf(it.timestamp.timeStampToDateTruncatedToHours() to it) }














