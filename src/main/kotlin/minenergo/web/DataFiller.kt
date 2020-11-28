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
        industries.forEach { industry ->
            yearMonthProgression.forEach {
                if (!industry.power.keys.contains(it)) {
                    return false
                }
            }
        }
        return true
    }

    private fun fillGaps(industries: MutableList<Industry>, yearMonthProgression: YearMonthProgression) {
        industries.forEach { industry ->
            val value = calculateValue(industry)
            yearMonthProgression.forEach {
                if (!industry.power.keys.contains(it)) {
                    industry.power[it] = value
                }
            }
        }
    }

    fun calculateValue(industry: Industry): Double {
        return industry.power.values.average()
    }
}