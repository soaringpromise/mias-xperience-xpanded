package net.soaringpromise.mxx.mixin.common;

import net.minecraft.world.entity.projectile.throwableitemprojectile.ThrownExperienceBottle;
import net.soaringpromise.mxx.registry.ModConfigs;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(ThrownExperienceBottle.class)
public abstract class ExperienceBottleMixin {
    @ModifyVariable(method = "onHit", at = @At("STORE"), name = "xpCount")
    private int xperiencexpanded$overrideExperienceReward(int xpCount) {
        if (!ModConfigs.GAMEPLAY.useFixedXpBottleReward.get())
            return xpCount;

        return ModConfigs.GAMEPLAY.xpPerExperienceBottle.get();
    }
}