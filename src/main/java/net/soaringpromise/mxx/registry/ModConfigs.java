package net.soaringpromise.mxx.registry;

import me.fzzyhmstrs.fzzy_config.api.ConfigApiJava;
import net.soaringpromise.mxx.Mxx;
import net.soaringpromise.mxx.config.GameplayConfig;

public final class ModConfigs {
    public static final GameplayConfig GAMEPLAY =
            ConfigApiJava.registerAndLoadConfig(GameplayConfig::new);

    private ModConfigs() {
    }

    public static void register() {
        Mxx.LOGGER.info("{}: initializing and loading configuration.", Mxx.MOD_NAME);
    }
}
