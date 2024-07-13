package com.ffanxxy.commandblockrunner.item;

import com.ffanxxy.commandblockrunner.CommandBlockRunner;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;

public class ModItems {
    public static final Item OUT_RUNNER = registerItems("out_runner", new Item(new Item.Settings()));
    private static Item registerItems(String id,Item item) {
/*
        return Registry.register(Registries.ITEM,RegistryKey.of(Registries.ITEM.getKey(), new Identifier(CommandBlockRunner.MOD_ID,id)), item);
        If there has bugs,you can change Identifier.of to /new Identifier()/
      For example:  return Registry.register(Registries.ITEM,new Identifier(CommandBlockRunner.MOD_ID,id), item);
*/
        return Registry.register(Registries.ITEM,Identifier.of(CommandBlockRunner.MOD_ID,id), item);
    }
    private static void addItemToIG(FabricItemGroupEntries fabricItemGroupEntries){
        fabricItemGroupEntries.add(OUT_RUNNER);
    }
    public static void registerModItems() {
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(ModItems::addItemToIG);
        CommandBlockRunner.LOGGER.info("Registering Items");
    }
}
