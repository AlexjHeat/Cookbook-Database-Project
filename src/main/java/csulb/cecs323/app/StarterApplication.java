package csulb.cecs323.app;
import csulb.cecs323.model.Cuisine;
import csulb.cecs323.model.FoodCritic;
import csulb.cecs323.model.User;
import csulb.cecs323.model.*;

import javax.persistence.*;
import csulb.cecs323.model.Recipe;

import javax.persistence.*;
import java.time.Duration;
import java.util.*;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


//Code imported from jpa-lab-2 repository
public class StarterApplication {
    private final EntityManager entityManager;
    private final Scanner userInput;
    private static final Logger LOGGER = Logger.getLogger(StarterApplication.class.getName());

    public StarterApplication(EntityManager manager) {
        this.entityManager = manager;
        userInput = new Scanner(System.in).useDelimiter("\\n");
    }

    public static void main(String[] args) {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("persistence_management");
        EntityManager manager = factory.createEntityManager();
        StarterApplication application = new StarterApplication(manager);
        application.mainMenu();

    }

    public void mainMenu() {
        int option = -1;
        while (option != 0) {
            System.out.print("1\tCreate a new recipe\n" +
                    "2\tUpdate an existing recipe\n" +
                    "3\tRemove food critic\n" +
                    "4\tRemove a cuisine entity\n" +
                    "5\tExecute Queries\n" +
                    "0\tExit Program\n");
            option = userInput.nextInt();

            switch (option) {
                case 1:
                    createNewRecipe();
                    break;

                case 2:
                    updateRecipe();
                    break;

                case 3:
                    deleteFoodCritic();
                    break;

                case 4:
                    deleteCuisine();
                    break;

                case 5:
                    executeQuery();
                    break;


            }
        }
    }

    /**
     * This function contains 3 queries that retrieve various information from the database that a user may find useful.
     * It prompts the user to select a query and then simply executes the query and outputs its result in a way formatted
     * for the user to understand without prior knowledge of databases.
     */
    public void executeQuery()
    {
        System.out.println("Select which information you would like to retrieve");
        int option = -1;
        System.out.print("1\tInformation on all chefs and their average review score\n" +
                "2\tAll ingredients, as well as the cuisines and recipes (if any) they are in\n" +
                "3\tEvery chef, and the rating of their most difficult recipe\n" );
        option = userInput.nextInt();

        switch (option) {
            case(1):
                TypedQuery<Object[]> query1 = entityManager.createQuery("SELECT u.firstName, u.lastName, c.yearsOfExperience, AVG(rv.rating) " +
                        "FROM User u" +
                        "   INNER JOIN Chef c ON u.id = c.id" +
                        "   INNER JOIN c.recipesCreated r" +
                        "   LEFT OUTER JOIN r.reviews rv" +
                        "   GROUP BY u.firstName, u.lastName, c.yearsOfExperience" , Object[].class);
                List<Object[]> result1 = query1.getResultList();

                for(Object[] o : result1)
                {
                    System.out.println(o[0] + " " + o[1] + ", years experience: " + o[2] + "; average rating: " + o[3]);
                }
                break;
            case(2):
                TypedQuery<Object[]> query2 = entityManager.createQuery("SELECT i.name, ci.name, r.name " +
                        "FROM Ingredient i" +
                        "    LEFT OUTER JOIN i.cuisinesInfluenced ci" +
                        "    LEFT OUTER JOIN i.ingredientAmountPerRecipe ia" +
                        "    INNER JOIN ia.recipe r", Object[].class);
                List<Object[]> result2 = query2.getResultList();

                for(Object[] o : result2)
                {
                    System.out.println("Ingredient: " + o[0] + " | Cuisine: " + o[1] + " | Recipe: " + o[2]);
                }
                break;
            case(3):
                TypedQuery<Object[]> query3 = entityManager.createQuery("SELECT c.firstName, c.lastName, c.yearsOfExperience, MAX(r.difficultyRating) " +
                        "   FROM Recipe r" +
                        "   INNER JOIN r.chef c" +
                        "   GROUP BY  c.firstName, c.lastName, c.yearsOfExperience", Object[].class);
                List<Object[]> result3 = query3.getResultList();

                for(Object[] o : result3)
                {
                    System.out.println("Name: " + o[0] + " " + o[1] + " | years of experience: " + o[2] + " | most difficult recipe: " + o[3]);
                }

                break;
        }

    }

