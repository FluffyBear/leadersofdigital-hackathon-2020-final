package minenergo

import minenergo.misc.YearMonthProgression
import minenergo.misc.rangeTo
import minenergo.web.Interpolation
import org.apache.commons.math3.stat.regression.OLSMultipleLinearRegression
import org.springframework.stereotype.Component
import java.time.YearMonth

@Component
class Math {
    private val interpolator = Interpolation()

    fun analyse(
        industries: MutableList<Industry>,
        energyConsumption: Industry,
        predictionStart: YearMonth?,
        predictionHorizon: YearMonth
    ): Industry {
        industries.forEach {
            interpolator.interpolate(
                it,
                YearMonthProgression(startDate(industries), predictionHorizon)
            )
        }
        interpolator.interpolate(
            energyConsumption, YearMonthProgression(
                startDate(mutableListOf(energyConsumption)), endDate(
                    mutableListOf(energyConsumption)
                )
            )
        )
        return OLSMultipleLinearRegression().let { regression ->
            regression.newSampleData(
                energyConsumption.power.filter { predictionStart == null || it.key <= predictionStart }.map { it.value }.toDoubleArray(),
                industries.first().power.firstKey().rangeTo(predictionStart ?: energyConsumption.power.lastKey()).map { yearMonth ->
                    industries.map { it.power.getValue(yearMonth) }.toDoubleArray()
                }.toTypedArray()
            )
            Industry(
                name = energyConsumption.name,
                power = energyConsumption.power.firstKey().rangeTo(predictionHorizon).associateWith { yearMonth ->
                    energyConsumption.power[yearMonth]
                        ?.takeIf { predictionStart == null || yearMonth <= predictionStart }
                        ?: regression.estimateRegressionParameters()
                        .reduceIndexed { idx, acc, p ->
                            acc + p * industries[idx - 1].power.getValue(yearMonth)
                        }
                }.toSortedMap()
            )
        }
    }

    fun startDate(industries: MutableList<Industry>): YearMonth {
        return industries.map { industry -> industry.power.keys }.map { set -> set.min() }
            .minBy { it as YearMonth } as YearMonth
    }

    fun endDate(industries: MutableList<Industry>): YearMonth {
        return industries.map { industry -> industry.power.keys }.map { set -> set.max() }
            .maxBy { it as YearMonth } as YearMonth
    }
}