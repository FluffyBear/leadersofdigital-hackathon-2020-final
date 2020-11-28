package minenergo

import kotlinx.serialization.ContextualSerialization
import kotlinx.serialization.Serializable
import java.time.YearMonth
import java.util.*

@Serializable
data class Industry(
    val name: String,
    val power: SortedMap<@ContextualSerialization YearMonth,Double>
)