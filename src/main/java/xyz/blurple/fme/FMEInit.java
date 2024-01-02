package xyz.blurple.fme;

import com.google.gson.JsonObject;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.blurple.fme.areas.ListedArea;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Date;
import java.util.List;

import static xyz.blurple.fme.CommandRegister.initCommands;
import static xyz.blurple.fme.areas.ListedUtils.ParseJson;
import static xyz.blurple.fme.files.FileHandler.Config.CheckFiles;
import static xyz.blurple.fme.files.FileHandler.JSON.readJSON;
public class FMEInit implements ModInitializer {
	public static final String MODID = "fme";
	public static final String VERSION = "1.0.0";
	private static final String LICENSE = "CC0-1.0";
    public static final Logger LOGGER = LoggerFactory.getLogger(MODID);
	private static final String UPDATEMODRINTH = "UPDATE CHECKER NOT IMPLEMENTED";
	private static final String SPLASHTEXT = "SPLASH TEXT NOT IMPLEMENTED";
	public static List<ListedArea> ListedAreaList;
	public static JsonObject PlayerDatabase;
	public static JsonObject ModConfigs;

	@Override
	public void onInitialize() {

		initCommands();
		LOGGER.info("CommandRegister Registered");

		CheckFiles();
		LOGGER.info("Files Registered");

		PlayerDatabase = readJSON(Path.of("./config/FME/fme-db.json"));
		ModConfigs = readJSON(Path.of("./config/FME/fme-config.json"));
		try {ListedAreaList = ParseJson(Path.of("./config/FME/fme-areas.json"));}
		catch (IOException e) {throw new RuntimeException(e);}
		LOGGER.info("Database and Configs Registered");

		LOGGER.info("  ______ __  __ ______    | Fabric Moderation Engine V"+VERSION);
		LOGGER.info(" |  ____|  \\/  |  ____|   |"+SPLASHTEXT);
		LOGGER.info(" | |__  | \\  / | |__      | "+LICENSE+" Licensed");
		LOGGER.info(" |  __| | |\\/| |  __|     | By FME Contributors");
		LOGGER.info(" | |    | |  | | |____    | Thanks to the EssentialsX Team");
		LOGGER.info(" |_|    |_|  |_|______|   | "+UPDATEMODRINTH);
	}

	public static long UnixTimestamp() {
		Date date = new Date();
		return date.getTime() / 1000L;
	}
}