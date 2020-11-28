package minenergo

import minenergo.misc.YearMonthProgression
import minenergo.misc.rangeTo
import minenergo.web.DataFiller
import org.apache.commons.math3.stat.regression.OLSMultipleLinearRegression
import org.springframework.stereotype.Component
import java.time.YearMonth

@Component
class Math {
    private val dataFiller = DataFiller()

    fun analyse(
        industries: MutableList<Industry>,
        industryToPredict: Industry,
        predictionHorizon: YearMonth
    ): Industry {
        dataFiller.fillData(industries, YearMonthProgression(startDate(industries), predictionHorizon.minusMonths(1)))
        return OLSMultipleLinearRegression().let { regression ->
            regression.newSampleData(
                industryToPredict.power.map { it.value }.toDoubleArray(),
                industries.first().power.firstKey().rangeTo(industryToPredict.power.lastKey()).map { yearMonth ->
                    industries.map { it.power.getValue(yearMonth) }.toDoubleArray()
                }.toTypedArray()
            )
            Industry(
                name = industryToPredict.name,
                power = industryToPredict.power.firstKey().rangeTo(predictionHorizon).associateWith { yearMonth ->
                    (industryToPredict.power[yearMonth] ?: regression.estimateRegressionParameters().reduceIndexed { idx, acc, p ->
                        acc + p * industries[idx - 1].power.getValue(yearMonth)
                    })
                }.toSortedMap()
            )
        }
    }

    fun startDate(industries: MutableList<Industry>): YearMonth {
        return industries.map { industry -> industry.power.keys }.map { set -> set.min() }
            .minBy { it as YearMonth } as YearMonth
    }
}