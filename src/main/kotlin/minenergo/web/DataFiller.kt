package minenergo.web

import minenergo.Industry
import minenergo.misc.YearMonthProgression
import org.springframework.stereotype.Component

@Component
class DataFiller {
    fun fillData(industries: MutableList<Industry>, yearMonthProgression: YearMonthProgression) {
        if (!isIntervalComplete(industries, yearMonthProgression)) {
            fillGaps(industries, yearMonthProgression)
        }
    }

    fun isIntervalComplete(industries: MutableList<Industry>, yearMonthProgression: YearMonthProgression): Boolean {
        yearMonthProgression.forEach {
            if (!industries.map { industry -> industry.power.keys }.any { set -> set.contains(it) }) {
                return false
            }
        }
        return true
    }

    private fun fillGaps(industries: MutableList<Industry>, yearMonthProgression: YearMonthProgression) {
        val value = calculateValue(industries, yearMonthProgression)
        yearMonthProgression.forEach {
            if (!industries.map { industry -> industry.power.keys }.any { set -> set.contains(it) }) {
                industries.add(Industry("filler", sortedMapOf(Pair(it, value))))
            }
        }
    }

    fun calculateValue(industries: MutableList<Industry>, yearMonthProgression: YearMonthProgression): Double {
        val values = mutableListOf<Double>()
        yearMonthProgression.forEach { date ->
            val value = industries.filter { industry -> industry.power.containsKey(date) }
            if (value.isNotEmpty()) {
                values.add(value.map { industry -> industry.power[date] }.sumByDouble { it as Double })
            }
        }
        return values.average()
    }
}