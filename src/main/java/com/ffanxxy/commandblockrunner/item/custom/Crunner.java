package com.ffanxxy.commandblockrunner.item.custom;

import com.ffanxxy.commandblockrunner.CommandBlockRunner;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.CommandBlockBlockEntity;
import net.minecraft.client.item.TooltipType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Rarity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.*;

import static net.minecraft.sound.SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP;

public class Crunner extends Item {
    public Crunner(Settings settings) {
        super(settings.rarity(Rarity.EPIC));
    }
    int useFrequency = 0;

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        PlayerEntity player = context.getPlayer();
        BlockPos pos = context.getBlockPos();
        World world = context.getWorld();
        BlockState blockState = world.getBlockState(pos);
        Block block = blockState.getBlock();
        if (block.equals(Blocks.COMMAND_BLOCK)) {
//            播放音效
            world.playSound(player, pos,ENTITY_EXPERIENCE_ORB_PICKUP,SoundCategory.PLAYERS, 75.0F,1.0F);

            BlockEntity blockEntity = world.getBlockEntity(pos);
            CommandBlockBlockEntity commandBlockBlockEntity = (CommandBlockBlockEntity) blockEntity;
            if (commandBlockBlockEntity != null) {
                String command = commandBlockBlockEntity.getCommandExecutor().getCommand();
                CommandBlockRunner.LOGGER.info("{}Command:", command);

//            输出文本
                Text translatable =Text.translatable("cbr.ran");
                Text command_text = Text.literal(command);
                String command_text_string = command_text.getString();
//            检测命令是否为空
                if (player != null && !command_text_string.isEmpty()) {
                    useFrequency++;
                           if (useFrequency == 1) {
                               player.sendMessage(translatable);
                               player.sendMessage(command_text);
                               //          运行命令方块
                               commandBlockBlockEntity.setAuto(true);
                               world.updateNeighbors(pos,block); //有问题的话可以删去
                               commandBlockBlockEntity.setAuto(false);
                           } else if (useFrequency == 2) {
                               useFrequency=0;
                           }
                }



            } else {
                return  ActionResult.PASS;
            }
            }

        return ActionResult.PASS;
    }

    public void appendTooltip(ItemStack itemStack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        tooltip.add(Text.translatable("item.tooltip.crunner"));
    }


}
