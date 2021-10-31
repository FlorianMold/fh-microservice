package at.fh.microservice.configurations

import at.fh.microservice.entities.Article
import at.fh.microservice.entities.User
import at.fh.microservice.repositories.ArticleRepository
import at.fh.microservice.repositories.UserRepository
import org.springframework.boot.ApplicationRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class BlogConfiguration {

    @Bean
    fun databaseInitializer(
        userRepository: UserRepository,
        articleRepository: ArticleRepository
    ) = ApplicationRunner {

        val me = userRepository.save(User("florian.mold", "Florian", "Mold"))
        articleRepository.save(
            Article(
                title = "A Russian Pipeline Changes Direction, and Energy Politics Come to the Fore",
                headline = "Amid an energy crunch in Europe, one of Russia’s largest natural gas pipelines began pulling gas out of Western Europe back eastward, Russian news agencies reported",
                content = "MOSCOW — Natural gas, already in short supply in Europe this fall, began moving away from Germany on Saturday and back toward the east in an unusual ... ",
                author = me
            )
        )
        articleRepository.save(
            Article(
                title = "Xi Hasn’t Left China in 21 Months. Covid May Be Only Part of the Reason.",
                headline = "Xi Jinping’s lack of face time with world leaders signals a turn inward on domestic issues and a reluctance to compromise on the global stage.",
                content = "Instead, China has turned inward, with officials preoccupied with protecting Mr. Xi’s health and internal political machinations, ...",
                author = me
            )
        )
    }
}
