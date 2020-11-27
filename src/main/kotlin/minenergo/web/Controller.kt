package minenergo.web

import minenergo.web.dto.VisualDataDto
import mu.KLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.web.bind.annotation.*

@RestController
@EnableAutoConfiguration
@RequestMapping
class Controller @Autowired constructor() {
    companion object : KLogging()

    @GetMapping(path = ["/analyse"])
    fun analyse() : VisualDataDto {
        return VisualDataDto()
    }
}