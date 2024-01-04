package xyz.blurple.fme.commands;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.command.CommandSource;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import xyz.blurple.fme.FMEInit;

import java.util.Arrays;
import java.util.List;

import static xyz.blurple.fme.FMEInit.stylizedInt;

public class WarnCommand {

    private static final List<String> suggestions = Arrays.asList("list","add","remove");

    public static void registerWarn() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, dedicated) ->
            dispatcher.register(CommandManager.literal("warn")
                .then(CommandManager.argument("subcommand", StringArgumentType.string())
                    .suggests((context, builder) -> CommandSource.suggestMatching(suggestions, builder))
                    .then(CommandManager.argument("player", EntityArgumentType.player())
                        .then(CommandManager.argument("reason", StringArgumentType.string())
                                .executes(WarnCommand::warn)
                        )
                        .executes(WarnCommand::warn)
                    )
                )
            )
        );
    }

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
}
