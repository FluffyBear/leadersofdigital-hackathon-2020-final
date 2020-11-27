package minenergo

import minenergo.misc.rangeTo
import minenergo.web.dto.AnalyseRequestDto
import org.junit.Test
import java.time.YearMonth

class SimpleTests {

    private val math = Math()

    @Test
    fun oneBigIndustry() {
        val request = generateOneBingIndustry()
        val response = math.analyse(request.industries, request.energyConsumption, request.predictionHorizon)

        YearMonth.of(2021, 1).rangeTo(YearMonth.of(2021, 12))
            .forEach {
                println("$it > ${response.power[it]}")
            }
    }

    @Test
    fun oneBigIndustryPlusSmallIndustry() {
        val request = generateOneBingIndustryPlusOneSmall()
        val response = math.analyse(request.industries, request.energyConsumption, request.predictionHorizon)

        YearMonth.of(2021, 1).rangeTo(YearMonth.of(2021, 12))
            .forEach {
                println("$it > ${response.power[it]}")
            }
    }

    @Test
    fun twoBigIndustries() {
        val request = generateTwoBigIndustries()
        val response = math.analyse(request.industries, request.energyConsumption, request.predictionHorizon)

        YearMonth.of(2021, 1).rangeTo(YearMonth.of(2021, 12))
            .forEach {
                println("$it > ${response.power[it]}")
            }
    }

    fun generateOneBingIndustry(): AnalyseRequestDto {
        val energyConsumption = sortedMapOf<YearMonth, Double>()
        for (i in 2010..2020) {
            energyConsumption[YearMonth.of(i, 1)] = 10500.0
            energyConsumption[YearMonth.of(i, 2)] = 11000.0
            energyConsumption[YearMonth.of(i, 3)] = 9800.0
            energyConsumption[YearMonth.of(i, 4)] = 9500.0
            energyConsumption[YearMonth.of(i, 5)] = 9000.0
            energyConsumption[YearMonth.of(i, 6)] = 8500.0
            energyConsumption[YearMonth.of(i, 7)] = 8400.0
            energyConsumption[YearMonth.of(i, 8)] = 8500.0
            energyConsumption[YearMonth.of(i, 9)] = 9100.0
            energyConsumption[YearMonth.of(i, 10)] = 9500.0
            energyConsumption[YearMonth.of(i, 11)] = 9800.0
            energyConsumption[YearMonth.of(i, 12)] = 10200.0
        }
        val industries = mutableListOf<Industry>()
        industries.add(
            Industry("1",
                (2010..2021).flatMap {i ->
                    listOf(
                        YearMonth.of(i, 1) to 10500.0,
                        YearMonth.of(i, 2) to 11000.0,
                        YearMonth.of(i, 3) to 9800.0,
                        YearMonth.of(i, 4) to 9500.0,
                        YearMonth.of(i, 5) to 9000.0,
                        YearMonth.of(i, 6) to 8500.0,
                        YearMonth.of(i, 7) to 8400.0,
                        YearMonth.of(i, 8) to 8500.0,
                        YearMonth.of(i, 9) to 9100.0,
                        YearMonth.of(i, 10) to 9500.0,
                        YearMonth.of(i, 11) to 9800.0,
                        YearMonth.of(i, 12) to 10200.0
                    )
                }.toMap().toSortedMap()
            )
        )

        return AnalyseRequestDto(
            industries = industries,
            energyConsumption = Industry("oneBigIndustry", energyConsumption),
            predictionHorizon = YearMonth.of(2021, 12)
        )
    }

