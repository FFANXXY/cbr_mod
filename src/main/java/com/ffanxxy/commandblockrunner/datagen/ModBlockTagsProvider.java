package com.ffanxxy.commandblockrunner.datagen;

import com.ffanxxy.commandblockrunner.tags.ModBlockTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;

import net.minecraft.block.Blocks;
import net.minecraft.registry.RegistryWrapper;


import java.util.concurrent.CompletableFuture;


public class ModBlockTagsProvider extends FabricTagProvider.BlockTagProvider {
    public ModBlockTagsProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
        getOrCreateTagBuilder(ModBlockTags.CBR_COMMAND_BLOCKS)
                .add(Blocks.COMMAND_BLOCK)
                .add(Blocks.CHAIN_COMMAND_BLOCK)
                .add(Blocks.REPEATING_COMMAND_BLOCK);
    }

}
