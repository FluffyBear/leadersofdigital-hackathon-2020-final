package minenergo


import com.google.common.io.Resources
import mu.KLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.web.bind.annotation.*

@RestController
@EnableAutoConfiguration
@RequestMapping
class Controller @Autowired constructor() {
    companion object : KLogging()

}