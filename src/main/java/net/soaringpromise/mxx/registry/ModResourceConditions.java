package net.soaringpromise.mxx.registry;

import net.fabricmc.fabric.api.resource.conditions.v1.ResourceConditions;
import net.soaringpromise.mxx.Mxx;
import net.soaringpromise.mxx.recipe.MxxResourceCondition;

public final class ModResourceConditions {
    public static void register() {
        Mxx.LOGGER.info("{}: registering custom conditionals.", Mxx.MOD_NAME);
        ResourceConditions.register(MxxResourceCondition.TYPE);
    }

    private ModResourceConditions() {}
}