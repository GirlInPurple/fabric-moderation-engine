package xyz.blurple.fme;

import com.mojang.brigadier.arguments.StringArgumentType;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.world.World;

import static xyz.blurple.fme.compat.LuckpermsApi.isAdmin;
import static xyz.blurple.fme.events.WarnBanMethods.warnPlayer;

public class CommandRegister {
    public static void initCommands() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, dedicated) -> {
            dispatcher.register(CommandManager.literal("warn")
                .then(CommandManager.argument("player", EntityArgumentType.player())
                    .then(CommandManager.argument("reason", StringArgumentType.greedyString())
                        .executes(context -> {
                            ServerPlayerEntity player = EntityArgumentType.getPlayer(context, "player");
                            String reason = StringArgumentType.getString(context, "reason");
                            String sending;
                            try {
                                int AmountOfWarns = warnPlayer();
                                sending = "Warned " + player.getEntityName() + " for reason: \"" + reason + "\". This is their "+AmountOfWarns;
                                context.getSource().sendMessage(Text.literal(sending));
                                FMEInit.LOGGER.info(sending);
                                return 1;
                            } catch (Exception e) {
                                sending = "Error while trying to warn"+player+": "+e.getMessage();
                                context.getSource().sendMessage(Text.literal(sending));
                                FMEInit.LOGGER.info(sending);
                                e.printStackTrace();
                                return 1;
                            }
                        })
                    )
                )
            );
            dispatcher.register(CommandManager.literal("admincall")
                .then(CommandManager.argument("reason", StringArgumentType.greedyString())
                    .executes(context -> {
                        String reason = StringArgumentType.getString(context, "reason");
                        if (context.getSource().getPlayer() == null) {return 0;}
                        PlayerEntity caller = context.getSource().getPlayer();
                        World world = context.getSource().getWorld();
                        String Sending = "Player "+caller.getEntityName()+" needs your help at X "+caller.getPos().x+" Y "+caller.getPos().y+" Z "+caller.getPos().z+" for the following reason: \""+reason+"\"";

                        for (PlayerEntity player : world.getPlayers()) {
                            isAdmin(player.getUuid()).thenAcceptAsync(result -> {
                                if (result) {player.sendMessage(Text.literal(Sending));}
                            });
                        }
                        FMEInit.LOGGER.info(Sending);
                        return 1;
                    })
                )
            );
        });
    }
}
