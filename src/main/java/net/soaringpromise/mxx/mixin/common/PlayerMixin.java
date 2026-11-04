package net.soaringpromise.mxx.mixin.common;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.soaringpromise.mxx.registry.ModConfigs;
import net.soaringpromise.mxx.registry.ModEffects;
import net.soaringpromise.mxx.util.ExperienceTransfer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(Player.class)
public abstract class PlayerMixin extends LivingEntity {
    protected PlayerMixin(EntityType<? extends LivingEntity> type, Level level) {
        super(type, level);
    }

    @ModifyReturnValue(method = "getXpNeededForNextLevel", at = @At("RETURN"))
    private int xperiencexpanded$applyFlatExpCurve(int original) {
        return ModConfigs.GAMEPLAY.constantXpPerLevel.get()
                ? ModConfigs.GAMEPLAY.xpRequiredPerLevel.get()
                : original;
    }

    @ModifyVariable(method = "giveExperiencePoints", at = @At("HEAD"), argsOnly = true, name = "i")
    private int xperiencexpanded$applyWisdomEffect(int i) {
        if (i <= 0 || ExperienceTransfer.isRestoring()) return i;
        MobEffectInstance wisdom = this.getEffect(ModEffects.WISDOM_EFFECT);

        if (wisdom == null) return i;

        double multiplier = 1.5 + wisdom.getAmplifier() * 0.5;
        return (int) Math.min(Integer.MAX_VALUE, i * multiplier);
    }
}