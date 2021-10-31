package at.fh.microservice.controllers

import at.fh.microservice.entities.Article
import at.fh.microservice.services.ArticleService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("/api/v1/articles")
class ArticleController(private val articleService: ArticleService) {

    @GetMapping("")
    fun findAll() = articleService.getAllArticles()

    @GetMapping("/{id}")
    fun findById(@PathVariable("id") articleId: Long): Article =
        articleService.getArticleById(articleId) ?: throw ResponseStatusException(
            HttpStatus.NOT_FOUND,
            "This article does not exist"
        )

    @PostMapping("")
    fun createEmployee(@RequestBody payload: Article): Article = articleService.createArticle(payload)

    @PutMapping("/{id}")
    fun updateEmployeeById(@PathVariable("id") articleId: Long, @RequestBody payload: Article): Article =
        articleService.updateArticleById(articleId, payload) ?: throw ResponseStatusException(
            HttpStatus.NOT_FOUND,
            "This article does not exist"
        )

    @DeleteMapping("/{id}")
    fun deleteEmployeesById(@PathVariable("id") articleId: Long) =
        articleService.deleteArticleById(articleId) ?: throw ResponseStatusException(
            HttpStatus.NOT_FOUND,
            "This article does not exist"
        )
}
