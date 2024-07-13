package com.ffanxxy.commandblockrunner.item;

import com.ffanxxy.commandblockrunner.CommandBlockRunner;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModItemGroup {
/*
    private static RegistryKey<ItemGroup> register(String id) {

        return RegistryKey.of(RegistryKeys.ITEM_GROUP, new Identifier(CommandBlockRunner.MOD_ID,id));
    }
    public static final RegistryKey<ItemGroup> CBR_GROUP = register("CBR");
    public static void registerModItemGroup() {
        Registry.register(Registries.ITEM_GROUP,CBR_GROUP,
                ItemGroup.create(ItemGroup.Row.TOP,7)
                        .displayName(Text.translatable("itemGroup.cbr")).icon(() -> new ItemStack(ModItems.OUT_RUNNER)).entries((displayContext, entries) -> {
                            entries.add(ModItems.OUT_RUNNER);
                        }).build());
        CommandBlockRunner.LOGGER.info("Registering Item Groups");
    }
*/
    public static final ItemGroup CBR_GROUP = Registry.register(Registries.ITEM_GROUP,Identifier.of(CommandBlockRunner.MOD_ID,"cbr_group"),
        ItemGroup.create(null,-1).displayName(Text.translatable("itemGroup.cbr"))
                .icon(() -> new ItemStack(ModItems.OUT_RUNNER))
                .entries((displayContext, entries) -> {
                    entries.add(ModItems.OUT_RUNNER);
                    entries.add(Blocks.COMMAND_BLOCK);
                })
               .build());
    public static void registerModItemGroup() {
        CommandBlockRunner.LOGGER.info("Registering Item Groups");
    }
}
