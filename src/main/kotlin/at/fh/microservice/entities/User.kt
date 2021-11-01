package at.fh.microservice.entities

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "authors")
class User(
    var login: String,
    var firstname: String,
    var lastname: String,
    @Id @GeneratedValue var id: Long? = null) {
    override fun toString(): String {
        return "User(login='$login', firstname='$firstname', lastname='$lastname', id=$id)"
    }
}
