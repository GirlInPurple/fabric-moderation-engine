package xyz.blurple.fme;

import com.google.gson.JsonObject;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static xyz.blurple.fme.Commands.initCommands;
import static xyz.blurple.fme.FileHandler.startupFiles;
import static xyz.blurple.fme.FileHandler.JSON.*;

public class FMEInit implements ModInitializer {
	public static final String MODID = "fme";
	public static final String VERSION = "1.0.0";
	private static final String LICENSE = "CC0-1.0";
    public static final Logger LOGGER = LoggerFactory.getLogger(MODID);
	private static final String UPDATEMODRINTH = "UPDATE CHECKER NOT IMPLEMENTED";
	public static List<ListedAreaInstance> ListedAreaList = new ArrayList<>();
	public static JsonObject playerDatabase = readJson(Path.of("./config/FME/fme-db.json"));

	@Override
	public void onInitialize() {

		initCommands();
		startupFiles();

		LOGGER.info("  ______ __  __ ______    | FME has completed loading!");
		LOGGER.info(" |  ____|  \\/  |  ____|   | Fabric Moderation Engine V"+VERSION);
		LOGGER.info(" | |__  | \\  / | |__      | "+LICENSE+" Licensed");
		LOGGER.info(" |  __| | |\\/| |  __|     | By FME Contributors");
		LOGGER.info(" | |    | |  | | |____    | Thanks to the EssentialsX Team");
		LOGGER.info(" |_|    |_|  |_|______|   | "+UPDATEMODRINTH);
	}
}