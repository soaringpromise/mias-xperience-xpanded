package net.soaringpromise.mxx.mixin.common;

import net.minecraft.world.inventory.GrindstoneMenu;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.soaringpromise.mxx.registry.ModConfigs;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import java.util.function.Consumer;

@Mixin(GrindstoneMenu.class)
public abstract class GrindstoneMenuMixin {

    @ModifyArg(
            method = "removeNonCursesFrom",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/item/enchantment/EnchantmentHelper;updateEnchantments(Lnet/minecraft/world/item/ItemStack;Ljava/util/function/Consumer;)Lnet/minecraft/world/item/enchantment/ItemEnchantments;"
            ),
            index = 1
    )
    private Consumer<ItemEnchantments.Mutable> xperiencexpanded$removeCurses(Consumer<ItemEnchantments.Mutable> original) {
        if (!ModConfigs.GAMEPLAY.allowGrindstoneCurseRemoval.get())
            return original;

        return enchantments -> enchantments.removeIf(_ -> true);
    }
}