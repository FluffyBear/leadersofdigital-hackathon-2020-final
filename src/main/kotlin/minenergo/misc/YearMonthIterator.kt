package minenergo.misc

import java.time.YearMonth

class YearMonthIterator (
    private val startDate: YearMonth,
    private val endDateInclusive: YearMonth
): Iterator<YearMonth> {
    private var currentDate = startDate

    override fun hasNext() = currentDate <= endDateInclusive

    override fun next(): YearMonth {
        val next = currentDate
        currentDate = currentDate.plusMonths(1)
        return next
    }

}