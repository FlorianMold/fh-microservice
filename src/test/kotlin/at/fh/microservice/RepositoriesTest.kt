package at.fh.microservice

import at.fh.microservice.entities.Article
import at.fh.microservice.entities.User
import at.fh.microservice.repositories.ArticleRepository
import at.fh.microservice.repositories.UserRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.data.repository.findByIdOrNull

@DataJpaTest
class RepositoriesTests
@Autowired constructor(
    val entityManager: TestEntityManager,
    val userRepository: UserRepository,
    val articleRepository: ArticleRepository
) {

    @Test
    fun `When findByIdOrNull then return Article`() {
        val florian = User("florian.mold", "Florian", "Mold")
        entityManager.persist(florian)
        val article = Article("Article", "Dear", "Lorem ipsum", florian)
        entityManager.persist(article)
        entityManager.flush()
        val found = articleRepository.findByIdOrNull(article.id!!)
        assertThat(found).isEqualTo(article)
    }

    @Test
    fun `When findByLogin then return User`() {
        val florian = User("florian.mold", "Florian", "Mold")
        entityManager.persist(florian)
        entityManager.flush()
        val user = userRepository.findByLogin(florian.login)
        assertThat(user).isEqualTo(florian)
    }
}
