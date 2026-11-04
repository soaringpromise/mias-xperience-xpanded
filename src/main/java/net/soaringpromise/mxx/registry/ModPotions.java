package net.soaringpromise.mxx.registry;

import net.fabricmc.fabric.api.registry.FabricPotionBrewingBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.Potions;
import net.soaringpromise.mxx.Mxx;

public final class ModPotions {
    public static final Holder<Potion> WISDOM_POTION = registerPotion(
            "wisdom",
            new Potion("wisdom",
                    new MobEffectInstance(ModEffects.WISDOM_EFFECT, 2400, 0))
    );

    public static final Holder<Potion> LONG_WISDOM_POTION = registerPotion(
            "long_wisdom",
            new Potion("long_wisdom",
                    new MobEffectInstance(ModEffects.WISDOM_EFFECT, 4800, 0))
    );

    public static final Holder<Potion> STRONG_WISDOM_POTION = registerPotion(
            "strong_wisdom",
            new Potion("strong_wisdom",
                    new MobEffectInstance(ModEffects.WISDOM_EFFECT, 1800, 1))
    );

    private static Holder<Potion> registerPotion(String name, Potion potion) {
        return Registry.registerForHolder(BuiltInRegistries.POTION, Mxx.id(name), potion);
    }

    public static void register() {
        Mxx.LOGGER.info("{}: registering custom potions.", Mxx.MOD_NAME);
        FabricPotionBrewingBuilder.BUILD.register(builder -> {
            builder.addMix(Potions.AWKWARD, Items.AMETHYST_SHARD, WISDOM_POTION);
            builder.addMix(WISDOM_POTION, Items.REDSTONE, LONG_WISDOM_POTION);
            builder.addMix(WISDOM_POTION, Items.GLOWSTONE_DUST, STRONG_WISDOM_POTION);
        });
    }

    private ModPotions() {}
}