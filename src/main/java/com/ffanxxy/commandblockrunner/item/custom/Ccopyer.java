package com.ffanxxy.commandblockrunner.item.custom;

import com.ffanxxy.commandblockrunner.CommandBlockRunner;
import com.ffanxxy.commandblockrunner.tags.ModBlockTags;
import net.minecraft.block.BlockState;
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


        if (blockState.isIn(ModBlockTags.CBR_COMMAND_BLOCKS)) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof CommandBlockBlockEntity commandBlockBlockEntity) {
                if (commandBlockBlockEntity.hasWorld() && commandBlockBlockEntity.getWorld() == world) {


                    String command = commandBlockBlockEntity.getCommandExecutor().getCommand();
                    CommandBlockRunner.LOGGER.info("Copier_String:{}",command);

                    char fc = getFirstChar(command);
                    Text Text_copied = Text.translatable("item.ccopy.copied");
                    String copied = Text_copied.getString();

                    if (fc == '/') {
                        Text clickableText = Text.literal(copied + command)
                                .styled(style -> style
                                        .withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, Text.translatable
                                                ("item.ccopy.copy")))
                                        .withClickEvent(new ClickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD, command)));
                        world.playSound(player, pos, UI_CARTOGRAPHY_TABLE_TAKE_RESULT, SoundCategory.PLAYERS, 75.0F, 1.0F);
                        // 向玩家发送带有命令的可点击文本
                        if (player != null && MinecraftClient.getInstance().player != null) {
                            MinecraftClient.getInstance().player.sendMessage(clickableText, false);
                        }
                    } else if (fc == '!') {
                        if (player != null) {
                            player.sendMessage(Text.translatable("item.ccopy.error"), false);
                        } else CommandBlockRunner.LOGGER.warn("Can't find player!__Ccopyer.java.68");
                        return ActionResult.FAIL;
                    } else {
                        // 创建一个可点击的文本，当点击时，会在聊天输入框中输入命令
                        Text clickableText = Text.literal(copied + "/" + command)
                                .styled(style -> style
                                        .withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, Text.translatable
                                                ("item.ccopy.copy")))
                                        .withClickEvent(new ClickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD, "/" + command)));
                        world.playSound(player, pos, UI_CARTOGRAPHY_TABLE_TAKE_RESULT, SoundCategory.PLAYERS, 75.0F, 1.0F);
                        // 向玩家发送带有命令的可点击文本
                        if (player != null && MinecraftClient.getInstance().player != null) {
                            MinecraftClient.getInstance().player.sendMessage(clickableText, false);
                        }
                    }


                } else {
                    if (player != null) {
                        player.sendMessage(Text.translatable("item.ccopy.error_not_initialized"), false);
                    } else {
                        CommandBlockRunner.LOGGER.warn("Can't find player!__Ccopyer.java.92");
                    }
                }
        }
    }
        return ActionResult.SUCCESS;
    }

    public void appendTooltip(ItemStack itemStack, Item.TooltipContext context, List<Text> tooltip, TooltipType type) {
        tooltip.add(translatable("item.tooltip.ccopyer"));
    }
    public static char getFirstChar(String text) {

        // 检查字符串是否为空
        if (!text.isEmpty()) {
            char c;
            c = text.charAt(0);
            return c;
        } else {
            CommandBlockRunner.LOGGER.info("String-{} is empty", text);
            return '!';
        }
    }
}
