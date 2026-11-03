package net.soaringpromise.mxx.registry;

import me.fzzyhmstrs.fzzy_config.api.ConfigApiJava;
import net.soaringpromise.mxx.MXX;
import net.soaringpromise.mxx.config.GameplayConfig;

public final class ModConfigs {
    public static final GameplayConfig GAMEPLAY =
            ConfigApiJava.registerAndLoadConfig(GameplayConfig::new);

    private ModConfigs() {
    }

    public static void register() {
        MXX.LOGGER.info("{}: initializing and loading configuration.", MXX.MOD_NAME);
    }
}
