package net.soaringpromise.mxx.mixin.common;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.alchemy.Potions;
import net.soaringpromise.mxx.registry.ModConfigs;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(PotionBrewing.class)
public abstract class PotionBrewingMixin {

    @Unique
    private static boolean xperiencexpanded$isXpBottleRecipe(ItemStack source, ItemStack ingredient) {
        return ModConfigs.GAMEPLAY.allowXpBottleBrewing.get()
                && ingredient.is(Items.ECHO_SHARD)
                && source.is(Items.POTION)
                && source.getOrDefault(DataComponents.POTION_CONTENTS, PotionContents.EMPTY).is(Potions.AWKWARD);
    }

    @ModifyReturnValue(method = "isIngredient", at = @At("RETURN"))
    private boolean xperiencexpanded$allowEchoShard(boolean original, ItemStack ingredient) {
        return original || (ModConfigs.GAMEPLAY.allowXpBottleBrewing.get() && ingredient.is(Items.ECHO_SHARD));
    }

    @ModifyReturnValue(method = "hasMix", at = @At("RETURN"))
    private boolean xperiencexpanded$allowXpBottleMix(boolean original, ItemStack source, ItemStack ingredient) {
        return original || xperiencexpanded$isXpBottleRecipe(source, ingredient);
    }

    @ModifyReturnValue(method = "mix", at = @At("RETURN"))
    private ItemStack xperiencexpanded$brewXpBottle(ItemStack original, ItemStack ingredient, ItemStack source) {
        return xperiencexpanded$isXpBottleRecipe(source, ingredient)
                ? new ItemStack(Items.EXPERIENCE_BOTTLE)
                : original;
    }
}