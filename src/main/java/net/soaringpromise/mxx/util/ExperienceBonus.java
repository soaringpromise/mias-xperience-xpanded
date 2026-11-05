package net.soaringpromise.mxx.util;

public final class ExperienceBonus {
    private static final double BONUS_PER_LEVEL = 0.2;

    public static int apply(int baseXp, int enchantmentLevel) {
        if (baseXp <= 0 || enchantmentLevel <= 0)
            return baseXp;

        return (int) Math.round(baseXp * (1.0 + BONUS_PER_LEVEL * enchantmentLevel)
        );
    }

    private ExperienceBonus() {}
}