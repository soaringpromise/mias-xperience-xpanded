package net.soaringpromise.mxx.item;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.level.Level;
import net.soaringpromise.mxx.registry.ModDataComponents;
import net.soaringpromise.mxx.util.ExperienceTransfer;
import org.jspecify.annotations.NonNull;

import java.util.function.Consumer;

public final class CatalyzedExperienceBottleItem extends Item {
    public CatalyzedExperienceBottleItem(Properties properties) {
        super(properties);
    }

    @Override
    public @NonNull InteractionResult use(@NonNull Level level, Player player, @NonNull InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        if (player.isShiftKeyDown()) {
            if (player.experienceLevel < 1) return InteractionResult.FAIL;


            if (!level.isClientSide()) {
                if (!(player instanceof ServerPlayer serverPlayer) || !depositLevel(serverPlayer, stack))
                    return InteractionResult.FAIL;

                level.playSound(
                        null, player.blockPosition(),
                        SoundEvents.BOTTLE_FILL,
                        SoundSource.PLAYERS, 0.8F, 1.0F
                );
            }
            return InteractionResult.SUCCESS;
        }
        if (getStoredXp(stack) <= 0) return InteractionResult.FAIL;

        player.startUsingItem(hand);
        return InteractionResult.CONSUME;
    }

    private static boolean depositLevel(ServerPlayer player, ItemStack stack) {
        int levelBefore = player.experienceLevel;
        if (levelBefore < 1) return false;

        int stored = getStoredXp(stack);
        float progress = player.experienceProgress;
        int neededBefore = player.getXpNeededForNextLevel();

        player.giveExperienceLevels(-1);

        int neededAfter = player.getXpNeededForNextLevel();

        int deposited = neededAfter + Math.round(progress * neededBefore) - Math.round(progress * neededAfter);

        if (deposited <= 0 || (long) stored + deposited > Integer.MAX_VALUE) {
            player.experienceLevel = levelBefore;
            return false;
        }

        player.totalExperience = Math.max(0, player.totalExperience - deposited);

        setStoredXp(stack, stored + deposited);
        return true;
    }

    @Override
    public @NonNull ItemStack finishUsingItem(@NonNull ItemStack stack, Level level, @NonNull LivingEntity entity) {
        if (!level.isClientSide() && entity instanceof ServerPlayer player) {
            int stored = getStoredXp(stack);

            if (stored > 0 && (long) player.totalExperience + stored <= Integer.MAX_VALUE) {

                ExperienceTransfer.restore(player, stored);
                setStoredXp(stack, 0);
            }
        }
        return stack;
    }

    @Override
    public boolean isFoil(@NonNull ItemStack stack) {
        return true;
    }

    @SuppressWarnings("deprecation")
    @Override
    public void appendHoverText(
            @NonNull ItemStack stack,
            @NonNull TooltipContext context,
            @NonNull TooltipDisplay display,
            Consumer<Component> tooltip,
            @NonNull TooltipFlag flag
    ) {
        tooltip.accept(
                Component.translatable("tooltip.xperience-xpanded.catalyzed_experience_bottle.flavor")
        );

        tooltip.accept(
                Component.translatable(
                        "tooltip.xperience-xpanded.catalyzed_experience_bottle.stored_xp",
                        getStoredXp(stack)
                ).withStyle(ChatFormatting.GRAY)
        );
    }

    public static int getStoredXp(ItemStack stack) {
        return stack.getOrDefault(ModDataComponents.STORED_XP, 0);
    }

    private static void setStoredXp(ItemStack stack, int xp) {
        int value = Math.max(0, xp);
        stack.set(ModDataComponents.STORED_XP, value);
    }
}
