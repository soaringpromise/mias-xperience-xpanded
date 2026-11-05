package net.soaringpromise.mxx.mixin.common;

import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.FishingHook;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.soaringpromise.mxx.registry.ModConfigs;
import net.soaringpromise.mxx.util.ExperienceBonus;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(FishingHook.class)
public abstract class FishingHookMixin extends Projectile {
    public FishingHookMixin(EntityType<? extends Projectile> type, Level level) {
        super(type, level);
    }

    @ModifyArgs(
            method = "retrieve",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/ExperienceOrb;<init>(Lnet/minecraft/world/level/Level;DDDI)V"
            )
    )
    private void xperiencexpanded$applyFishingXpBonus(Args args, ItemStack rod) {
        if (!(this.level() instanceof ServerLevel level))
            return;

        var enchantments = level.registryAccess().lookupOrThrow(Registries.ENCHANTMENT);
        var luckOfTheSea = enchantments.getOrThrow(Enchantments.LUCK_OF_THE_SEA);

        int enchantmentLevel = EnchantmentHelper.getItemEnchantmentLevel(luckOfTheSea, rod);
        int original = args.get(4);

        boolean enabled = ModConfigs.GAMEPLAY.luckOfTheSeaBoostsXp.get();

        int adjusted = enabled
                ? ExperienceBonus.apply(original, enchantmentLevel)
                : original;

        args.set(4, adjusted);
    }
}