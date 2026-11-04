package net.soaringpromise.mxx.registry;

import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.soaringpromise.mxx.network.SyncConfigPayload;

public final class ModPayloads {
    public static void register() {
        PayloadTypeRegistry.clientboundPlay().register(
                SyncConfigPayload.TYPE,
                SyncConfigPayload.STREAM_CODEC
        );
    }

    private ModPayloads() {}
}