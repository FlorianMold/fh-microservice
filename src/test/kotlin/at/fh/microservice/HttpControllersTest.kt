package at.fh.microservice

import at.fh.microservice.entities.Article
import at.fh.microservice.entities.User
import at.fh.microservice.services.ArticleService
import at.fh.microservice.services.UserService
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@WebMvcTest
class HttpControllersTest(@Autowired val mockMvc: MockMvc) {

    @MockkBean
    private lateinit var userService: UserService

    @MockkBean
    private lateinit var articleService: ArticleService

    @Test
    fun `List articles`() {
        val florian = User("florian.mold", "Florian", "Mold")
        val article1 =
            Article("Article1", "Dear", "Lorem ipsum", florian)
        val article2 =
            Article("Article2", "Dear", "Lorem ipsum", florian)
        every { articleService.getAllArticles() } returns listOf(article1, article2)
        mockMvc.perform(get("/api/v1/articles").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("\$.[0].author.login").value(florian.login))
            .andExpect(jsonPath("\$.[0].slug").value(article1.slug))
            .andExpect(jsonPath("\$.[1].author.login").value(florian.login))
            .andExpect(jsonPath("\$.[1].slug").value(article2.slug))
    }

    @Test
    fun `List users`() {
        val florian = User("florian.mold", "Florian", "Mold")
        val daniel = User("daniel.ka", "Daniel", "Ka")
        every { userService.getAllUsers() } returns listOf(florian, daniel)
        mockMvc.perform(get("/api/v1/users").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("\$.[0].login").value(florian.login))
            .andExpect(jsonPath("\$.[1].login").value(daniel.login))
    }
}
