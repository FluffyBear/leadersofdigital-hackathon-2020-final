package minenergo.web

import minenergo.Industry
import minenergo.misc.YearMonthProgression
import minenergo.web.dto.AnalyseRequestDto

class DataFiller {
    fun fillData(analyseRequestDto: AnalyseRequestDto, yearMonthProgression: YearMonthProgression) {
        if (isIntervalNotComplete(analyseRequestDto, yearMonthProgression)) {
            fillGaps(analyseRequestDto, yearMonthProgression)
        }
    }

    private fun isIntervalNotComplete(
        analyseRequestDto: AnalyseRequestDto,
        yearMonthProgression: YearMonthProgression
    ): Boolean {
        yearMonthProgression.forEach {
            if (!analyseRequestDto.industries.map { industry -> industry.power.firstKey() }.contains(it))
                return false
        }
        return true
    }

    private fun fillGaps(analyseRequestDto: AnalyseRequestDto, yearMonthProgression: YearMonthProgression) {
        val value = calculateValue(analyseRequestDto, yearMonthProgression)
        yearMonthProgression.forEach {
            if (!analyseRequestDto.industries.map { industry -> industry.power.firstKey() }.contains(it))
                analyseRequestDto.industries.add(Industry("filler", sortedMapOf(Pair(it, value))))
        }
    }

    private fun calculateValue(
        analyseRequestDto: AnalyseRequestDto,
        yearMonthProgression: YearMonthProgression
    ): Double {
        val values = mutableListOf<Double>()
        yearMonthProgression.forEach { date ->
            val value = analyseRequestDto.industries.filter { industry -> industry.power.containsKey(date) }
            if (value.isNotEmpty()) {
                values.add(value.map { industry -> industry.power[date] }.sumByDouble { it as Double })
            }
        }
        return values.average()
    }
}