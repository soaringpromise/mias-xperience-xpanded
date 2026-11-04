package net.soaringpromise.mxx.mixin.common;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.tags.EnchantmentTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.soaringpromise.mxx.registry.ModConfigs;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(targets = "net.minecraft.world.inventory.GrindstoneMenu$4")
public abstract class GrindstoneResultSlotMixin {

    @Unique
    private boolean xperiencexpanded$hasCurse;

    @Inject(method = "getExperienceAmount(Lnet/minecraft/world/level/Level;)I", at = @At("HEAD"))
    private void xperiencexpanded$resetCurseState(Level level, CallbackInfoReturnable<Integer> cir) {
        xperiencexpanded$hasCurse = false;
    }

    @ModifyReturnValue(method = "getExperienceFromItem(Lnet/minecraft/world/item/ItemStack;)I", at = @At("RETURN"))
    private int xperiencexpanded$detectCurses(int original, ItemStack item) {
        var enchantments = EnchantmentHelper.getEnchantmentsForCrafting(item);

        for (var entry : enchantments.entrySet()) {
            if (entry.getKey().is(EnchantmentTags.CURSE)) {
                xperiencexpanded$hasCurse = true;
                break;
            }
        }
        return original;
    }

    @Inject(
            method = "getExperienceAmount(Lnet/minecraft/world/level/Level;)I",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/util/RandomSource;nextInt(I)I"
            ),
            cancellable = true
    )
    private void xperiencexpanded$calculateXp(
            Level level,
            CallbackInfoReturnable<Integer> cir,
            @Local(name = "amount") int amount)
    {
        double multiplier = ModConfigs.GAMEPLAY.grindstoneXpMultiplier.get();

        if (xperiencexpanded$hasCurse && ModConfigs.GAMEPLAY.allowGrindstoneCurseRemoval.get())
            multiplier *= ModConfigs.GAMEPLAY.grindstoneCurseXpMultiplier.get();

        cir.setReturnValue((int) Math.round(amount * multiplier));
    }
}