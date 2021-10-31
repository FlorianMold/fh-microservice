package at.fh.microservice.services;

import at.fh.microservice.entities.User
import at.fh.microservice.repositories.UserRepository
import org.springframework.stereotype.Service

@Service
class UserService(private val userRepository: UserRepository) {

    fun getAllUsers(): Iterable<User> = userRepository.findAll()

    fun getByLogin(login: String) = userRepository.findByLogin(login)

    fun getUserById(userId: Long): User? = userRepository.findById(userId).orElse(null)

    fun createUser(user: User): User = userRepository.save(user)

    fun updateUserById(userId: Long, user: User): User? {
        return if (userRepository.existsById(userId)) {
            userRepository.save(
                User(
                    login = user.login,
                    firstname = user.firstname,
                    lastname = user.lastname,
                )
            )
        } else null
    }

    fun deleteUserById(userId: Long): Long? {
        return if (userRepository.existsById(userId)) {
            userRepository.deleteById(userId)
            return userId
        } else null
    }

}
