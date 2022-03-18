package com.github.thedeathlycow.lostinthecold;

import com.github.thedeathlycow.lostinthecold.attributes.ModEntityAttributes;
import net.fabricmc.api.ClientModInitializer;

public class LostInTheColdClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ModEntityAttributes.registerAttributes();
    }
}
