package fr.cmm.controller;

import fr.cmm.service.RecipeService;
import org.jongo.MongoCollection;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc
public class ApiControllerTestConfig {
    @Bean
    ApiController apiController() {
        return new ApiController();
    }

    @Bean
    RecipeService recipeService() {
        return Mockito.mock(RecipeService.class);
    }

    @Bean
    MongoCollection recipeCollection() {
        return Mockito.mock(MongoCollection.class);
    }
}
