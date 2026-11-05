package net.soaringpromise.mxx.event;

import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.NetherWartBlock;
import net.soaringpromise.mxx.registry.ModConfigs;

public final class BlockXpEvents {
    public static void register() {
        PlayerBlockBreakEvents.AFTER.register((level, player, pos, state, _) -> {
            if (player.isCreative() || !(level instanceof ServerLevel serverLevel))
                return;

            int xp = 0;

            boolean matureCrop = state.getBlock() instanceof CropBlock crop && crop.isMaxAge(state);
            boolean matureWart = state.getBlock() instanceof NetherWartBlock && state.getValue(NetherWartBlock.AGE) >= 3;

            if ((matureCrop || matureWart) && ModConfigs.GAMEPLAY.xpFromBreakingMatureCrops.get()) {
                xp = ModConfigs.GAMEPLAY.xpPerMatureCrop.get();
            } else if (state.is(Blocks.AMETHYST_CLUSTER) && ModConfigs.GAMEPLAY.xpFromBreakingAmethystClusters.get()) {
                xp = ModConfigs.GAMEPLAY.xpPerAmethystCluster.get();
            }

            if (xp > 0) {
                int finalXp = EnchantmentHelper.processBlockExperience(serverLevel, player.getMainHandItem(), xp);
                if (finalXp > 0) ExperienceOrb.award(serverLevel, pos.getCenter(), finalXp);
            }
        });
    }

    private BlockXpEvents() {}
}