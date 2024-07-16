package com.ffanxxy.commandblockrunner.tags;

import com.ffanxxy.commandblockrunner.CommandBlockRunner;
import net.minecraft.block.Block;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class ModBlockTags {

    public static final TagKey<Block> CBR_COMMAND_BLOCKS = of("cbr_command_blocks");

    private static TagKey<Block> of(String id) {
        return TagKey.of(RegistryKeys.BLOCK, new Identifier(CommandBlockRunner.MOD_ID, id));
    }
    public static void registerModBlockTags() {
        CommandBlockRunner.LOGGER.info("Registering Block Tags");
    }
}
