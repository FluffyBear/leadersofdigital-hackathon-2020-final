package minenergo.web.dto

import minenergo.Industry
import java.time.YearMonth

class AnalyseRequestDto(
    val industries: MutableList<Industry>,
    val energyConsumption: Industry,
    val predictionHorizon: YearMonth
)