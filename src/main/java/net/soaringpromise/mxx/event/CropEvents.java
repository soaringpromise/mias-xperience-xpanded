package net.soaringpromise.mxx.event;

import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.NetherWartBlock;
import net.soaringpromise.mxx.registry.ModConfigs;

public final class CropEvents {
    public static void register() {
        PlayerBlockBreakEvents.AFTER.register((level, player, pos, state, _) -> {
            if (!ModConfigs.GAMEPLAY.xpFromBreakingMatureCrops.get() || player.isCreative())
                return;

            if (!(level instanceof ServerLevel serverLevel))
                return;

            boolean matureCrop = state.getBlock() instanceof CropBlock crop && crop.isMaxAge(state);
            boolean matureWart = state.getBlock() instanceof NetherWartBlock && state.getValue(NetherWartBlock.AGE) >= 3;

            if (!matureCrop && !matureWart)
                return;

            ExperienceOrb.award(serverLevel, pos.getCenter(), ModConfigs.GAMEPLAY.xpPerMatureCrop.get());
        });
    }

    private CropEvents() {}
}