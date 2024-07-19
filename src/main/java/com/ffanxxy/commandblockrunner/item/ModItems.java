package com.ffanxxy.commandblockrunner.item;

import com.ffanxxy.commandblockrunner.CommandBlockRunner;
import com.ffanxxy.commandblockrunner.item.custom.Ccopyer;
import com.ffanxxy.commandblockrunner.item.custom.Crunner;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModItems {
    public static final Item OUT_RUNNER = registerItems("out_runner", new Item(new Item.Settings()));
    public static final Item CRUNNER = registerItems("crunner", new Crunner(new Item.Settings().maxCount(1)));
    public static final Item CCOPYER = registerItems("ccopyer", new Ccopyer(new Item.Settings().maxCount(1)));



    private static Item registerItems(String id,Item item) {
        //版本改动须知:
        /*
        return Registry.register(Registries.ITEM,RegistryKey.of(Registries.ITEM.getKey(), new Identifier(CommandBlockRunner.MOD_ID,id)), item);
        If there has bugs,you can change Identifier.of to /new Identifier()/
      For example:  return Registry.register(Registries.ITEM,new Identifier(CommandBlockRunner.MOD_ID,id), item);
          [The English version is not complete for the time being, please take a look at the Chinese version!!!]
      ====================
      如果有bug，注册的方法记得改成For example:  return Registry.register(Registries.ITEM,new Identifier(CommandBlockRunner.MOD_ID,id), item);
        如果是高版本，如1.21，则不用看此注释，如果是1.20.5以下版本，记得改为new Identifier()
*/
        return Registry.register(Registries.ITEM,Identifier.of(CommandBlockRunner.MOD_ID,id), item);
    }
    private static void addItemToIG(FabricItemGroupEntries fabricItemGroupEntries){
        fabricItemGroupEntries.add(CRUNNER);
        fabricItemGroupEntries.add(CCOPYER);
    }
    public static void registerModItems() {
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(ModItems::addItemToIG);
        CommandBlockRunner.LOGGER.info("Registering Items");
    }

}
