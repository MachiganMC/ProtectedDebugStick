package be.machigan.protecteddebugstick.def;

import be.machigan.protecteddebugstick.ProtectedDebugStick;
import be.machigan.protecteddebugstick.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

import java.util.*;
import java.util.stream.Collectors;

public class RecipeHandler {
    private static Set<String> recipesName = new HashSet<>();
    final private static List<String> POSSIBLE_FIELDS = new ArrayList<>(Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8", "9"));

    public static void register() {
        List<ShapedRecipe> recipes = createRecipes();
        for (ShapedRecipe recipe : recipes) {
            Bukkit.addRecipe(recipe);
        }
        Utils.log(recipes.size() + " recipes has been registered");

    }

    private static void searchRecipes() {
        try {
            recipesName = ProtectedDebugStick.config.getConfigurationSection("recipes").getKeys(false);
        } catch (NullPointerException ignored) {
            recipesName = new HashSet<>();
        }
        Set<String> removed = new HashSet<>();
        for (String key : recipesName) {
            int durability = ProtectedDebugStick.config.getInt("recipes." + key + ".durability");
            if (durability <= 0 && durability != -1) {
                removed.add(key);
            }
            Set<String> fields;
            try {
                fields = ProtectedDebugStick.config.getConfigurationSection("recipes." + key + ".craft").getKeys(false);
            } catch (NullPointerException ignored) {
                Utils.log("Recipes \"" + key + "\" has no slot. Ignoring the recipe", Utils.LOG_WARNING);
                removed.add(key);
                continue;
            }
            fields = fields.stream().filter(POSSIBLE_FIELDS::contains).collect(Collectors.toSet());
            if (fields.size() == 0) {
                removed.add(key);
            }
        }
        recipesName.removeAll(removed);
    }

    private static List<ShapedRecipe> createRecipes() {
        searchRecipes();
        List<ShapedRecipe> recipeList = new ArrayList<>();
        for (String key : recipesName) {
            NamespacedKey namespacedKey = new NamespacedKey(ProtectedDebugStick.instance, key);
            ItemStack ds;
            if (ProtectedDebugStick.config.getInt("recipes." + key + ".durability") == -1) {
                ds = DebugStick.getInfinityDebugStick();
            } else {
                ds = DebugStick.getDebugStick(ProtectedDebugStick.config.getInt("recipes." + key + ".durability"));
            }

            Set<String> shapeSet;
            try {
                shapeSet = ProtectedDebugStick.config.getConfigurationSection("recipes." + key + ".craft").getKeys(false);
            } catch (NullPointerException ignored) {
                Utils.log("Recipes \"" + key + "\" has no slot. Ignoring the recipe", Utils.LOG_WARNING);
                continue;
            }

            ShapedRecipe recipe = new ShapedRecipe(namespacedKey, ds);

            recipe.shape(
                    (shapeSet.contains("1") ? "1" : " ") + (shapeSet.contains("2") ? "2" : " ") + (shapeSet.contains("3") ? "3" : " "),
                    (shapeSet.contains("4") ? "4" : " ") + (shapeSet.contains("5") ? "5" : " ") + (shapeSet.contains("6") ? "6" : " "),
                    (shapeSet.contains("7") ? "7" : " ") + (shapeSet.contains("8") ? "8" : " ") + (shapeSet.contains("9") ? "9" : " ")
            );

            for (int i = 1; i <= 9; i++) {
                try {
                    Material m = Material.matchMaterial(ProtectedDebugStick.config.getString("recipes." + key + ".craft." + i));
                    if (m != null) {
                        recipe.setIngredient(Integer.toString(i).toCharArray()[0], m);
                    } else {
                        recipe.setIngredient(Integer.toString(i).toCharArray()[0], Material.BARRIER);
                        Utils.log("The material \"" + ProtectedDebugStick.config.getString("recipes." + key + ".craft." + i) + "\" doesn't" +
                                " exist from the recipe \"" + key + "\" (slot N°" + i + ") ! This slot has been replaces by a barrier block", Utils.LOG_WARNING);
                    }
                } catch (IllegalArgumentException ignored) {
                }
            }
            Bukkit.getServer().removeRecipe(namespacedKey);

            recipeList.add(recipe);
        }
        return recipeList;
    }

}
