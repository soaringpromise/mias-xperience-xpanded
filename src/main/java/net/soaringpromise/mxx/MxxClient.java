package net.soaringpromise.mxx;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.soaringpromise.mxx.client.ClientConfigState;
import net.soaringpromise.mxx.network.SyncConfigPayloadHandler;

public final class MxxClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        SyncConfigPayloadHandler.register();

        ClientPlayConnectionEvents.DISCONNECT.register((_, _) ->
                ClientConfigState.removeAnvilTooExpensiveLimit = false);
    }
}