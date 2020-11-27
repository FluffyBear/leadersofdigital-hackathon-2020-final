package minenergo

import minenergo.misc.rangeTo
import org.apache.commons.math3.stat.regression.OLSMultipleLinearRegression
import java.time.YearMonth

class Math {

    fun analyse(industries: List<Industry>, industryToPredict: Industry, predictionHorizon: YearMonth): Industry =
        OLSMultipleLinearRegression().let { regression ->
            regression.newSampleData(
                industryToPredict.power.map { it.value }.toDoubleArray(),
                industries.first().let { industry ->
                    industry.power.firstKey().rangeTo(industryToPredict.power.lastKey()).map { yearMonth ->
                        industries.map { it.power.getValue(yearMonth) }.toDoubleArray()
                    }.toTypedArray()
                }
            )
            regression.estimateRegressionParameters().let { params ->
                Industry(
                    name = industryToPredict.name,
                    power = industryToPredict.power.firstKey().rangeTo(predictionHorizon).associateWith { yearMonth ->
                        (industryToPredict.power[yearMonth] ?: params.reduceIndexed { idx, acc, p ->
                                acc + p * industries[idx - 1].power.getValue(yearMonth) })
                    }.toSortedMap()
                )
            }
        }
}