package net.soaringpromise.mxx.event;

import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.gamerules.GameRules;
import net.soaringpromise.mxx.registry.ModConfigs;

public final class PlayerEvents {
    public static void register() {
        ServerPlayerEvents.AFTER_RESPAWN.register((oldPlayer, newPlayer, alive) -> {
            if (alive) return;
            if (oldPlayer.level().getGameRules().get(GameRules.KEEP_INVENTORY)) return;
            if (!ModConfigs.GAMEPLAY.retainXpOnDeath.get()) return;

            int availableXp = calculateAvailableXp(oldPlayer);
            int retainedXp = (int) (availableXp * (ModConfigs.GAMEPLAY.retainedXpPercentage.get() / 100.0));

            newPlayer.giveExperiencePoints(retainedXp);
        });
    }

    private static int calculateAvailableXp(Player player) {
        int level = player.experienceLevel;
        long xp;

        if (ModConfigs.GAMEPLAY.constantXpPerLevel.get()) {
            xp = (long) level * ModConfigs.GAMEPLAY.xpRequiredPerLevel.get();
        } else if (level <= 16) {
            xp = (long) level * level + 6L * level;
        } else if (level <= 31) {
            xp = (5L * level * level - 81L * level + 720) / 2;
        } else {
            xp = (9L * level * level - 325L * level + 4440) / 2;
        }

        xp += Math.round(player.experienceProgress * player.getXpNeededForNextLevel());

        return (int) Math.min(xp, Integer.MAX_VALUE);
    }

    private PlayerEvents() {}
}