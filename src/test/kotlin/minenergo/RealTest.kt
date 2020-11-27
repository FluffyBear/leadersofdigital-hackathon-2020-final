package minenergo

import minenergo.web.dto.AnalyseRequestDto
import java.time.YearMonth

class RealTest {

    fun generateRealData(): AnalyseRequestDto {
        val energyConsumption = sortedMapOf<YearMonth, Double>()
        for (i in 1..12) {
            energyConsumption[YearMonth.of(2015, i)] = 531.2
            energyConsumption[YearMonth.of(2016, i)] = 536.1
            energyConsumption[YearMonth.of(2017, i)] = 620.4
//            energyConsumption[YearMonth.of(2018, i)] = 763.6
        }
        val industries = mutableListOf<Industry>()
        industries.add(
            Industry("Population",
                (1..12).flatMap { i ->
                    listOf(
                        YearMonth.of(2015, i) to 280.5,
                        YearMonth.of(2016, i) to 278.7,
                        YearMonth.of(2017, i) to 277.8,
                        YearMonth.of(2018, i) to 272.6
                    )
                }.toMap().toSortedMap()
            )
        )
        return AnalyseRequestDto(
            industries = industries,
            energyConsumption = Industry("oneBigIndustry", energyConsumption),
            predictionHorizon = YearMonth.of(2018, 12)
        )
    }
}