    fun generateOneBingIndustryPlusOneSmall(): AnalyseRequestDto {
        val energyConsumption = sortedMapOf<YearMonth, Double>()
        for (i in 2010..2020) {
            energyConsumption[YearMonth.of(i, 1)] = 10500.0
            energyConsumption[YearMonth.of(i, 2)] = 11000.0
            energyConsumption[YearMonth.of(i, 3)] = 9800.0
            energyConsumption[YearMonth.of(i, 4)] = 9500.0
            energyConsumption[YearMonth.of(i, 5)] = 9000.0
            energyConsumption[YearMonth.of(i, 6)] = 8500.0
            energyConsumption[YearMonth.of(i, 7)] = 8400.0
            energyConsumption[YearMonth.of(i, 8)] = 8500.0
            energyConsumption[YearMonth.of(i, 9)] = 9100.0
            energyConsumption[YearMonth.of(i, 10)] = 9500.0
            energyConsumption[YearMonth.of(i, 11)] = 9800.0
            energyConsumption[YearMonth.of(i, 12)] = 10200.0
        }
        val industries = mutableListOf<Industry>()
        industries.add(
            Industry("1",
                (2010..2021).flatMap {i ->
                    listOf(
                        YearMonth.of(i, 1) to 10350.0,
                        YearMonth.of(i, 2) to 11000.0,
                        YearMonth.of(i, 3) to 9700.0,
                        YearMonth.of(i, 4) to 9400.0,
                        YearMonth.of(i, 5) to 9000.0,
                        YearMonth.of(i, 6) to 8400.0,
                        YearMonth.of(i, 7) to 8300.0,
                        YearMonth.of(i, 8) to 8350.0,
                        YearMonth.of(i, 9) to 9000.0,
                        YearMonth.of(i, 10) to 9350.0,
                        YearMonth.of(i, 11) to 9700.0,
                        YearMonth.of(i, 12) to 10100.0
                    )
                }.toMap().toSortedMap()
            )
        )
        industries.add(
            Industry("2",
                (2010..2021).flatMap {i ->
                    listOf(
                        YearMonth.of(i, 1) to 150.0,
                        YearMonth.of(i, 2) to 0.0,
                        YearMonth.of(i, 3) to 100.0,
                        YearMonth.of(i, 4) to 100.0,
                        YearMonth.of(i, 5) to 0.0,
                        YearMonth.of(i, 6) to 100.0,
                        YearMonth.of(i, 7) to 100.0,
                        YearMonth.of(i, 8) to 150.0,
                        YearMonth.of(i, 9) to 100.0,
                        YearMonth.of(i, 10) to 150.0,
                        YearMonth.of(i, 11) to 100.0,
                        YearMonth.of(i, 12) to 100.0
                    )
                }.toMap().toSortedMap()
            )
        )
        return AnalyseRequestDto(
            industries = industries,
            energyConsumption = Industry("oneBigIndustry", energyConsumption),
            predictionHorizon = YearMonth.of(2021, 12)
        )
    }

    fun generateTwoBigIndustries(): AnalyseRequestDto {
        val energyConsumption = sortedMapOf<YearMonth, Double>()
        for (i in 2010..2020) {
            energyConsumption[YearMonth.of(i, 1)] = 10500.0
            energyConsumption[YearMonth.of(i, 2)] = 11000.0
            energyConsumption[YearMonth.of(i, 3)] = 9800.0
            energyConsumption[YearMonth.of(i, 4)] = 9500.0
            energyConsumption[YearMonth.of(i, 5)] = 9000.0
            energyConsumption[YearMonth.of(i, 6)] = 8500.0
            energyConsumption[YearMonth.of(i, 7)] = 8400.0
            energyConsumption[YearMonth.of(i, 8)] = 8500.0
            energyConsumption[YearMonth.of(i, 9)] = 9100.0
            energyConsumption[YearMonth.of(i, 10)] = 9500.0
            energyConsumption[YearMonth.of(i, 11)] = 9800.0
            energyConsumption[YearMonth.of(i, 12)] = 10200.0
        }
        val industries = mutableListOf<Industry>()
        industries.add(
            Industry("1",
                (2010..2021).flatMap {i ->
                    listOf(
                        YearMonth.of(i, 1) to 10350.0,
                        YearMonth.of(i, 2) to 11000.0,
                        YearMonth.of(i, 3) to 9700.0,
                        YearMonth.of(i, 4) to 9400.0,
                        YearMonth.of(i, 5) to 9000.0,
                        YearMonth.of(i, 6) to 8400.0,
                        YearMonth.of(i, 7) to 8300.0,
                        YearMonth.of(i, 8) to 8350.0,
                        YearMonth.of(i, 9) to 9000.0,
                        YearMonth.of(i, 10) to 9350.0,
                        YearMonth.of(i, 11) to 9700.0,
                        YearMonth.of(i, 12) to 10100.0
                    )
                }.toMap().toSortedMap()
            )
        )
        industries.add(
            Industry("2",
                (2010..2021).flatMap {i ->
                    listOf(
                        YearMonth.of(i, 1) to 15000.0,
                        YearMonth.of(i, 2) to 000.0,
                        YearMonth.of(i, 3) to 10000.0,
                        YearMonth.of(i, 4) to 10000.0,
                        YearMonth.of(i, 5) to 000.0,
                        YearMonth.of(i, 6) to 10000.0,
                        YearMonth.of(i, 7) to 10000.0,
                        YearMonth.of(i, 8) to 15000.0,
                        YearMonth.of(i, 9) to 10000.0,
                        YearMonth.of(i, 10) to 15000.0,
                        YearMonth.of(i, 11) to 10000.0,
                        YearMonth.of(i, 12) to 10000.0
                    )
                }.toMap().toSortedMap()
            )
        )
        return AnalyseRequestDto(
            industries = industries,
            energyConsumption = Industry("oneBigIndustry", energyConsumption),
            predictionHorizon = YearMonth.of(2021, 12)
        )
    }
}