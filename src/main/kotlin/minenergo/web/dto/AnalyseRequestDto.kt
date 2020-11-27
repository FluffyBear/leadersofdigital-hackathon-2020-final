package minenergo.web.dto

import minenergo.Industry
import java.time.YearMonth

class AnalyseRequestDto(
    val industries: List<Industry>,
    val energyConsumption: Industry,
    val predictionHorizon: YearMonth
)