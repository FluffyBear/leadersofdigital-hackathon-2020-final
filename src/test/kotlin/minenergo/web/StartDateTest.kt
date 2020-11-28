package minenergo.web

import junit.framework.Assert.assertEquals
import minenergo.Industry
import minenergo.Math
import org.junit.Test
import java.time.YearMonth

class StartDateTest {
    private val math = Math()

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