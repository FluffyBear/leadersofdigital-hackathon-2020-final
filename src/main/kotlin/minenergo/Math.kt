package minenergo

import minenergo.misc.rangeTo
import org.apache.commons.math3.stat.regression.OLSMultipleLinearRegression
import org.springframework.stereotype.Component
import java.time.YearMonth

@Component
class Math {
    fun analyse(industries: List<Industry>, industryToPredict: Industry, predictionHorizon: YearMonth): Industry =
        OLSMultipleLinearRegression().let { regression ->
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
                            acc + p * industries[idx - 1].power.getValue(yearMonth) })
                }.toSortedMap()
            )
        }
}