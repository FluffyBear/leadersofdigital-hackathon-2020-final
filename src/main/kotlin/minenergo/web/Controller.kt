package minenergo.web

import minenergo.Math
import minenergo.web.dto.AnalyseRequestDto
import minenergo.web.dto.AnalyseResponseDto
import mu.KLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.web.bind.annotation.*

@RestController
@EnableAutoConfiguration
@RequestMapping
class Controller @Autowired constructor(
    val math: Math
) {
    companion object : KLogging()

    @GetMapping(path = ["/analyse"])
    fun analyse(request: AnalyseRequestDto) : AnalyseResponseDto {
        return AnalyseResponseDto(math.analyse(request.industries, request.energyConsumption, request.predictionHorizon))
    }
}