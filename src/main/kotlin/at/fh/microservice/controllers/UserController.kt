package at.fh.microservice.controllers;


import at.fh.microservice.entities.User
import at.fh.microservice.services.UserService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("/api/v1/users")
class UserController(private val userService: UserService) {

    @GetMapping("")
    fun findAll() = userService.getAllUsers()

    @GetMapping("/{login}")
    fun findOne(@PathVariable login: String) =
        userService.getByLogin(login) ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "This user does not exist")

    @GetMapping("/{id}")
    fun findById(@PathVariable("id") userId: Long): User =
        userService.getUserById(userId) ?: throw ResponseStatusException(
            HttpStatus.NOT_FOUND,
            "This user does not exist"
        )

    @PostMapping("")
    fun createEmployee(@RequestBody payload: User): User = userService.createUser(payload)

    @PutMapping("/{id}")
    fun updateEmployeeById(@PathVariable("id") userId: Long, @RequestBody payload: User): User =
        userService.updateUserById(userId, payload) ?: throw ResponseStatusException(
            HttpStatus.NOT_FOUND,
            "This user does not exist"
        )

    @DeleteMapping("/{id}")
    fun deleteEmployeesById(@PathVariable("id") userId: Long) =
        userService.deleteUserById(userId) ?: throw ResponseStatusException(
            HttpStatus.NOT_FOUND,
            "This user does not exist"
        )
}
