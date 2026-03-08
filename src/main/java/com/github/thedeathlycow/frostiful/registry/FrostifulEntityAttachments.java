package com.github.thedeathlycow.frostiful.registry;

import com.github.thedeathlycow.frostiful.Frostiful;
import com.github.thedeathlycow.frostiful.entity.attachment.BrushableComponent;
import com.github.thedeathlycow.frostiful.entity.attachment.FrostWandRootComponent;
import com.github.thedeathlycow.frostiful.entity.attachment.LivingEntityComponents;
import com.github.thedeathlycow.frostiful.entity.attachment.SnowAccumulationComponent;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public final class FrostifulEntityAttachments {
    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(
            NeoForgeRegistries.ATTACHMENT_TYPES,
            Frostiful.MODID
    );

    public static final Supplier<AttachmentType<LivingEntityComponents>> ENTITY_COMPONENTS = ATTACHMENT_TYPES.register(
            "living_entity",
            () -> AttachmentType.builder(LivingEntityComponents::new)
                    .sync(new LivingEntityComponents.SyncHandler())
                    .build()
    );

    public static final Supplier<AttachmentType<FrostWandRootComponent>> FROST_WAND_ROOT_COMPONENT = ATTACHMENT_TYPES.register(
            "frost_wand_root",
            () -> AttachmentType.serializable(FrostWandRootComponent::new)
                    .sync(new FrostWandRootComponent.SyncHandler())
                    .build()
    );

    public static final Supplier<AttachmentType<BrushableComponent>> BRUSHABLE_COMPONENT = ATTACHMENT_TYPES.register(
            "brushable",
            () -> AttachmentType.serializable(BrushableComponent::new)
                    .sync(new BrushableComponent.SyncHandler())
                    .build()
    );

    public static final Supplier<AttachmentType<SnowAccumulationComponent>> SNOW_ACCUMULATION = ATTACHMENT_TYPES.register(
            "snow_accumulation",
            () -> AttachmentType.serializable(SnowAccumulationComponent::new)
                    .build()
    );

    private FrostifulEntityAttachments() {

    }
}
