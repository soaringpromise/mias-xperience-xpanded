package net.soaringpromise.mxx.network;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.soaringpromise.mxx.client.ClientConfigState;

public final class SyncConfigPayloadHandler {
    private SyncConfigPayloadHandler() {
    }

    public static void register() {
        ClientPlayNetworking.registerGlobalReceiver(
                SyncConfigPayload.TYPE,
                (payload, _) -> ClientConfigState.removeAnvilTooExpensiveLimit = payload.removeAnvilTooExpensiveLimit()
        );
    }
}