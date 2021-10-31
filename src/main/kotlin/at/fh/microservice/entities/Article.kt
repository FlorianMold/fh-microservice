package at.fh.microservice.entities

import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "articles")
class Article(
    var title: String,
    var headline: String,
    var content: String,
    @ManyToOne var author: User,
    var slug: String = title,
    var addedAt: LocalDateTime = LocalDateTime.now(),
    @Id @GeneratedValue var id: Long? = null
)
