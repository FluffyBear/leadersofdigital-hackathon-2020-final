package minenergo.web

import minenergo.Industry
import minenergo.misc.YearMonthProgression
import org.springframework.stereotype.Component

@Component
class Interpolation {
    fun interpolate(industry: Industry, yearMonthProgression: YearMonthProgression) {
        if (!isIntervalComplete(industry, yearMonthProgression)) {
            fillGaps(industry, yearMonthProgression)
        }
    }

    fun isIntervalComplete(industry: Industry, yearMonthProgression: YearMonthProgression): Boolean {
        yearMonthProgression.forEach {
            if (!industry.power.keys.contains(it)) {
                return false
            }
        }
        return true
    }

    private fun fillGaps(industry: Industry, yearMonthProgression: YearMonthProgression) {
        val value = calculateValue(industry)
        yearMonthProgression.forEach {
            if (!industry.power.keys.contains(it)) {
                industry.power[it] = value
            }
        }
    }

    fun calculateValue(industry: Industry): Double {
        return industry.power.values.average()
    }
}