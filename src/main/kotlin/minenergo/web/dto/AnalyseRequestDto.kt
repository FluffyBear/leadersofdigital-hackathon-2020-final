package minenergo.web.dto

import minenergo.Industry
import java.time.YearMonth

class AnalyseRequestDto(
    val industries: MutableList<Industry>,
    val energyConsumption: Industry,
    val predictionStart: YearMonth?,
    val predictionHorizon: YearMonth
)