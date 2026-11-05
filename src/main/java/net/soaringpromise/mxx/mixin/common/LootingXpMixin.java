package net.soaringpromise.mxx.mixin.common;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.soaringpromise.mxx.registry.ModConfigs;
import net.soaringpromise.mxx.util.ExperienceBonus;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(EnchantmentHelper.class)
public abstract class LootingXpMixin {
    @ModifyReturnValue(method = "processMobExperience",at = @At("RETURN"))
    private static int xperiencexpanded$applyLootingBonus(
            int original,
            ServerLevel serverLevel,
            Entity killer,
            Entity killed,
            int baseXp
    ) {
        if (!ModConfigs.GAMEPLAY.lootingBoostsXp.get()
                || original <= 0
                || !(killer instanceof Player player)
                || !(killed instanceof Mob)) {
            return original;
        }

        var enchantments = serverLevel.registryAccess().lookupOrThrow(Registries.ENCHANTMENT);
        var looting = enchantments.getOrThrow(Enchantments.LOOTING);
        int lootingLevel = EnchantmentHelper.getItemEnchantmentLevel(looting, player.getMainHandItem());

        return ExperienceBonus.apply(original, lootingLevel);
    }
}