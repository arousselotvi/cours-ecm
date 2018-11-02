package fr.cmm.controller;



import fr.cmm.domain.Recipe;
import fr.cmm.domain.RecipeWithPage;
import fr.cmm.helper.PageQuery;
import fr.cmm.service.RecipeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import javax.inject.Inject;

@Controller
public class ApiController {
    @Inject
    private RecipeService recipeService;

    @RequestMapping(value = "/api/recipes", method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Iterable<RecipeWithPage> getRecipesList() {
        PageQuery pageQuery = new PageQuery();
        return recipeService.findAndSortByQuery(pageQuery);
    }

    @RequestMapping(value = "/api/recipes/{id}", method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Recipe getRecipesById(@PathVariable("id") String id) {
        Recipe myRecipe=recipeService.findById(id);
        if (myRecipe==null){
            throw new UnknownRecipeException();
        }
        return recipeService.findById(id);
    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public class UnknownRecipeException extends RuntimeException { }

}
