package minenergo.misc

import java.time.YearMonth

class YearMonthProgression(override val start: YearMonth,
                           override val endInclusive: YearMonth) :
    Iterable<YearMonth>, ClosedRange<YearMonth> {

    override fun iterator(): Iterator<YearMonth> =
        YearMonthIterator(start, endInclusive)
}

operator fun YearMonth.rangeTo(other: YearMonth) = YearMonthProgression(this, other)