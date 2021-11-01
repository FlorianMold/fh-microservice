package at.fh.microservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@SpringBootApplication
@RestController
class MicroserviceApplication {

	@GetMapping()
	fun index() = "Hello World"

}

fun main(args: Array<String>) {
	runApplication<MicroserviceApplication>(*args)
}
