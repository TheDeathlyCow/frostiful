package com.github.thedeathlycow.frostiful.mixins;

import net.minecraft.resources.Identifier;
import org.apache.commons.lang3.NotImplementedException;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(Identifier.class)
public interface IdentifierInvoker {
    @Invoker("<init>")
    static Identifier invokeConstructor(final String namespace, final String path) {
        throw new NotImplementedException();
    }

    @Invoker("assertValidPath")
    static String invokeAssertValidPath(final String namespace, final String path) {
        throw new NotImplementedException();
    }
}