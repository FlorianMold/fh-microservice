package at.fh.microservice.controllers

import at.fh.microservice.entities.Article
import at.fh.microservice.services.ArticleService
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("/api/v1/articles")
class ArticleController(private val articleService: ArticleService) {

    private val log = LoggerFactory.getLogger(javaClass)
    private val baseUrl = "/api/v1/articles"

    @GetMapping("")
    fun findAll(): Iterable<Article> {
        log.info("GET $baseUrl")

        return articleService.getAllArticles()
    }

    @GetMapping("/{id}")
    fun findById(@PathVariable("id") articleId: Long): Article {
        log.info("GET $baseUrl/$articleId")

        return articleService.getArticleById(articleId) ?: throw ResponseStatusException(
            HttpStatus.NOT_FOUND,
            "This article does not exist"
        )
    }

    @PostMapping("")
    fun createEmployee(@RequestBody payload: Article): Article {
        log.info("POST $baseUrl", payload)

        return articleService.createArticle(payload)
    }

    @PutMapping("/{id}")
    fun updateEmployeeById(@PathVariable("id") articleId: Long, @RequestBody payload: Article): Article {
        log.info("PUT $baseUrl/$articleId", payload)

        return articleService.updateArticleById(articleId, payload) ?: throw ResponseStatusException(
            HttpStatus.NOT_FOUND,
            "This article does not exist"
        )
    }

    @DeleteMapping("/{id}")
    fun deleteEmployeesById(@PathVariable("id") articleId: Long): Long {
        log.info("DELETE $baseUrl/$articleId")

        return articleService.deleteArticleById(articleId) ?: throw ResponseStatusException(
            HttpStatus.NOT_FOUND,
            "This article does not exist"
        )
    }
}
