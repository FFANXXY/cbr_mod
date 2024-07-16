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
import net.minecraft.text.ClickEvent;
import net.minecraft.text.HoverEvent;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Rarity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.*;

import static net.minecraft.sound.SoundEvents.BLOCK_ENCHANTMENT_TABLE_USE;
import static net.minecraft.sound.SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP;

public class Crunner extends Item {
    public Crunner(Settings settings) {
        super(settings.rarity(Rarity.EPIC));
    }

    int useFrequency = 0;
    int repFre = 0;
    int canFre = 0;

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        PlayerEntity player = context.getPlayer();
        BlockPos pos = context.getBlockPos();
        World world = context.getWorld();
        BlockState blockState = world.getBlockState(pos);
        Block block = blockState.getBlock();
        BlockEntity blockEntity = world.getBlockEntity(pos);
        CommandBlockBlockEntity commandBlockBlockEntity = (CommandBlockBlockEntity) blockEntity;
        if (blockState.isOf(Blocks.COMMAND_BLOCK)) {
            if (commandBlockBlockEntity != null) {
                String command = commandBlockBlockEntity.getCommandExecutor().getCommand();
                CommandBlockRunner.LOGGER.info("{}Command:", command);

//            输出文本
                String runCommand = "/give @s command_block" +
                        "[block_entity_data={Command:\"" +
                        command + "\",id:\"minecraft:command_block\"}]";

                Text translatable = Text.translatable("cbr.ran")
                        .styled(style -> style
                                .withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                                        Text.literal("复制命令方块")))
                                .withClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, runCommand)));
                Text command_text = Text.literal(command).styled(style -> style
                        .withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                                Text.literal("复制命令方块")))
                        .withClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, runCommand)));
                String command_text_string = command_text.getString();
                world.playSound(player, pos, ENTITY_EXPERIENCE_ORB_PICKUP, SoundCategory.PLAYERS, 75.0F, 1.0F);
                //            检测命令是否为空
                if (player != null && !command_text_string.isEmpty()) {
                    //            播放音效
                    player.sendMessage(translatable);
                    player.sendMessage(command_text);
                    if (useFrequency == 0) {
                        //          运行命令方块
                        commandBlockBlockEntity.setAuto(true);
//                               world.updateNeighbors(pos,block); //有问题的话可以删去
                        commandBlockBlockEntity.setAuto(false);
                        useFrequency++;
                        return ActionResult.SUCCESS;
                    } else if (useFrequency == 1) {
                        useFrequency = 0;
                        return ActionResult.PASS;
                    }
                } else if (command_text_string.isEmpty()) {
                    if (player != null) {
                        player.sendMessage(Text.translatable("cbr.ran.empty"));
                    } else {
                        CommandBlockRunner.LOGGER.warn("Player is null,Please run on Minecraft Client!");
                    }
                }
            }

//
//            重复命令方块
//
        } else if (blockState.isOf(Blocks.REPEATING_COMMAND_BLOCK)) {

            String pos_String = pos.toString();
            CommandBlockRunner.LOGGER.info("String_posShow.debug:{}.pos,{}.(String)Pos", pos, pos_String);

            world.playSound(player, pos, BLOCK_ENCHANTMENT_TABLE_USE, SoundCategory.PLAYERS, 75.0F, 1.0F);
            if (commandBlockBlockEntity == null) throw new AssertionError();
            boolean isAuto = commandBlockBlockEntity.isAuto();
            if (!isAuto) {
                if (player == null) throw new AssertionError();
                if (repFre == 0 ){
                player.sendMessage(Text.translatable("cbr.ran.repeatrun")
                        .styled(style -> style
                                .withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, Text.literal(pos_String))
                                )
                        ));repFre++;}else {repFre=0;}
                commandBlockBlockEntity.setAuto(true);
                return ActionResult.SUCCESS;
            } else {
                commandBlockBlockEntity.setAuto(false);
                if (player == null) throw new AssertionError();
                if (repFre == 0 ){
                    player.sendMessage(Text.translatable("cbr.ran.repeatstop")
                            .styled(style -> style
                                    .withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, Text.literal(pos_String))
                                    )
                            ));repFre++;}else {repFre=0;}
                return ActionResult.SUCCESS;
            }
        } else if (blockState.isOf(Blocks.CHAIN_COMMAND_BLOCK)) {

//            连锁命令方块

                if (commandBlockBlockEntity != null) {
                    if (player != null) {
                        if (canFre == 0) {
                        player.sendMessage(Text.translatable("cbr.ran.chainrun"));canFre++;}else{canFre=0;}
                    }
                    commandBlockBlockEntity.setAuto(true);
                        world.updateNeighbors(pos, block);

                } else {return ActionResult.FAIL;}

        } else {
            return ActionResult.PASS;
        }

        return ActionResult.FAIL;
    }
//    Over

    public void appendTooltip(ItemStack itemStack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        tooltip.add(Text.translatable("item.tooltip.crunner"));
    }

}
