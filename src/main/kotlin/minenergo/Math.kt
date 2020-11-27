package minenergo

import minenergo.misc.rangeTo
import org.apache.commons.math3.stat.regression.OLSMultipleLinearRegression
import java.time.YearMonth

class Math {

    fun analyse(industries: List<Industry>, industryToPredict: Industry, predictionHorizon: YearMonth) : Industry {
        val regression = OLSMultipleLinearRegression()
        regression.newSampleData(
            industryToPredict.power.map { it.value }.toDoubleArray(),
            industries.first().let { industry ->
                industry.power.firstKey().rangeTo(industryToPredict.power.lastKey()).map {yearMonth ->
                    industries.map { it.power.getValue(yearMonth) }.toDoubleArray()
                }.toTypedArray()
            }
            /*
            industries
                .map { industry -> industry.power.map { it.value }
                .take(industryToPredict.power.size).toDoubleArray() }
                .toTypedArray()
             */
        )
        val params = regression.estimateRegressionParameters()

        params.forEach { println(it) }

        return Industry(
            name = industryToPredict.name,
            power = industryToPredict.power.firstKey().rangeTo(predictionHorizon).associateWith { yearMonth ->
                (industryToPredict.power[yearMonth]
                    ?: params.reduceIndexed { idx, acc, p -> acc +
                            p*industries[idx-1].power.getValue(yearMonth)
                    })
            }.toSortedMap()
        )
    }

    fun analyse() {
        val regression = OLSMultipleLinearRegression()
        // weight
        val y = doubleArrayOf(50.0, 60.0, 65.0, 65.0, 70.0, 75.0, 80.0, 85.0, 90.0, 95.0)
        // height, waist
        val x = arrayOfNulls<DoubleArray>(10)
        x[0] = doubleArrayOf(165.0, 65.0)
        x[1] = doubleArrayOf(170.0, 68.0)
        x[2] = doubleArrayOf(172.0, 70.0)
        x[3] = doubleArrayOf(175.0, 65.0)
        x[4] = doubleArrayOf(170.0, 80.0)
        x[5] = doubleArrayOf(172.0, 85.0)
        x[6] = doubleArrayOf(183.0, 78.0)
        x[7] = doubleArrayOf(187.0, 79.0)
        x[8] = doubleArrayOf(180.0, 95.0)
        x[9] = doubleArrayOf(185.0, 97.0)

        regression.newSampleData(y, x)

        val params = regression.estimateRegressionParameters()

        params.forEach { println(it) }

        val p = doubleArrayOf(190.0, 100.0)

        println("${p.joinToString(",")} > "+(params[0]+params[1]*p[0]+params[2]*p[1]))
    }
}

fun main(args: Array<String>) {
    Math().analyse()
}