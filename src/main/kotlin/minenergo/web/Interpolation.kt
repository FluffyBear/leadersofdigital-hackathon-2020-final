package minenergo.web

import minenergo.Industry
import minenergo.misc.YearMonthProgression
import org.springframework.stereotype.Component
import java.time.YearMonth

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
        val dates = industry.power.keys.toList()
        fillFirstRange(industry, YearMonthProgression(yearMonthProgression.start, dates.first()))
        for (i in 0 until dates.size - 1) {
            fillRange(industry, YearMonthProgression(dates[i], dates[i + 1]))
        }
        fillLastRange(
            industry, YearMonthProgression(dates[dates.size - 1], dates.last()), yearMonthProgression.endInclusive
        )
    }

    fun fillRange(industry: Industry, yearMonthProgression: YearMonthProgression) {
        yearMonthProgression.forEach {
            industry.power[it] = calculateFunction(industry, yearMonthProgression, it)
        }
    }

    private fun fillFirstRange(industry: Industry, yearMonthProgression: YearMonthProgression) {
        yearMonthProgression.forEach {
            if (!industry.power.keys.contains(it))
                industry.power[it] = industry.power.getValue(yearMonthProgression.endInclusive)
        }
    }

    private fun fillLastRange(industry: Industry, yearMonthProgression: YearMonthProgression, horizon: YearMonth) {
        YearMonthProgression(yearMonthProgression.start, horizon).forEach {
            if (!industry.power.keys.contains(it))
                industry.power[it] = calculateFunction(industry, yearMonthProgression, it)
        }
    }

    private fun calculateFunction(
        industry: Industry,
        yearMonthProgression: YearMonthProgression,
        currentDate: YearMonth
    ): Double {
        return industry.power[yearMonthProgression.start]?.plus(
            (industry.power[yearMonthProgression.endInclusive]?.minus(
                industry.power[yearMonthProgression.start] as Double
            )!!).div(
                (yearMonthProgression.endInclusive.year * 12 + yearMonthProgression.endInclusive.monthValue) -
                        (yearMonthProgression.start.year * 12 + yearMonthProgression.start.monthValue)
            )
                .times((currentDate.year * 12 + currentDate.monthValue) - (yearMonthProgression.start.year * 12 + yearMonthProgression.start.monthValue))
        ) as Double
    }
}