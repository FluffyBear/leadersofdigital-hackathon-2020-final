package minenergo.web.dto

import minenergo.Industry

class AnalyseRequestDto(
    val energyConsumption: Industry,
    val industries: List<Industry>
)