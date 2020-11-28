package minenergo

import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals
import java.time.YearMonth

class VvpTest {

    private val math = Math()

    @Test
    fun energyVsVvp() {
        val consumption = Industry("energy",
            (1..12).flatMap { i ->
                listOf(
                    YearMonth.of(1990, i) to 1082200.0,
                    YearMonth.of(1991, i) to 1068200.0,
                    YearMonth.of(1992, i) to 1008500.0,
                    YearMonth.of(1993, i) to 956600.0,
                    YearMonth.of(1994, i) to 875900.0,
                    YearMonth.of(1995, i) to 860000.0,
                    YearMonth.of(1996, i) to 847200.0,
                    YearMonth.of(1997, i) to 834100.0,
                    YearMonth.of(1998, i) to 827200.0,
                    YearMonth.of(1999, i) to 846200.0,
                    YearMonth.of(2000, i) to 877800.0,
                    YearMonth.of(2001, i) to 891300.0,
                    YearMonth.of(2002, i) to 891000.0,
                    YearMonth.of(2003, i) to 916000.0,
                    YearMonth.of(2004, i) to 932000.0,
                    YearMonth.of(2005, i) to 953100.0,
                    YearMonth.of(2006, i) to 995800.0,
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
            Industry("VVP",
                (1..12).flatMap { i ->
                    listOf(
                        YearMonth.of(1990, i) to 571.0,
                        YearMonth.of(1991, i) to 560.5,
                        YearMonth.of(1992, i) to 490.1,
                        YearMonth.of(1993, i) to 458.2,
                        YearMonth.of(1994, i) to 408.5,
                        YearMonth.of(1995, i) to 399.5,
                        YearMonth.of(1996, i) to 392.1,
                        YearMonth.of(1997, i) to 404.9,
                        YearMonth.of(1998, i) to 271.0,
                        YearMonth.of(1999, i) to 195.9,
                        YearMonth.of(2000, i) to 259.7,
                        YearMonth.of(2001, i) to 306.6,
                        YearMonth.of(2002, i) to 345.5,
                        YearMonth.of(2003, i) to 430.3,
                        YearMonth.of(2004, i) to 590.9,
                        YearMonth.of(2005, i) to 764.0,
                        YearMonth.of(2006, i) to 989.9,
                        YearMonth.of(2007, i) to 1299.7,
                        YearMonth.of(2008, i) to 1660.8,
                        YearMonth.of(2009, i) to 1222.6,
                        YearMonth.of(2010, i) to 1524.9,
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
        val response = math.analyse(industries, consumption, null, YearMonth.of(2017,12))

        assertEquals(1047447.3, response.power.getValue(YearMonth.of(2014,12)), 1047447.3 / 20)
        assertEquals(1049904.9, response.power.getValue(YearMonth.of(2015,12)), 1049904.9 / 20)
        assertEquals(1071841.7, response.power.getValue(YearMonth.of(2016,12)), 1071841.7 / 10)
        assertEquals(1073724.9, response.power.getValue(YearMonth.of(2017,12)), 1073724.9 / 10)
    }
}