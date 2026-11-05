package net.soaringpromise.mxx.mixin.common;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.soaringpromise.mxx.registry.ModConfigs;
import net.soaringpromise.mxx.util.ExperienceBonus;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(EnchantmentHelper.class)
public abstract class FortuneXpMixin {
    @ModifyReturnValue(method = "processBlockExperience", at = @At("RETURN"))
    private static int xperiencexpanded$applyFortuneBonus(
            int original,
            ServerLevel serverLevel,
            ItemStack tool,
            int baseXp
    ) {
        if (!ModConfigs.GAMEPLAY.fortuneBoostsXp.get() || original <= 0)
            return original;

        var enchantments = serverLevel.registryAccess().lookupOrThrow(Registries.ENCHANTMENT);
        var fortune = enchantments.getOrThrow(Enchantments.FORTUNE);
        int fortuneLevel = EnchantmentHelper.getItemEnchantmentLevel(fortune, tool);

        return ExperienceBonus.apply(original, fortuneLevel);
    }
}