package minenergo.web

import com.google.common.io.Resources
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

    @PostMapping(path = ["/analyse"])
    fun analyse(@RequestBody request: AnalyseRequestDto) : AnalyseResponseDto {
        return AnalyseResponseDto(
            math.analyse(
                request.industries,
                request.energyConsumption,
                request.predictionStart,
                request.predictionHorizon
            )
        )
    }
}