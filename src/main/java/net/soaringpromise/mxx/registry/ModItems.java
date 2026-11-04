
package net.soaringpromise.mxx.registry;

import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.component.Consumables;
import net.soaringpromise.mxx.Mxx;
import net.soaringpromise.mxx.item.CatalyzedExperienceBottleItem;

public final class ModItems {

    private static final ResourceKey<Item> BOTTLE_KEY =
            ResourceKey.create(
                    Registries.ITEM,
                    Mxx.id("catalyzed_experience_bottle")
            );

    public static final Item CATALYZED_EXPERIENCE_BOTTLE =
            Registry.register(
                    BuiltInRegistries.ITEM,
                    BOTTLE_KEY,
                    new CatalyzedExperienceBottleItem(
                            new Item.Properties()
                                    .setId(BOTTLE_KEY)
                                    .stacksTo(1)
                                    .rarity(Rarity.UNCOMMON)
                                    .component(ModDataComponents.STORED_XP, 0)
                                    .component(DataComponents.CONSUMABLE, Consumables.DEFAULT_DRINK)
                    )
            );

    public static void register() {}

    private ModItems() {}
}