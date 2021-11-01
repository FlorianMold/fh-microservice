package at.fh.microservice.controllers;


import at.fh.microservice.entities.User
import at.fh.microservice.services.UserService
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("/api/v1/users")
class UserController(private val userService: UserService) {

    private val log = LoggerFactory.getLogger(javaClass)
    private val baseUrl = "/api/v1/articles"

    @GetMapping("")
    fun findAll(): Iterable<User> {
        log.info("GET $baseUrl")

        return userService.getAllUsers()
    }

    @GetMapping("/{login}")
    fun findOne(@PathVariable login: String): User {
        log.info("GET $baseUrl/$login")

        return userService.getByLogin(login) ?: throw ResponseStatusException(
            HttpStatus.NOT_FOUND,
            "This user does not exist"
        )

    }

    @PostMapping("")
    fun createEmployee(@RequestBody payload: User): User {
        log.info("POST $baseUrl", payload)

        return userService.createUser(payload)
    }

    @PutMapping("/{id}")
    fun updateEmployeeById(@PathVariable("id") userId: Long, @RequestBody payload: User): User {
        log.info("PUT $baseUrl/$userId", payload)

        return userService.updateUserById(userId, payload) ?: throw ResponseStatusException(
            HttpStatus.NOT_FOUND,
            "This user does not exist"
        )
    }

    @DeleteMapping("/{id}")
    fun deleteEmployeesById(@PathVariable("id") userId: Long): Long {
        log.info("DELETE $baseUrl/$userId")

        return userService.deleteUserById(userId) ?: throw ResponseStatusException(
            HttpStatus.NOT_FOUND,
            "This user does not exist"
        )
    }
}
