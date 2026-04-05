package com.silvia.EndPlan.command.cilent;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.command.ICommandSender;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.WorldServer;

import gregtech.api.metatileentity.BaseMetaTileEntity;

public class set_allowopt {

    public static void process(ICommandSender sender) {
        MinecraftServer server = MinecraftServer.getServer();
        if (server == null) {
            sender.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "无法获取服务端实例。"));
            return;
        }

        int modifiedCount = 0;

        // 遍历服务端所有维度的世界 (主世界、下界、末地等)
        for (WorldServer world : server.worldServers) {
            if (world == null) continue;

            // 复制一份已加载的 TileEntity 列表，防止在修改过程中发生 ConcurrentModificationException
            List<TileEntity> loadedTiles = new ArrayList<TileEntity>(world.loadedTileEntityList);

            for (TileEntity te : loadedTiles) {
                // 判断是否是 GregTech 的机器
                if (te instanceof BaseMetaTileEntity) {

                    // 获取当前的 NBT 数据
                    NBTTagCompound nbt = new NBTTagCompound();
                    te.writeToNBT(nbt);

                    // 检查是否已经为 1，避免不必要的方块更新带来的性能消耗
                    // 注意：这里假设 allowopt 是 Integer。如果底层是 Byte，请改为 getByte 和 setByte
                    if (!nbt.hasKey("allowopt") || nbt.getInteger("allowopt") != 1) {

                        // 修改 NBT 标签
                        nbt.setInteger("allowopt", 1);
                        // 如果原模组存的是 Byte 型，请换成: nbt.setByte("allowopt", (byte) 1);

                        // 将修改后的 NBT 写回机器实例中
                        te.readFromNBT(nbt);

                        // 标记数据已脏，确保服务端在下一次保存时写入硬盘
                        te.markDirty();

                        // 触发方块更新，将 NBT 的变动同步给周围的客户端
                        world.markBlockForUpdate(te.xCoord, te.yCoord, te.zCoord);

                        modifiedCount++;
                    }
                }
            }
        }

        // 向指令发送者汇报结果
        sender.addChatMessage(
            new ChatComponentText(EnumChatFormatting.GREEN + "操作完成！已将 " + modifiedCount + " 个加载的机器的 allowopt 设置为 1。"));
    }
}
