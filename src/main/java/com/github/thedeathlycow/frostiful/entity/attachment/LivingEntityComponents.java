package com.github.thedeathlycow.frostiful.entity.attachment;

import com.github.thedeathlycow.frostiful.registry.FrostifulEntityAttachments;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.neoforged.neoforge.attachment.AttachmentSyncHandler;
import net.neoforged.neoforge.attachment.IAttachmentHolder;
import org.jetbrains.annotations.Nullable;

public class LivingEntityComponents {
    private byte skateFlags;

    private final IAttachmentHolder provider;

    public LivingEntityComponents(IAttachmentHolder provider) {
        this(provider, (byte) 0); // i love java
    }

    private LivingEntityComponents(IAttachmentHolder provider, byte skateFlags) {
        this.provider = provider;
        this.skateFlags = skateFlags;
    }

    public byte getSkateFlags() {
        return skateFlags;
    }

    public void setSkateFlags(byte skateFlags) {
        if (this.skateFlags != skateFlags) {
            this.skateFlags = skateFlags;
            this.provider.syncData(FrostifulEntityAttachments.ENTITY_COMPONENTS);
        }
    }

    public static final class SyncHandler implements AttachmentSyncHandler<LivingEntityComponents> {
        @Override
        public void write(RegistryFriendlyByteBuf buf, LivingEntityComponents attachment, boolean initialSync) {
            buf.writeLong(attachment.getSkateFlags());
        }

        @Override
        public LivingEntityComponents read(IAttachmentHolder holder, RegistryFriendlyByteBuf buf, @Nullable LivingEntityComponents previousValue) {
            return new LivingEntityComponents(holder, buf.readByte());
        }
    }
}