    /**
     * This function executes a query to retrieve all food critics who have users following them, and then prompts
     * for the user to pick one to delete.  Once a critic has been selected, a transaction is started that will delete
     * the critic.  The database will handle all cascading deletions of relationships
     */
    public void deleteFoodCritic(){
        TypedQuery<FoodCritic> queryCritics = entityManager.createQuery("SELECT c FROM FoodCritic c " +
                        "WHERE c.usersFollowing is not empty",
                FoodCritic.class);
        List<FoodCritic> foodCritics = queryCritics.getResultList();

        int idx = 1;
        for(FoodCritic f : foodCritics)
        {
            System.out.println(idx++ + ": " + f.getFirstName() + " " + f.getLastName());
        }

        System.out.println("Select food critic to delete");
        int criticIndex = userInput.nextInt() - 1;

        FoodCritic criticDelete = foodCritics.get(criticIndex);

        TypedQuery<User> userQuery = entityManager.createQuery("SELECT u FROM User u", User.class);
        List<User> users = userQuery.getResultList();

        entityManager.getTransaction().begin();

        for(User u : users)
            u.getUsersFollowing().remove(criticDelete);

        entityManager.remove(criticDelete);
        entityManager.getTransaction().commit();
    }

    /**
     * This function executes a query to retrieve all cuisine, and then prompts for the user to pick one to delete
     * Because a cuisine object is a parent of recipe, deleting a cuisine will cause a cascade that
     * will also delete some recipes associated with it.  The function warns the user of this before they select a cuisine
     * to delete
     */
    public void deleteCuisine()
    {
        TypedQuery<Cuisine> queryCuisine = entityManager.createQuery("SELECT c FROM Cuisine c ",
                Cuisine.class);
        List<Cuisine> cuisines = queryCuisine.getResultList();

        int idx = 1;
        for(Cuisine c : cuisines)
        {
            System.out.println(idx++ + ": " + c.getName());
        }

        System.out.println("Select a cuisine to delete, note that this may cause the deletion of recipes and ingredients" +
                " that originate from the selected cuisine");
        int cuisineIndex = userInput.nextInt() - 1;

        Cuisine cuisineDelete = cuisines.get(cuisineIndex);

        entityManager.getTransaction().begin();
        entityManager.remove(cuisineDelete);
        entityManager.getTransaction().commit();
    }
    
    /**
     * Asks for input attempting to match a given regex. Returns once a valid input option has been entered.
     * @param options : a matching regex
     * @return A valid string as described by the options regex
     */
    public String validInput(String options){
            Pattern p = Pattern.compile(options);
            boolean matchFound;
            String in;
            do{
                in = userInput.nextLine().strip();
                Matcher m = p.matcher(in);
                matchFound = m.matches();
                if(!matchFound)
                    System.out.println("Please enter in the format ("+options+"): ");
            }while(!matchFound);
            return in;
    }
    
