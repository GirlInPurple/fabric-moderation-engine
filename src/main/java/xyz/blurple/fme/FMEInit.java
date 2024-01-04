package xyz.blurple.fme;

import com.google.gson.JsonObject;
import net.fabricmc.api.DedicatedServerModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.minecraft.entity.Entity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.blurple.fme.files.DatabaseSchema;
import xyz.blurple.fme.files.DatabaseSchema.HistorySchema;
import xyz.blurple.fme.files.DatabaseSchema.HistorySchema.LogginIPs;
import xyz.blurple.fme.files.DatabaseSchema.OffenceSchema;
import xyz.blurple.fme.files.ListedArea;

import java.io.IOException;
import java.nio.file.Path;
import java.util.*;

import static xyz.blurple.fme.commands.AdminCallCommand.registerAdminCall;
import static xyz.blurple.fme.commands.WarnCommand.registerWarn;
import static xyz.blurple.fme.files.DatabaseAccess.getPlayerDatabase;
import static xyz.blurple.fme.files.FileHandler.Config.CheckFiles;
import static xyz.blurple.fme.files.FileHandler.JSON.readJSON;
import static xyz.blurple.fme.files.ListedUtils.ParseJson;
public class FMEInit implements DedicatedServerModInitializer {
	public static final String MODID = "fme";
	public static final String VERSION = "1.0.0";
	private static final String LICENSE = "CC0-1.0";
    public static final Logger LOGGER = LoggerFactory.getLogger(MODID);
	private static final String UPDATEMODRINTH = "UPDATE CHECKER NOT IMPLEMENTED";
	private static final String SPLASHTEXT = "SPLASH TEXT NOT IMPLEMENTED";
	public static List<ListedArea> ListedAreaList;
	public static HashMap<UUID, DatabaseSchema> PlayerDatabase;
	public static JsonObject ModConfigs;

	@Override
	public void onInitializeServer() {

		LOGGER.info("  ______ __  __ ______   |  Fabric Moderation Engine V"+VERSION);
		LOGGER.info(" |  ____|  \\/  |  ____|  |  "+SPLASHTEXT);
		LOGGER.info(" | |__  | \\  / | |__     |  "+LICENSE+" Licensed");
		LOGGER.info(" |  __| | |\\/| |  __|    |  By FME Contributors");
		LOGGER.info(" | |    | |  | | |____   |  Thanks to the EssentialsX and LuckPerms Teams");
		LOGGER.info(" |_|    |_|  |_|______|  |  "+UPDATEMODRINTH);

		ModConfigs = readJSON(Path.of("./config/FME/fme-config.json"));
		LOGGER.info("Config Loaded");

		registerAdminCall();
		registerWarn();
		LOGGER.info("Commands Registered");

		CheckFiles();
		LOGGER.info("Files Checked");

		try {PlayerDatabase = getPlayerDatabase();}
		catch (Exception e) {throw new RuntimeException(e);}
		try {ListedAreaList = ParseJson(Path.of("./config/FME/fme-areas.json"));}
		catch (IOException e) {throw new RuntimeException(e);}
		LOGGER.info("Database Loaded");

		ServerEntityEvents.ENTITY_LOAD.register(this::onPlayerJoin);
		LOGGER.info("Event Trackers Started");

		LOGGER.info("FME has finished loading!");
	}

	private void onPlayerJoin(Entity entity, ServerWorld serverWorld) {
		if (!(entity instanceof ServerPlayerEntity)) {return;}
		if (PlayerDatabase.containsKey(entity.getUuid())) {
			// Past this point you should assume it's a returning player.
			LOGGER.info(entity.getEntityName() + " is a returning player. Refreshing database...");

			List<OffenceSchema> Warns = PlayerDatabase.get(entity.getUuid()).getWarns();
			List<OffenceSchema> Bans = PlayerDatabase.get(entity.getUuid()).getBans();

			List<LogginIPs> LoginList = PlayerDatabase.get(entity.getUuid()).getHistory().getLogins();
			if (!PlayerDatabase.get(entity.getUuid()).getHistory().doesLogginsContain(((ServerPlayerEntity) entity).getIp())) {LoginList.add(new LogginIPs(((ServerPlayerEntity) entity).getIp(), UnixTimestamp()));}

			List<String> UsernameList = PlayerDatabase.get(entity.getUuid()).getHistory().getUsername();
			if (!PlayerDatabase.get(entity.getUuid()).getHistory().doesUsernamesContain(entity.getEntityName())) {UsernameList.add(entity.getEntityName());}

			List<OffenceSchema> AntiCheatFlags = PlayerDatabase.get(entity.getUuid()).getHistory().getAntiCheatFlags();

			PlayerDatabase.remove(entity.getUuid());
			PlayerDatabase.put(
				entity.getUuid(),
				new DatabaseSchema(
					Warns,
					Bans,
					new HistorySchema(
						LoginList,
						UsernameList,
						AntiCheatFlags,
						true // Considering the player just joined, set it true
					)
				)
			);

		} else {
			// Past this point you should assume it's a new player.
			LOGGER.info(entity.getEntityName() + " is a new player. Adding to database...");

			PlayerDatabase.put(
				entity.getUuid(),
				new DatabaseSchema(
					new ArrayList<>(),
					new ArrayList<>(),
					new HistorySchema(
						new ArrayList<>(
							List.of(
								new LogginIPs(
									((ServerPlayerEntity) entity).getIp(),
									UnixTimestamp()
								)
							)
						),
						new ArrayList<>(
							List.of(entity.getName().getString())
						),
						new ArrayList<>(),
						true // Considering the player just joined, set it true
					)
				)
			);
		}
	}

	public static long UnixTimestamp() {
		Date date = new Date();
		return date.getTime() / 1000L;
	}

	public static String stylizedInt(int input) {
		String string = Integer.toString(input);
		char lastChar = string.charAt(string.length() - 1);

		return switch (lastChar) {
			case '1' -> string + (string.endsWith("11") ? "th" : "st");
			case '2' -> string + (string.endsWith("12") ? "th" : "nd");
			case '3' -> string + (string.endsWith("13") ? "th" : "rd");
			default -> string + "th";
		};
	}

}