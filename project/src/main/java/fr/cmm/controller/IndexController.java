package fr.cmm.controller;

import fr.cmm.controller.form.SearchForm;
import fr.cmm.domain.Recipe;
import fr.cmm.helper.Columns;
import fr.cmm.helper.PageQuery;
import fr.cmm.helper.Pagination;
import fr.cmm.service.RecipeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Inject;
import java.util.List;

@Controller
public class IndexController {
    @Inject
    private RecipeService recipeService;

    @RequestMapping({"/index", "/"})
    public String index(ModelMap model) {
        model.put("columns", randomColumns());

        return "index";
    }

    @RequestMapping("/tags.json")
    @ResponseBody
    public List<String> tags() {
        return recipeService.findAllTags();
    }

    @RequestMapping("/recettes")
    public String recettes(SearchForm searchForm, ModelMap model) {
        PageQuery pageQuery = new PageQuery();
        if (searchForm.getPageIndex()<1){
            pageQuery.setIndex(0);
        }
        else{
            pageQuery.setIndex(searchForm.getPageIndex() - 1);
        }
        pageQuery.setTag(searchForm.getTag());

        Pagination pagination = new Pagination();
        pagination.setPageIndex(searchForm.getPageIndex());
        pagination.setPageSize(pageQuery.getSize());
        pagination.setCount(recipeService.countByQuery(pageQuery));

        model.put("recipes", recipeService.findByQuery(pageQuery));
        model.put("pagination", pagination);
        model.put("searchForm", searchForm);

        return "recettes";
    }

    @RequestMapping("/recette-du-moment")
    public String recettesDuMoment(ModelMap model) {
        model.put("recipe", recipeService.findRandom(1).next());

        return "recette-du-moment";
    }

    private Columns randomColumns() {
        Columns columns = new Columns();
        //Iterable<Recipe> myRecipes=recipeService.findRandom(30);
        columns.add(recipeService.findRandom(10));
        columns.add(recipeService.findRandom(10));
        columns.add(recipeService.findRandom(10));

        return columns;
    }


    @RequestMapping("/recette/{id}")
    public String recette(@PathVariable("id") String id, ModelMap model) {
        if (recipeService.findById(id) != null){
            model.put("recipe", recipeService.findById(id));
            return "recette";
        }
        else {
            throw new RecipeNotFoundException();
        }
    }

    @RequestMapping("/contact")
    public String contact() {
        return "contact";
    }

    @RequestMapping("/404")
    public String error404() {
        return "error";
    }

    @RequestMapping("/500")
    public String error500() {
        return "error";
    }

    @RequestMapping("/mentions-legales")
    public String mentionsLegales() {
        return "mentions-legales";
    }
}

