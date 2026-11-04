package net.soaringpromise.mxx.registry;

import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.soaringpromise.mxx.Mxx;
import net.soaringpromise.mxx.effect.WisdomEffect;

public final class ModEffects {
    public static final Holder<MobEffect> WISDOM_EFFECT = registerMobEffect("wisdom",
            new WisdomEffect(MobEffectCategory.BENEFICIAL, 0xBB88EE));

    private static Holder<MobEffect> registerMobEffect(String name, MobEffect effect) {
        return Registry.registerForHolder(BuiltInRegistries.MOB_EFFECT, Mxx.id(name), effect);
    }

    public static void register() {
        Mxx.LOGGER.info("{}: registering custom effects.", Mxx.MOD_NAME);
    }

    private ModEffects() {}
}