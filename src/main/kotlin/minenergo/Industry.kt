package minenergo

import java.time.YearMonth
import java.util.*

data class Industry(
    val name: String,
    val power: SortedMap<YearMonth,Double>
)