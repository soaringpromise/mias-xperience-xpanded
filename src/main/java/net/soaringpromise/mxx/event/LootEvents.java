package net.soaringpromise.mxx.event;

import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.soaringpromise.mxx.registry.ModConfigs;

public final class LootEvents {
    private LootEvents() {
    }

    public static void register() {
        LootTableEvents.MODIFY_DROPS.register((holder, context, drops) -> {
            if (!ModConfigs.GAMEPLAY.allowSilkTouchBuddingAmethyst.get())
                return;

            if (!holder.is(Blocks.BUDDING_AMETHYST.getLootTable().orElseThrow()))
                return;

            var tool = context.getOptionalParameter(LootContextParams.TOOL);
            if (tool == null) return;

            var enchantments = context.getLevel()
                    .registryAccess()
                    .lookupOrThrow(Registries.ENCHANTMENT);

            var silkTouch = enchantments.getOrThrow(Enchantments.SILK_TOUCH);

            if (EnchantmentHelper.getItemEnchantmentLevel(silkTouch, tool) <= 0)
                return;

            if (drops.isEmpty())
                drops.add(new ItemStack(Blocks.BUDDING_AMETHYST));
        });
    }
}