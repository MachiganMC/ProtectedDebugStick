package be.machigan.protecteddebugstick.utils;

import be.machigan.protecteddebugstick.ProtectedDebugStick;
import be.machigan.protecteddebugstick.def.DebugStick;
import com.google.common.base.Preconditions;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class RecipeValidator {
    @Nullable String invalidReason;
    @NotNull ConfigurationSection section;
    @NotNull String recipeName;
    private static final List<String> POSSIBLE_FIELDS = Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8", "9");

    public RecipeValidator(@NotNull ConfigurationSection section, @NotNull String recipeName) {
        this.section = section;
        this.recipeName = recipeName;
        this.invalidReason = this.getItemReason();
        this.invalidReason = this.getCraftFieldReason();
        this.invalidReason = this.getDurabilityReason();
        this.invalidReason = this.getIngredientReason();

    }

    @Nullable
    private String getItemReason() {
        if (this.invalidReason != null)
            return this.invalidReason;

        String itemName = this.section.getString("Item");
        if (itemName == null)
            return "The recipe \"" + this.recipeName + "\" doesn't have the field \"Item\"";

        try {
            Config.Item.valueOf(itemName.toUpperCase());
        } catch (IllegalArgumentException e) {
            return "The item \"" + itemName + "\" of the recipe \"" + this.recipeName + "\" is invalid";
        }
        return null;
    }

    @Nullable
    private String getCraftFieldReason() {
        if (this.invalidReason != null)
            return this.invalidReason;

        ConfigurationSection craftSection = this.section.getConfigurationSection("Craft");
        if (craftSection == null)
            return "The recipe \"" + this.recipeName + "\" has no crafting slot";


        for (String slot : craftSection.getKeys(false)) {
            if (!POSSIBLE_FIELDS.contains(slot))
                return "The slot \"" + slot + "\" of the recipe \"" + this.recipeName + "\" is invalid (valid : from 1 to 9)";
        }

        return null;
    }

    @Nullable
    private String getDurabilityReason() {
        if (this.invalidReason != null || !this.section.getString("Item").equalsIgnoreCase(Config.Item.BASIC.name()))
            return this.invalidReason;

        int durability = this.section.getInt("Durability");
        return durability > 0 ? null : "The durability of the recipe \"" + this.recipeName + "\" must be greater than 0";
    }

    @Nullable
    private String getIngredientReason() {
        if (this.invalidReason != null)
            return this.invalidReason;

        ConfigurationSection craftSection = this.section.getConfigurationSection("Craft");
        for (String slot : craftSection.getKeys(false)) {
            String ingredient = craftSection.getString(slot);
            if (Material.matchMaterial(ingredient) == null)
                return "The ingredient \"" + ingredient + "\" of the recipe \"" + this.recipeName + "\" is not an item";
        }
        return null;
    }

    @NotNull
    public ShapedRecipe toRecipe() {
        Preconditions.checkState(this.invalidReason == null, this.invalidReason);
        ShapedRecipe recipe = new ShapedRecipe(
                new NamespacedKey(ProtectedDebugStick.getInstance(), "craft-" + this.recipeName),
                this.getItemOfRecipe()
        );

        this.addIngredientsToRecipe(recipe);
        return recipe;
    }

    @NotNull
    private ItemStack getItemOfRecipe() {
        Preconditions.checkState(this.invalidReason == null, this.invalidReason);
        switch (Config.Item.valueOf(this.section.getString("Item"))) {
            case BASIC ->  {
                return DebugStick.getBasicDebugStick(this.section.getInt("Durability"));
            }
            case INFINITY -> {
                return DebugStick.getInfiniteDebugStick();
            }
            case INSPECTOR ->  {
                return DebugStick.getInspector();
            }
            default -> throw new IllegalStateException();
        }
    }

    private void addIngredientsToRecipe(@NotNull ShapedRecipe recipe) {
        Preconditions.checkState(this.invalidReason == null, this.invalidReason);
        ConfigurationSection craftSection = this.section.getConfigurationSection("Craft");
        Set<String> slots = craftSection.getKeys(false);
        recipe.shape(
                (slots.contains("1") ? "1" : " ") + (slots.contains("2") ? "2" : " ") + (slots.contains("3") ? "3" : " "),
                (slots.contains("4") ? "4" : " ") + (slots.contains("5") ? "5" : " ") + (slots.contains("6") ? "6" : " "),
                (slots.contains("7") ? "7" : " ") + (slots.contains("8") ? "8" : " ") + (slots.contains("9") ? "9" : " ")
        );

        for (String slot : slots) {
            if (POSSIBLE_FIELDS.contains(slot)) {
                recipe.setIngredient(slot.toCharArray()[0], Material.matchMaterial(craftSection.getString(slot)));
            }
        }
    }

    @Nullable
    public String getInvalidReason() {
        return invalidReason;
    }
}
