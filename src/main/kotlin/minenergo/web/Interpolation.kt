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
        for (i in 0 until dates.size - 1) {
            fillRange(industry, YearMonthProgression(dates[i], dates[i + 1]))
        }
        fillLast(
            industry,
            YearMonthProgression(dates[dates.size - 1], dates.last()),
            yearMonthProgression.endInclusive
        )
    }

    fun fillRange(industry: Industry, yearMonthProgression: YearMonthProgression) {
        yearMonthProgression.forEach {
            industry.power[it] = industry.power[yearMonthProgression.start]?.plus(
                (industry.power[yearMonthProgression.endInclusive]?.minus(
                    industry.power[yearMonthProgression.start] as Double
                )!!).div(
                    (yearMonthProgression.endInclusive.year * 12 + yearMonthProgression.endInclusive.monthValue) -
                            (yearMonthProgression.start.year * 12 + yearMonthProgression.start.monthValue)
                )
                    .times((it.year * 12 + it.monthValue) - (yearMonthProgression.start.year * 12 + yearMonthProgression.start.monthValue))
            )
        }
    }

    fun fillLast(industry: Industry, yearMonthProgression: YearMonthProgression, horizon: YearMonth) {
        YearMonthProgression(yearMonthProgression.start, horizon).forEach {
            if (!industry.power.keys.contains(it))
                industry.power[it] = industry.power[yearMonthProgression.start]?.plus(
                    (industry.power[yearMonthProgression.endInclusive]?.minus(
                        industry.power[yearMonthProgression.start] as Double
                    )!!).div(
                        (yearMonthProgression.endInclusive.year * 12 + yearMonthProgression.endInclusive.monthValue) -
                                (yearMonthProgression.start.year * 12 + yearMonthProgression.start.monthValue)
                    )
                        .times((it.year * 12 + it.monthValue) - (yearMonthProgression.start.year * 12 + yearMonthProgression.start.monthValue))
                )
        }
    }
}