package net.soaringpromise.mxx.registry;

import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.util.ExtraCodecs;
import net.soaringpromise.mxx.Mxx;

public final class ModDataComponents {

    public static final DataComponentType<Integer> STORED_XP =
            Registry.register(
                    BuiltInRegistries.DATA_COMPONENT_TYPE,
                    Mxx.id("stored_xp"),
                    DataComponentType.<Integer>builder()
                            .persistent(ExtraCodecs.NON_NEGATIVE_INT)
                            .networkSynchronized(ByteBufCodecs.VAR_INT)
                            .build()
            );

    public static void register() {}

    private ModDataComponents() {}
}