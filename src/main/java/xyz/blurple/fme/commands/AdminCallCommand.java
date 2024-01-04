package xyz.blurple.fme.commands;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import xyz.blurple.fme.FMEInit;

import static xyz.blurple.fme.compat.LuckpermsApi.isAdmin;

public class AdminCallCommand {
    public static void registerAdminCall() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, dedicated) -> {
            dispatcher.register(CommandManager.literal("admincall")
                    .then(CommandManager.argument("reason", StringArgumentType.greedyString())
                            .executes(AdminCallCommand::admincall)
                    )
            );
        });
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
