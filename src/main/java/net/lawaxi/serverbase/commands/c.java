package net.lawaxi.serverbase.commands;

import com.mojang.brigadier.CommandDispatcher;
import net.lawaxi.serverbase.utils.LocationInfo;
import net.lawaxi.serverbase.utils.PseudoFreecam;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.world.GameMode;

public class c {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(CommandManager.literal("c")
                .executes(ctx -> {
                    ServerPlayerEntity player = ctx.getSource().getPlayer();
                    if (GameMode.byName(player.getGameProfile().getName()) == GameMode.SURVIVAL) {
                        LocationInfo locationinfo = new LocationInfo();
                        locationinfo.position = player.getBlockPos();
                        locationinfo.world = player.getServerWorld();
                        locationinfo.yaw = player.getHeadYaw();
                        locationinfo.pitch = player.getPitch(1);
                        PseudoFreecam.actualLocation.put(player.getGameProfile(), locationinfo);
                        player.setGameMode(GameMode.SPECTATOR);
                        player.sendMessage(new LiteralText("§6Freecam On"), false);
                        return 1;
                    } else {
                        player.sendMessage(new LiteralText("§cYou are not in survival mode!"), false);
                        return 0;
                    }
                }));
    }
}