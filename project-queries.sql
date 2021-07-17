
-- retrieves relevant information written of all reviews written by food critics that
-- the user GreatDane does not already follow
-- (Username GreatDane could be switched out for any user to see all reviews from critics they follow)
SELECT u.firstName as Critic_First_Name, u.lastName as Critic_Last_Name, r.name as Review_Title, rv.rating as Review_Rating, rv.description as Review_Description
FROM recipe r
    INNER JOIN review rv ON r.id = rv.recipeRated_id
    INNER JOIN foodCritic fc ON rv.author_id = fc.id
    INNER JOIN user_account u ON fc.id = u.id
EXCEPT
    SELECT u.firstName, u.lastName, r.name, rv.rating, rv.description
    FROM recipe r
    INNER JOIN review rv ON r.id = rv.recipeRated_id
    INNER JOIN foodCritic fc ON rv.author_id = fc.id
    INNER JOIN user_account u ON fc.id = u.id
    INNER JOIN user_account_junction uaj ON u.id = uaj.user_account
    WHERE uaj.user_following = (SELECT u.id FROM user_account u WHERE u.username = 'GreatDane')

-- retrieve the average review rating of every chef's recipes
SELECT u.firstName as First_Name, u.lastName as Last_Name, c.yearsOfExperience as Years_of_Experience, AVG(rv.rating) as Average_Review_Rating
FROM user_account u
    INNER JOIN chef c ON u.id = c.id
    INNER JOIN recipe r ON c.id = r.chef_id
    LEFT OUTER JOIN review rv ON r.id = rv.recipeRated_id
    GROUP BY u.firstName, u.lastName, c.yearsOfExperience

-- find all ingredients and if they are a part of cuisine, list it, as well as the recipes they are a part of if any
SELECT i.name as Ingredient, ci.cuisine as Cuisine, r.name as Recipe
FROM ingredient i
    LEFT OUTER JOIN cuisine_ingredient ci ON i.name = ci.ingredient
    LEFT OUTER JOIN ingredientamount ia ON i.name = ia.ingredient_name
    INNER JOIN recipe r ON ia.recipe_id = r.id;

--Retrieves the names of chefs who have the most recipes, as well as the number of recipes
SELECT u.firstName, u.lastName, COUNT(r.id) as number_recipe
    FROM user_account u
    INNER JOIN chef c ON u.id = c.id
    INNER JOIN recipe r ON c.id = r.chef_id
    GROUP BY u.firstName, u.lastName
    HAVING COUNT(r.id) = (SELECT MAX(r_count) FROM (SELECT COUNT(r.id) as r_count FROM
    chef c INNER JOIN recipe r ON c.id = r.chef_id
        GROUP BY c.id) sq1);

-- List the recipe with the highest difficultyrating amd chef's first name, and last name who creates the recipe,
-- and the year of experience of the chef
-- List all chefs with the rating of their most difficult recipe
SELECT MAX(r.difficultyrating) as Highest_rating_of_difficulty, u.firstName as First_Name, u.lastName as Last_name, c.yearsofexperience as Year_of_experience
FROM recipe r
    INNER JOIN user_account u ON u.id = r.chef_id
    INNER JOIN chef c ON u.id = c.id
GROUP BY  r.name, u.firstName, u.lastName, c.yearsofexperience


--List recipes that have 5 or more steps, and chef's first name and last name
SELECT DISTINCT r.name as Recipe, u.firstName as First_name, u.lastName as Last_name
FROM    recipe r
        INNER JOIN step s ON s.recipe_id = r.id
        INNER JOIN user_account u ON u.id = r.chef_id
WHERE   s.recipe_id IN (
        SELECT recipe_id
        FROM
        (SELECT recipe_id, COUNT(ordernumber)
        FROM step
        GROUP BY recipe_id
        HAVING COUNT(ordernumber) > 4) steps)