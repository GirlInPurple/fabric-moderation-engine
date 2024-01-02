package xyz.blurple.fme;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.world.World;

import static xyz.blurple.fme.FMEInit.stylizedInt;
import static xyz.blurple.fme.compat.LuckpermsApi.isAdmin;

public class CommandRegister {
    public static void initCommands() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, dedicated) -> {
            dispatcher.register(CommandManager.literal("warn")
                .then(CommandManager.argument("player", EntityArgumentType.player())
                    .then(CommandManager.argument("reason", StringArgumentType.string())
                        .executes(CommandMethods::warn)
                    )
                    .executes(CommandMethods::warn)
                )
            );
            dispatcher.register(CommandManager.literal("admincall")
                .then(CommandManager.argument("reason", StringArgumentType.greedyString())
                    .executes(CommandMethods::admincall)
                )
            );
        });
    }

    public static class CommandMethods {
        public static int warn(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
            String sending;
            String reason;
            ServerPlayerEntity player = EntityArgumentType.getPlayer(context, "player");
            try {reason = StringArgumentType.getString(context, "reason");}
            catch (Exception ignored) {reason = "No Reason Given";}
            try {
                String AmountOfWarns = stylizedInt(1);
                sending = "Warned " + player.getEntityName() + " for reason: \"" + reason + "\". This is their "+AmountOfWarns+" warning.";
                context.getSource().sendMessage(Text.literal(sending));
                FMEInit.LOGGER.info(sending);
                return 1;
            } catch (Exception e) {
                sending = "Error while trying to warn"+player.getEntityName()+": "+e.getMessage();
                context.getSource().sendMessage(Text.literal(sending));
                FMEInit.LOGGER.info(sending);
                e.printStackTrace();
                return 0;
            }
        }

        public static int admincall(CommandContext<ServerCommandSource> context) {
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
        }
    }
}
