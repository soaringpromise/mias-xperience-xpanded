package net.soaringpromise.mxx.mixin.common;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantedItemInUse;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.soaringpromise.mxx.registry.ModConfigs;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

import java.util.Optional;
import java.util.function.Predicate;

@Mixin(ExperienceOrb.class)
public abstract class ExperienceOrbMixin {
    @Unique
    private static double xperiencexpanded$getNewRange() {
        return ModConfigs.GAMEPLAY.xpOrbAttractionRange.get();
    }

    @WrapOperation(
            method = "repairPlayerItems",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/item/enchantment/EnchantmentHelper;getRandomItemWith(Lnet/minecraft/core/component/DataComponentType;Lnet/minecraft/world/entity/LivingEntity;Ljava/util/function/Predicate;)Ljava/util/Optional;")
    )
    private Optional<EnchantedItemInUse> xperiencexpanded$overrideMendingTarget(
            DataComponentType<?> componentType,
            LivingEntity source,
            Predicate<ItemStack> predicate,
            Operation<Optional<EnchantedItemInUse>> original)
    {
        if (!ModConfigs.GAMEPLAY.mendingPrefersHighestDamagePercent.get() || !(source instanceof ServerPlayer player))
            return original.call(componentType, source, predicate);

        EnchantedItemInUse candidate = null;
        var highestDamageRatio = -1.0;

        for (EquipmentSlot slot : EquipmentSlot.values()) {
            ItemStack stack = player.getItemBySlot(slot);

            if (stack.isEmpty()
                    || !stack.isDamageableItem()
                    || !predicate.test(stack)
                    || !EnchantmentHelper.has(stack, componentType)) {
                continue;
            }

            double damageRatio = (double) stack.getDamageValue() / stack.getMaxDamage();

            if (damageRatio > highestDamageRatio) {
                highestDamageRatio = damageRatio;
                candidate = new EnchantedItemInUse(stack, slot, player);
            }
        }
        return Optional.ofNullable(candidate);
    }

    @ModifyExpressionValue(
            method = "followNearbyPlayer", at = @At(value = "CONSTANT", args = "doubleValue=8.0"), require = 2)
    private double xperiencexpanded$modifyFollowRange(double original) {
        return xperiencexpanded$getNewRange();
    }

    @ModifyExpressionValue(
            method = "followNearbyPlayer", at = @At(value = "CONSTANT", args = "doubleValue=64.0"), require = 1)
    private double xperiencexpanded$modifyFollowRangeSquared(double original) {
        double range = xperiencexpanded$getNewRange();
        return range * range;
    }
}