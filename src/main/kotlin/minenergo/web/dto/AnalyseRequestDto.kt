package minenergo.web.dto

import kotlinx.serialization.ContextualSerialization
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration
import minenergo.Industry
import java.time.YearMonth

@Serializable
class AnalyseRequestDto(
    val industries: MutableList<Industry>,
    val energyConsumption: Industry,
    @ContextualSerialization val predictionHorizon: YearMonth
) {
    companion object {
        private val json = Json(JsonConfiguration.Stable)

        fun parse(string: String) = json.parse(serializer(), string)
    }
}