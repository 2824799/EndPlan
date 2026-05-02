package com.silvia.EndPlan.command;

import java.util.List;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

import com.silvia.EndPlan.command.cilent.check_gt_recipe_amount;
import com.silvia.EndPlan.command.cilent.set_machine_property;

public class endplan_cilent extends CommandBase {

    @Override
    public String getCommandName() {
        return "endplan";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/endplan <check_gt_recipe_amount | allowopt set <数值> | useNewGTPatternCache set <数值>>";
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 0; // 0=所有人可用。如果涉及修改服务端数据，正式发布时建议改为 2 或 4 (仅OP)
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        if (args.length == 0) {
            sender.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "用法: " + getCommandUsage(sender)));
            return;
        }

        String subCommand = args[0];

        if ("check_gt_recipe_amount".equals(subCommand)) {
            check_gt_recipe_amount.p(sender);
        } else if ("allowopt".equals(subCommand) || "useNewGTPatternCache".equals(subCommand)) {
            if (args.length >= 3 && "set".equals(args[1])) {
                try {
                    int value = Integer.parseInt(args[2]);
                    set_machine_property.process(sender, subCommand, value);
                } catch (NumberFormatException e) {
                    sender.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "数值无效: " + args[2]));
                }
            } else {
                sender.addChatMessage(
                    new ChatComponentText(EnumChatFormatting.RED + "用法: /endplan " + subCommand + " set <数值>"));
            }
        } else {
            sender.addChatMessage(new ChatComponentText(EnumChatFormatting.YELLOW + "未知指令"));
        }
    }

    /**
     * 支持 Tab 键自动补全
     */
    @Override
    public List addTabCompletionOptions(ICommandSender sender, String[] args) {
        if (args.length == 1) {
            return getListOfStringsMatchingLastWord(args, "check_gt_recipe_amount", "allowopt", "useNewGTPatternCache");
        } else if (args.length == 2) {
            if ("allowopt".equals(args[0]) || "useNewGTPatternCache".equals(args[0])) {
                return getListOfStringsMatchingLastWord(args, "set");
            }
        }
        return null;
    }
}
