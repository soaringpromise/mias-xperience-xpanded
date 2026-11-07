package net.soaringpromise.mxx.compat.jei;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.RecipeTypes;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.alchemy.Potions;
import net.soaringpromise.mxx.Mxx;
import net.soaringpromise.mxx.registry.ModConfigs;
import org.jspecify.annotations.NonNull;

import java.util.List;

@JeiPlugin
public final class MxxJeiPlugin implements IModPlugin {
    @Override
    public @NonNull Identifier getPluginUid() {
        return Mxx.id("jei_plugin");
    }

    @Override
    public void registerRecipes(@NonNull IRecipeRegistration registration) {
        if (!ModConfigs.GAMEPLAY.allowXpBottleBrewing.get())
            return;

        var awkwardPotion = PotionContents.createItemStack(Items.POTION, Potions.AWKWARD);

        var recipe = registration.getVanillaRecipeFactory()
                .createBrewingRecipe(
                        List.of(new ItemStack(Items.ECHO_SHARD)),
                        awkwardPotion,
                        new ItemStack(Items.EXPERIENCE_BOTTLE),
                        Mxx.id("brewing/experience_bottle")
                );

        registration.addRecipes(RecipeTypes.BREWING, List.of(recipe));
    }
}