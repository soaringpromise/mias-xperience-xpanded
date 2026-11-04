package net.soaringpromise.mxx.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.soaringpromise.mxx.Mxx;
import org.jspecify.annotations.NonNull;

public record SyncConfigPayload(boolean removeAnvilTooExpensiveLimit) implements CustomPacketPayload {
    public static final Type<SyncConfigPayload> TYPE = new Type<>(Mxx.id("sync_config"));

    public static final StreamCodec<FriendlyByteBuf, SyncConfigPayload> STREAM_CODEC =
            StreamCodec.of(
                    (buf, payload) -> buf.writeBoolean(payload.removeAnvilTooExpensiveLimit()),
                    buf -> new SyncConfigPayload(buf.readBoolean())
            );

    @Override
    public @NonNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
