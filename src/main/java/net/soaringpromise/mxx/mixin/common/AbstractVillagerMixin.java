package net.soaringpromise.mxx.mixin.common;

import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.npc.villager.AbstractVillager;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.*;
import net.soaringpromise.mxx.registry.ModConfigs;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractVillager.class)
public abstract class AbstractVillagerMixin {

    @Inject(method = "addOffersFromTradeSet", at = @At("TAIL"))
    private void xperiencexpanded$replaceClericXpBottleTrade(
            ServerLevel level,
            MerchantOffers offers,
            ResourceKey<TradeSet> resourceKey,
            CallbackInfo ci
    ) {
        if (!ModConfigs.GAMEPLAY.replaceClericXpBottleTradeWithEchoShards.get()) return;
        if (!resourceKey.equals(TradeSets.CLERIC_LEVEL_5)) return;

        for (int i = 0; i < offers.size(); i++) {
            MerchantOffer offer = offers.get(i);

            if (!offer.getResult().is(Items.EXPERIENCE_BOTTLE))
                continue;

            offers.set(i, new MerchantOffer(
                    new ItemCost(Items.EMERALD, 3),
                    new ItemStack(Items.ECHO_SHARD),
                    10,
                    20,
                    0.05F
            ));
        }
    }
}