    /**
     *  Function to create and persist a new Recipe object/row and the corresponding objects/rows that relate to it.
     *  Takes user input using a scanner object,
     *  certifying input viability occasionally using the helper function validInput()
     */
    public void createNewRecipe() {
        userInput.nextLine();
        
        // transaction is begins
        entityManager.getTransaction().begin();

        Recipe newRecipe = new Recipe();


        System.out.println("\nEnter a recipe name: ");
        String name = userInput.nextLine();
        newRecipe.setName(name);


        System.out.println("\nEnter a recipe description: ");
        String description = userInput.nextLine();
        newRecipe.setDescription(description);


        System.out.println("\nEnter a recipe prep time: (in long minutes) ");
        Duration prepTimeMinutes = Duration.ofMinutes(userInput.nextInt());
        newRecipe.setPrepTime(prepTimeMinutes);


        System.out.println("\nEnter a recipe cook time: (in long minutes) ");
        Duration cookTimeMinutes = Duration.ofMinutes(userInput.nextLong());
        newRecipe.setCookTime(cookTimeMinutes);


        System.out.println("\nDoes your recipe have a difficulty rating? (y|n): ");
        if (validInput("y|n").equals("y")) {
            System.out.println("\nEnter a numeric recipe difficulty: ");
            int difficulty = Integer.parseInt(userInput.nextLine());
            newRecipe.setDifficultyRating(difficulty);
        }


        System.out.println("\nEnter a number of servings: ");
        int servings = Integer.parseInt(userInput.nextLine());
        newRecipe.setNumberOfServings(servings);


        System.out.println("\nWhat chef created this recipe?");
        
        // query the chef table for a resulting list representation
        TypedQuery<Chef> queryChefs = entityManager.createQuery("SELECT c FROM Chef c", Chef.class);
        List<Chef> chefs = queryChefs.getResultList();
        
        //displays chefs, lets user choose from a list
        int idx = 0;
        for (Chef c : chefs) {
            System.out.println(idx++ + "\t" + c.getFirstName() + " " + c.getLastName());
        }
        System.out.println("Enter index: ");
        int chefIdx = Integer.parseInt(userInput.nextLine());
        newRecipe.setChef(queryChefs.getResultList().get(chefIdx));
        
        System.out.println("\nDoes your recipe have an associated cuisine? (y|n): ");
        if (validInput("y|n").equals("y")) {
            System.out.println("\nWhat cuisine does this recipe belong to?: ");
    
            // query the cuisine table for a resulting list representation
            TypedQuery<Cuisine> queryCuisines = entityManager.createQuery("SELECT c FROM Cuisine c", Cuisine.class);
            List<Cuisine> cuisines = queryCuisines.getResultList();
    
            //displays cuisines, lets user choose from a list
            int idx2 = 0;
            for (Cuisine cui : cuisines) {
                System.out.println(idx2++ + "\t" + cui.getName());
            }
            System.out.println("Enter index: ");
            int cuisineIdx = Integer.parseInt(userInput.nextLine());
            newRecipe.setCuisine(queryCuisines.getResultList().get(cuisineIdx));
        }
        
        System.out.println("\n");
        Set<IngredientAmount> ingredientAmountsSet = new HashSet<>();
        boolean done = false;
        do {
            System.out.println("Ingredient to add: ");
            
            //creates a new IngredientAmount object associated with the recipe
            IngredientAmount ingredientAmount = new IngredientAmount();
            ingredientAmountsSet.add(ingredientAmount);
            ingredientAmount.setRecipe(newRecipe);
            
            // queries the ingredient table
            TypedQuery<Ingredient> queryIngredients = entityManager.createQuery(
                    "SELECT i FROM Ingredient i " +
                            "WHERE i.name = '" + userInput.nextLine() + "'"
                    , Ingredient.class);

            Ingredient temp = queryIngredients.getSingleResult();
            ingredientAmount.setIngredient(temp);
            
            System.out.println("Amount: (float_unitString)");
            String[] amount = userInput.nextLine().strip().split("_");

            ingredientAmount.setAmount(Float.parseFloat(amount[0]));
            ingredientAmount.setUnits(amount[1]);
            
            // persists the new associated ingredientamount row to the database
            entityManager.persist(ingredientAmount);

            System.out.println("All ingredients added?: (y|n)");
            if (validInput("y|n").equals("y"))
                done = true;
        } while (!done);
        newRecipe.setIngredientAmount(ingredientAmountsSet);
        done = false;


        System.out.println("\nPlease add all steps in-order.");
        Set<Step> stepsSet = new HashSet<>();
        int stepOrder = 1;
        do {
            System.out.println("Step to add: ");
            Step s = new Step();
            stepsSet.add(s);
            s.setRecipe(newRecipe);
            s.setOrderNumber(stepOrder++);
            
            String stepDescription = userInput.nextLine();
            s.setDescription(stepDescription);

            System.out.println("Estimated time: ");
            Duration stepTime = Duration.ofMinutes(userInput.nextInt());
            s.setTime(stepTime);

            //persists the new associated step row to the database
            entityManager.persist(s);

            System.out.println("All steps added?: (y|n)");
            if (validInput("y|n").equals("y"))
                done = true;
        } while (!done);
        newRecipe.setSteps(stepsSet);
        
        //persists the entire Recipe object to the database as a row in the recipe table
        entityManager.persist(newRecipe);
        
        //displays the recipe added
        System.out.println("Recipe complete! Recipe added: " + newRecipe);
        
        // transaction is committed
        entityManager.getTransaction().commit();
    }

