package com.ffanxxy.commandblockrunner.item.custom;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.CommandBlockBlockEntity;
import net.minecraft.client.MinecraftClient;
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


import java.util.List;

import static net.minecraft.sound.SoundEvents.UI_CARTOGRAPHY_TABLE_TAKE_RESULT;
import static net.minecraft.text.Text.translatable;

public class Ccopyer extends Item {
    public Ccopyer(Settings settings) {
        super(settings.rarity(Rarity.EPIC));
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        PlayerEntity player = context.getPlayer();
        BlockPos pos = context.getBlockPos();
        World world = context.getWorld();

        if (!world.isClient) {
            return ActionResult.PASS; // 如果在服务器上，则跳过
        }

        BlockState blockState = world.getBlockState(pos);
        Block block = blockState.getBlock();

        if (block.equals(Blocks.COMMAND_BLOCK)) {
            world.updateNeighbors(pos,block);
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof CommandBlockBlockEntity commandBlockBlockEntity) {
                String command = commandBlockBlockEntity.getCommandExecutor().getCommand();

                // 创建一个可点击的文本，当点击时，会在聊天输入框中输入命令
                Text clickableText = Text.literal( "COPY::\n" + "/" + command)
                        .styled(style -> style
                                        .withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, Text.translatable
                                                ("item.ccopy.copy")))
                                        .withClickEvent(new ClickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD, "/" + command)));
                world.playSound(player, pos,UI_CARTOGRAPHY_TABLE_TAKE_RESULT,SoundCategory.PLAYERS, 75.0F,1.0F);
                // 向玩家发送带有命令的可点击文本
                if (player != null && MinecraftClient.getInstance().player != null) {
                    MinecraftClient.getInstance().player.sendMessage(clickableText,false);
                }

            }
        }

        return ActionResult.SUCCESS;
    }

    public void appendTooltip(ItemStack itemStack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        tooltip.add(translatable("item.tooltip.ccopyer"));
    }
}
