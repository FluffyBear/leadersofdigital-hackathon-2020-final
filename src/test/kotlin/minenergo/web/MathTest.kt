package minenergo.web

import junit.framework.Assert.assertEquals
import minenergo.Industry
import minenergo.Math
import org.junit.Test
import java.time.YearMonth

class MathTest {
    private val math = Math()

    @Test
    fun dataFillerIntegrationTest() {
        val consumption = Industry(
            "energy",
            (1..12).flatMap { i ->
                listOf(
                    YearMonth.of(2007, i) to 1015300.0,
                    YearMonth.of(2008, i) to 1040400.0,
                    YearMonth.of(2009, i) to 992000.0,
                    YearMonth.of(2010, i) to 1038000.0,
                    YearMonth.of(2011, i) to 1054800.0,
                    YearMonth.of(2012, i) to 1069300.0,
                    YearMonth.of(2013, i) to 1045015.5
                )
            }.toMap().toSortedMap()
        )
        val industries = mutableListOf<Industry>()
        industries.add(
            Industry(
                "alumni",
                (1..12).flatMap { i ->
                    listOf(
                        YearMonth.of(2007, i) to 1299.7,
                        YearMonth.of(2008, i) to 1660.8,
                        YearMonth.of(2009, i) to 1222.6,
                        YearMonth.of(2011, i) to 2031.8,
                        YearMonth.of(2012, i) to 2170.1,
                        YearMonth.of(2013, i) to 2230.6,
                        YearMonth.of(2014, i) to 2003.9,
                        YearMonth.of(2015, i) to 1326.3,
                        YearMonth.of(2016, i) to 1246.0,
                        YearMonth.of(2017, i) to 1267.0
                    )
                }.toMap().toSortedMap()
            )
        )
        val response = math.analyse(industries, consumption, YearMonth.of(2017, 12))
        assertEquals(1047447.3, response.power.getValue(YearMonth.of(2014, 12)), 1047447.3 / 20)
    }


    @Test
    fun startDate() {
        val testDto = mutableListOf(
            Industry(
                "1", sortedMapOf(
                    Pair(YearMonth.of(2010, 1), 1.0),
                    Pair(YearMonth.of(2010, 2), 2.0),
                    Pair(YearMonth.of(2010, 4), 2.0),
                    Pair(YearMonth.of(2010, 5), 3.0),
                    Pair(YearMonth.of(2010, 6), 1.0),
                    Pair(YearMonth.of(2010, 9), 3.0),
                    Pair(YearMonth.of(2010, 11), 2.0),
                    Pair(YearMonth.of(2010, 12), 2.0)
                )
            ),
            Industry(
                "2", sortedMapOf(
                    Pair(YearMonth.of(2010, 7), 1.0),
                    Pair(YearMonth.of(2010, 12), 1.0)
                )
            )
        )
        val secondTestDto = mutableListOf(
            Industry(
                "1", sortedMapOf(
                    Pair(YearMonth.of(2019, 1), 1.0),
                    Pair(YearMonth.of(2010, 2), 2.0),
                    Pair(YearMonth.of(2010, 4), 2.0),
                    Pair(YearMonth.of(2009, 5), 3.0),
                    Pair(YearMonth.of(2010, 6), 1.0),
                    Pair(YearMonth.of(2010, 9), 3.0),
                    Pair(YearMonth.of(2010, 11), 2.0),
                    Pair(YearMonth.of(2010, 12), 2.0)
                )
            ),
            Industry(
                "2", sortedMapOf(
                    Pair(YearMonth.of(2010, 7), 1.0),
                    Pair(YearMonth.of(2005, 12), 1.0)
                )
            )
        )
        assertEquals(YearMonth.of(2010, 1), math.startDate(testDto))
        assertEquals(YearMonth.of(2005, 12), math.startDate(secondTestDto))
    }
}