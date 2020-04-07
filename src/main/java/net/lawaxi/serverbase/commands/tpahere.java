package net.lawaxi.serverbase.commands;

import com.mojang.brigadier.CommandDispatcher;
import net.lawaxi.serverbase.shits.list;
import net.lawaxi.serverbase.shits.tparequest;
import net.minecraft.command.arguments.EntityArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;

public class tpahere {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher)
    {
        dispatcher.register(CommandManager.literal("tpahere")
                        .then(CommandManager.argument("玩家",EntityArgumentType.player())
                                .executes(ctx -> {
                                    ServerPlayerEntity to = EntityArgumentType.getPlayer(ctx,"玩家");
                                    ServerPlayerEntity me =ctx.getSource().getPlayer();
                                    if(to.equals(me))
                                    {
                                        to.sendMessage(new LiteralText("§c您不能请求自己传送"));
                                    }
                                    else
                                    {
                                        tparequest newrequest = new tparequest();
                                        newrequest.to=to;
                                        newrequest.me=me;
                                        newrequest.mode=true;

                                        if(list.tparequests.add(newrequest))
                                        {
                                            me.sendMessage(new LiteralText("§6已成功发送传送请求"));

                                            to.sendMessage(new LiteralText("§e"+me.getEntityName()+" §6请求你传送到他那里:"));
                                            to.sendMessage(new LiteralText("§6同意请输入/tpaccept"));
                                            to.sendMessage(new LiteralText("§6不同意请输入/tpadeny"));
                                        }
                                        else
                                        {
                                            me.sendMessage(new LiteralText("§c传送请求发送失败"));
                                        }
                                    }
                                    //-1 is failure, 0 is a pass and 1 is success.
                                    return 1;
                                }))
                        // execute if there are no arguments.
                        .executes(ctx -> {
                            ctx.getSource().getPlayer().sendMessage(new LiteralText("§c请输入要请求传送的玩家"));
                            return 1;
                        })
        );
    }
}