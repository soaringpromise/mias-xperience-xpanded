package net.soaringpromise.mxx.config;

import me.fzzyhmstrs.fzzy_config.config.Config;
import me.fzzyhmstrs.fzzy_config.config.ConfigGroup;
import me.fzzyhmstrs.fzzy_config.validation.misc.ValidatedBoolean;
import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedDouble;
import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedInt;
import net.soaringpromise.mxx.Mxx;

public class GameplayConfig extends Config {

    // Experience Progression
    public ConfigGroup experienceProgression = new ConfigGroup("experienceProgression");

    public ValidatedBoolean constantXpPerLevel = new ValidatedBoolean(true);
    @ConfigGroup.Pop
    public ValidatedInt xpRequiredPerLevel = new ValidatedInt(30, 100, 10);


    // Experience Retention
    public ConfigGroup experienceRetention = new ConfigGroup("experienceRetention");

    public ValidatedBoolean retainXpOnDeath = new ValidatedBoolean(true);
    @ConfigGroup.Pop
    public ValidatedInt retainedXpPercentage = new ValidatedInt(100, 100, 10);


    // Experience Sources
    public ConfigGroup experienceSources = new ConfigGroup("experienceSources");

    public ValidatedBoolean xpFromBreakingMatureCrops = new ValidatedBoolean(true);
    public ValidatedInt xpPerMatureCrop = new ValidatedInt(3, 10, 1);
    public ValidatedBoolean xpFromBreakingAmethystClusters = new ValidatedBoolean(true);
    @ConfigGroup.Pop
    public ValidatedInt xpPerAmethystCluster = new ValidatedInt(2, 10, 1);


    // Bottles o' Enchanting
    public ConfigGroup experienceBottles = new ConfigGroup("experienceBottles");

    public ValidatedBoolean allowXpBottleBrewing = new ValidatedBoolean(true);
    public ValidatedBoolean useFixedXpBottleReward = new ValidatedBoolean(true);
    @ConfigGroup.Pop
    public ValidatedInt xpPerExperienceBottle = new ValidatedInt(15, 30, 10);


    // Anvil Improvements
    public ConfigGroup anvilImprovements = new ConfigGroup("anvilImprovements");

    public ValidatedBoolean removeAnvilTooExpensiveLimit = new ValidatedBoolean(true);
    @ConfigGroup.Pop
    public ValidatedBoolean removeAnvilPriorWorkPenalty = new ValidatedBoolean(true);


    // Grindstone Improvements
    public ConfigGroup grindstoneImprovements = new ConfigGroup("grindstoneImprovements");

    public ValidatedDouble grindstoneXpMultiplier = new ValidatedDouble(1.25, 5.0, 0.0);
    public ValidatedBoolean allowGrindstoneCurseRemoval = new ValidatedBoolean(true);
    @ConfigGroup.Pop
    public ValidatedDouble grindstoneCurseXpMultiplier = new ValidatedDouble(0.5, 1.0, 0.0);


    // Experience Quality of Life
    public ConfigGroup experienceQualityOfLife = new ConfigGroup("experienceQualityOfLife");

    public ValidatedBoolean mendingPrefersHighestDamagePercent = new ValidatedBoolean(true);
    @ConfigGroup.Pop
    public ValidatedDouble xpOrbAttractionRange = new ValidatedDouble(10.0, 16.0, 8.0);


    // Renewable Resources
    public ConfigGroup renewableResources = new ConfigGroup("renewableResources");

    public ValidatedBoolean enableEchoShardCrafting = new ValidatedBoolean(true);
    @ConfigGroup.Pop
    public ValidatedBoolean allowSilkTouchBuddingAmethyst = new ValidatedBoolean(true);


    // Villager Trades
    public ConfigGroup villagerTrades = new ConfigGroup("villagerTrades");
    @ConfigGroup.Pop
    public ValidatedBoolean replaceClericXpBottleTradeWithEchoShards = new ValidatedBoolean(true);

    public GameplayConfig() {
        super(Mxx.id("config"));
    }
}