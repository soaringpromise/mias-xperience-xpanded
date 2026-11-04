package net.soaringpromise.mxx.mixin.common;

import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.world.inventory.AnvilMenu;
import net.soaringpromise.mxx.registry.ModConfigs;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Slice;

@Mixin(AnvilMenu.class)
public abstract class AnvilMenuMixin {
    @WrapMethod(method = "calculateIncreasedRepairCost")
    private static int xperiencexpanded$disablePriorWorkPenalty(int baseCost, Operation<Integer> original) {
        return ModConfigs.GAMEPLAY.removeAnvilPriorWorkPenalty.get() ? 0 : original.call(baseCost);
    }

    @Definition(id = "cost", field = "Lnet/minecraft/world/inventory/AnvilMenu;cost:Lnet/minecraft/world/inventory/DataSlot;")
    @Definition(id = "get", method = "Lnet/minecraft/world/inventory/DataSlot;get()I")
    @Definition(id = "set", method = "Lnet/minecraft/world/inventory/DataSlot;set(I)V")
    @Expression(value = "this.cost.get() >= 40", id = "target")
    @Expression(value = "this.cost.set(39)", id = "sliceFrom")
    @WrapOperation(
            method = "createResult",
            at = @At(value = "MIXINEXTRAS:EXPRESSION", id = "target"),
            slice = @Slice(from = @At(value = "MIXINEXTRAS:EXPRESSION", id = "sliceFrom"))
    )
    private boolean xperiencexpanded$overrideTooExpensiveCheck(int left, int right, Operation<Boolean> original) {
        return !ModConfigs.GAMEPLAY.removeAnvilTooExpensiveLimit.get() && original.call(left, right);
    }

    @ModifyVariable(method = "createResult", at = @At(value = "STORE", ordinal = 1), name = "tax", require = 1)
    private long xperiencexpanded$removeExistingWorkPenalty(long tax) {
        return ModConfigs.GAMEPLAY.removeAnvilPriorWorkPenalty.get()
                ? 0L
                : tax;
    }
}