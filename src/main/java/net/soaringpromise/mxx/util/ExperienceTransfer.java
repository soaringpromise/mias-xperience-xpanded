package net.soaringpromise.mxx.util;

import net.minecraft.world.entity.player.Player;

public final class ExperienceTransfer {
    private static final ThreadLocal<Boolean> RESTORING = ThreadLocal.withInitial(() -> false);

    public static boolean isRestoring() {
        return RESTORING.get();
    }

    public static void restore(Player player, int xp) {
        boolean previous = RESTORING.get();
        RESTORING.set(true);

        try {
            player.giveExperiencePoints(xp);
        } finally {
            if (previous) {
                RESTORING.set(true);
            } else {
                RESTORING.remove();
            }
        }
    }

    private ExperienceTransfer() {}
}