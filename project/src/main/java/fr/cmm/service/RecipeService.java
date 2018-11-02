package fr.cmm.service;

import java.util.*;

import javax.inject.Inject;


import fr.cmm.domain.RecipeWithPage;
import org.bson.types.ObjectId;
import org.jongo.MongoCollection;


import org.springframework.stereotype.Service;

import fr.cmm.domain.Recipe;
import fr.cmm.helper.PageQuery;

@Service
public class RecipeService {
    @Inject
    private MongoCollection recipeCollection;

    public Iterable<Recipe> findByQuery(PageQuery query) {
        String mongoQuery = "{}";
        String[] params = {};

        if (query.getTag() != null && !"".equals(query.getTag())) {
            mongoQuery = "{tags: #}";
            params = new String[] {query.getTag()};
        }

        return recipeCollection
                .find(mongoQuery, (Object[]) params)
                .skip(query.skip())
                .limit(query.getSize())
                .as(Recipe.class);
    }


    public Iterable<RecipeWithPage> findAndSortByQuery(PageQuery query) {
        String mongoQuery = "{}";
        String[] params = {};

        if (query.getTag() != null && !"".equals(query.getTag())) {
            mongoQuery = "{tags: #}";
            params = new String[] {query.getTag()};
        }

        Iterable<Recipe> iterableRecipeCollection = recipeCollection
                .find(mongoQuery, (Object[]) params)
                .skip(query.skip())
                .as(Recipe.class);//Note: Il n'est pas nécéssaire de limiter la query par size ici. Cela ne ferait que compliquer le process plus tard

        //remplissage d'une arrayList avec les recettes de iterableRecipeCollection:
        ArrayList<Recipe> myRecipes=new ArrayList<Recipe>();
        for (Recipe recipe:iterableRecipeCollection){
            myRecipes.add(recipe);
        }
        //Tri de ces recettes par date décroissante
        myRecipes=sortRecipesByDate(myRecipes);

        ArrayList<RecipeWithPage> myRecipesWithPage=new ArrayList<RecipeWithPage>();
        int count=0;
        for (Recipe recipe:myRecipes){
            //On créé un objet de type recipe with page et on le construit à l'aide de chaque recette de notre liste
            RecipeWithPage recipeWithPage=new RecipeWithPage();
            recipeWithPage.setDate(recipe.getDate());
            recipeWithPage.setId(recipe.getId());
            recipeWithPage.setImageId(recipe.getImageId());
            recipeWithPage.setIngredients(recipe.getIngredients());
            recipeWithPage.setIntro(recipe.getIntro());
            recipeWithPage.setRandomLocation(recipe.getRandomLocation());
            recipeWithPage.setTags(recipe.getTags());
            recipeWithPage.setTagsAsString(recipe.getTagsAsString());
            recipeWithPage.setText(recipe.getText());
            recipeWithPage.setTitle(recipe.getTitle());
            //On rajoute la page
            recipeWithPage.setPage((count/query.getSize())+1);
            myRecipesWithPage.add(recipeWithPage);
            count+=1;
        }

        return myRecipesWithPage;
    }


    public ArrayList<Recipe> sortRecipesByDate(ArrayList<Recipe> list)
    {
        if (list.size() <= 1)
            return list; // Already sorted

        ArrayList<Recipe> sorted = new ArrayList<Recipe>();
        ArrayList<Recipe> lesser = new ArrayList<Recipe>();
        ArrayList<Recipe> greater = new ArrayList<Recipe>();
        Recipe pivot = list.get(list.size()-1); // Use last Vehicle as pivot
        for (int i = 0; i < list.size()-1; i++)
        {
            if (list.get(i).getDate().compareTo(pivot.getDate()) < 0 )
                greater.add(list.get(i));
            else
                lesser.add(list.get(i));
        }

        lesser = sortRecipesByDate(lesser);
        greater = sortRecipesByDate(greater);

        lesser.add(pivot);
        lesser.addAll(greater);
        sorted = lesser;

        return sorted;
    }


    public long countByQuery(PageQuery query) {
        String mongoQuery = "{}";
        String[] params = {};
        if (query.getTag() != null && !"".equals(query.getTag())) {
            mongoQuery = "{tags: #}";
            params = new String[] {query.getTag()};
        }
        return recipeCollection.find(mongoQuery, (Object[]) params)
                .skip(query.skip())
                .limit(query.getSize())
                .as(Recipe.class).count();
    }

    public Iterator<Recipe> findRandom(int count) {
        return recipeCollection.find("{randomLocation: {$near: [#, 0]}}", Math.random()).limit(count).as(Recipe.class);
    }

    public Recipe findById(String id) {
        if (id.length()==24){
            return recipeCollection.findOne(new ObjectId(id)).as(Recipe.class);
        }
        else{
            return null;
        }
    }

    public void save(Recipe recipe) {
        recipeCollection.save(recipe);
    }

    public List<String> findAllTags() {
        return recipeCollection.distinct("tags").as(String.class);
    }
}
