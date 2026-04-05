package com.silvia.EndPlan.command;

import java.util.List;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

import com.silvia.EndPlan.command.cilent.check_gt_recipe_amount;
import com.silvia.EndPlan.command.cilent.set_allowopt;

public class endplan_cilent extends CommandBase {

    @Override
    public String getCommandName() {
        return "endplan";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/endplan <check_gt_recipe_amount | set_allowopt>";
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

        switch (subCommand) {
            case "check_gt_recipe_amount":
                check_gt_recipe_amount.p(sender);
                break;
            case "set_allowopt":
                set_allowopt.process(sender);
                break;
            default:
                sender.addChatMessage(new ChatComponentText(EnumChatFormatting.YELLOW + "未知指令"));
                break;
        }
    }

    /**
     * 支持 Tab 键自动补全
     */
    @Override
    public List addTabCompletionOptions(ICommandSender sender, String[] args) {
        // 如果用户正在输入第一个参数 (二级指令)
        if (args.length == 1) {
            return getListOfStringsMatchingLastWord(args, "check_gt_recipe_amount", "set_allowopt");
        }
        return null;
    }
}
