package at.fh.microservice.services;

import at.fh.microservice.entities.Article
import at.fh.microservice.repositories.ArticleRepository
import org.springframework.stereotype.Service

@Service
class ArticleService(private val articleRepository: ArticleRepository) {

    fun getAllArticles(): Iterable<Article> = articleRepository.findAllByOrderByAddedAtDesc()

    fun getArticleById(articleId: Long): Article? = articleRepository.findById(articleId).orElse(null)

    fun createArticle(article: Article): Article = articleRepository.save(article)

    fun updateArticleById(articleId: Long, article: Article): Article? {
        return if (articleRepository.existsById(articleId)) {
                articleRepository.save(
                    Article(
                        title = article.title,
                        headline = article.headline,
                        content = article.content,
                        author = article.author
                    )
                )
            } else null
    }

    fun deleteArticleById(articleId: Long): Long? {
       return if (articleRepository.existsById(articleId)) {
           articleRepository.deleteById(articleId)
           return articleId
       } else null
    }


}
