package com.github.thedeathlycow.lostinthecold.items;

import com.github.thedeathlycow.lostinthecold.init.LostInTheCold;
import com.github.thedeathlycow.lostinthecold.init.LostInTheColdClient;
import com.github.thedeathlycow.lostinthecold.init.OnInitializeListener;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModItems implements OnInitializeListener {


    @Override
    public void onInitialize() {

    }

    private static void register(String id, Item item) {
        Registry.register(Registry.ITEM, new Identifier(LostInTheCold.MODID, id), item);
    }

    private ModItems() {
        LostInTheCold.addOnInitializeListener(this);
        LostInTheColdClient.addOnInitializeListener(this);
    }

    private static final ModItems INSTANCE = new ModItems();
}
