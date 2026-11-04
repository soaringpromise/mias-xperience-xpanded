package net.soaringpromise.mxx.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.fabricmc.fabric.api.resource.conditions.v1.ResourceCondition;
import net.fabricmc.fabric.api.resource.conditions.v1.ResourceConditionType;
import net.minecraft.resources.RegistryOps;
import net.soaringpromise.mxx.Mxx;
import net.soaringpromise.mxx.registry.ModConfigs;
import org.jspecify.annotations.NonNull;

public record MxxResourceCondition(String feature) implements ResourceCondition {

    public static final MapCodec<MxxResourceCondition> CODEC =
            Codec.STRING.fieldOf("feature").xmap(MxxResourceCondition::new, MxxResourceCondition::feature);

    public static final ResourceConditionType<MxxResourceCondition> TYPE =
            ResourceConditionType.create(Mxx.id("feature_enabled"), CODEC);

    @Override
    public boolean test(RegistryOps.RegistryInfoLookup lookup) {
        return feature.equalsIgnoreCase("renewable_echo_shards")
                && ModConfigs.GAMEPLAY.enableEchoShardCrafting.get();
    }

    @Override
    public @NonNull ResourceConditionType<?> getType() {
        return TYPE;
    }
}