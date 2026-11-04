package net.soaringpromise.mxx.mixin.common;

import com.mojang.authlib.GameProfile;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gamerules.GameRules;
import net.soaringpromise.mxx.registry.ModConfigs;
import org.jspecify.annotations.NonNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayer.class)
public abstract class ServerPlayerMixin extends Player {
    @Shadow
    public abstract @NonNull ServerLevel level();

    public ServerPlayerMixin(Level level, GameProfile gameProfile) {
        super(level, gameProfile);
    }

    @Inject(method = "die", at = @At("HEAD"))
    private void xperiencexpanded$skipDeathXpDrops(DamageSource source, CallbackInfo ci) {
        if (!ModConfigs.GAMEPLAY.retainXpOnDeath.get()) return;
        if (this.level().getGameRules().get(GameRules.KEEP_INVENTORY)) return;

        this.skipDropExperience();
    }
}