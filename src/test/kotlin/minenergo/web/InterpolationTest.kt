package minenergo.web

import junit.framework.Assert.assertEquals
import minenergo.Industry
import minenergo.misc.YearMonthProgression
import org.junit.Test
import java.time.YearMonth

class InterpolationTest {
    private val dataFiller = Interpolation()

    @Test
    fun isDataComplete() {
        val completeTestDto = mutableListOf(
            Industry(
                "1", sortedMapOf(
                    Pair(YearMonth.of(2010, 1), 1.0),
                    Pair(YearMonth.of(2010, 2), 1.0),
                    Pair(YearMonth.of(2010, 3), 1.0),
                    Pair(YearMonth.of(2010, 4), 1.0),
                    Pair(YearMonth.of(2010, 5), 1.0)
                )
            )
        )
        val uncompleteTestDto = mutableListOf(
            Industry(
                "1", sortedMapOf(
                    Pair(YearMonth.of(2010, 1), 1.0),
                    Pair(YearMonth.of(2010, 2), 3.0),
                    Pair(YearMonth.of(2010, 4), 2.0),
                    Pair(YearMonth.of(2010, 5), 4.0)
                )
            )
        )
        completeTestDto.forEach {
            assertEquals(
                true,
                dataFiller.isIntervalComplete(
                    it,
                    YearMonthProgression(YearMonth.of(2010, 1), YearMonth.of(2010, 5))
                )
            )
        }
        uncompleteTestDto.forEach {
            assertEquals(
                false,
                dataFiller.isIntervalComplete(
                    it,
                    YearMonthProgression(YearMonth.of(2010, 1), YearMonth.of(2010, 5))
                )
            )
        }
    }

    @Test
    fun fillData() {
        val uncompleteTestDto = mutableListOf(
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
                    Pair(YearMonth.of(2010, 7), 2.0),
                    Pair(YearMonth.of(2010, 12), 3.0)
                )
            )
        )
        uncompleteTestDto.forEach {
            assertEquals(
                false,
                dataFiller.isIntervalComplete(
                    it,
                    YearMonthProgression(YearMonth.of(2010, 1), YearMonth.of(2010, 12))
                )
            )
        }
        uncompleteTestDto.forEach {
            dataFiller.interpolate(it, YearMonthProgression(YearMonth.of(2010, 1), YearMonth.of(2010, 12)))
        }
        uncompleteTestDto.forEach {
            assertEquals(
                true,
                dataFiller.isIntervalComplete(
                    it,
                    YearMonthProgression(YearMonth.of(2010, 1), YearMonth.of(2010, 12))
                )
            )
        }
        assertEquals(2.5, uncompleteTestDto[1].power[YearMonth.of(2010, 8)])
        assertEquals(2.0, uncompleteTestDto[1].power[YearMonth.of(2010, 7)])
    }
}