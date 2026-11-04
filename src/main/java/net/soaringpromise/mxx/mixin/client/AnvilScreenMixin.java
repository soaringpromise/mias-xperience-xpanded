package net.soaringpromise.mxx.mixin.client;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.client.gui.screens.inventory.AnvilScreen;
import net.soaringpromise.mxx.client.ClientConfigState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(AnvilScreen.class)
public abstract class AnvilScreenMixin {
    @ModifyExpressionValue(method = "extractLabels", at = @At(value = "CONSTANT", args = "intValue=40"))
    private int xperiencexpanded$modifyClientSideCap(int original) {
        return ClientConfigState.removeAnvilTooExpensiveLimit
                ? Integer.MAX_VALUE
                : original;
    }
}