    /**
     *  Function to update any existing Recipe object/row.  User will be prompted to insert a substring.  The function
     *  will retrieve a list of every recipe that contains the whole substring somewhere in the recipe Name field.
     *  This search is case insensitive.
     *  The user may select a recipe from the list and change the following fields:
     *  CookTime, PrepTime, Description, & DifficultyRating
     */
    public void updateRecipe() {
            System.out.print("Enter all or part of the recipe name you would like to update: ");
            String searchKey = userInput.next().toUpperCase(Locale.ROOT);
            String recipeQuery = "SELECT r FROM Recipe r WHERE UPPER(r.name) LIKE '%" + searchKey + "%'";
            Query q = entityManager.createQuery(recipeQuery);
            List<Recipe> recipeList = q.getResultList();
            int queryCount = recipeList.size();

            if (queryCount == 0) {
                System.out.print("We found no recipes containing that phrase.  Returning to menu...");
                return;
            }
            for (int i = 0; i < queryCount; i++) {
                Recipe r = recipeList.get(i);
                System.out.print((i + 1) + "\t" + r.getName() + ", by " + r.getChef().getUserName() + "\n");
            }

            System.out.print("0\tReturn to main menu\n");
            int option = -1;

            while (option > queryCount || option < 1) {
                option = userInput.nextInt();
                if (option == 0) return;
                if (option > queryCount || option < 1) {
                    System.out.print("Invalid number. Try again.\n");
                }
            }
            Recipe r = recipeList.get(option - 1);
            option = -1;
            EntityTransaction tx = entityManager.getTransaction();
            while (option != 0) {
                System.out.print("\nUpdating " + r.getName() + ", by " + r.getChef().getUserName() + "...\n");
                System.out.print("1\tAdd or change description\n" +
                        "2\tChange prep time\n" +
                        "3\tChange cook time\n" +
                        "4\tChange difficulty rating\n" +
                        "0\tReturn to menu\n");

                option = userInput.nextInt();
                switch (option) {
                    case 0:
                        return;

                    case 1:
                        System.out.print("Current description:\n" + r.getDescription() + "\n");
                        System.out.print("Enter the new description:\n");
                        String newDescription = userInput.next();
                       tx.begin();
                       r.setDescription(newDescription);
                       tx.commit();
                       break;

                   case 2:
                       System.out.print("Current prep time: " + r.getPrepTime().toMinutes() + " minutes \n");
                       System.out.print("Enter the new prep time in minutes:\n");
                       Long newPrepTime = userInput.nextLong();
                       tx.begin();
                       r.setPrepTime(Duration.ofMinutes(newPrepTime));
                       tx.commit();
                       break;

                   case 3:
                       System.out.print("Current cook time: " + r.getCookTime().toMinutes() + " minutes \n");
                       System.out.print("Enter the new cook time in minutes:\n");
                       Long newCookTime = userInput.nextLong();
                       tx.begin();
                       r.setCookTime(Duration.ofMinutes(newCookTime));
                       tx.commit();
                       break;

                   case 4:
                       System.out.print("Current difficulty rating: " + r.getDifficultyRating() + "\n");
                       System.out.print("\nEnter the new difficulty rating (1 - 5):\n");
                       int newDifficulty = userInput.nextInt();
                       tx.begin();
                       r.setDifficultyRating(newDifficulty);
                       tx.commit();
                       break;
               }
        }
    }
}