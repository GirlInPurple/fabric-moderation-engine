package xyz.blurple.fme.files;


import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import xyz.blurple.fme.files.DatabaseSchema.HistorySchema;
import xyz.blurple.fme.files.DatabaseSchema.HistorySchema.LogginIPs;
import xyz.blurple.fme.files.DatabaseSchema.OffenceSchema;

import java.io.IOException;
import java.net.UnknownHostException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import static java.net.InetAddress.getByName;
import static xyz.blurple.fme.FMEInit.PlayerDatabase;
import static xyz.blurple.fme.files.FileHandler.JSON.modifiedReadJSON;

public class DatabaseAccess {

    public static HashMap<UUID, DatabaseSchema> getPlayerDatabase() throws IOException {
        HashMap<UUID, DatabaseSchema> startupHashmap = new HashMap<>();
        JsonArray DatabaseArray = modifiedReadJSON(Path.of("./config/FME/fme-db.json")).getAsJsonArray();

        for (JsonElement jsonElement : DatabaseArray) {
            JsonObject jsonObject = jsonElement.getAsJsonObject();

            UUID uuid = UUID.fromString(jsonObject.get("UUID").getAsString());
            JsonArray warns = jsonObject.get("warns").getAsJsonArray();
            JsonArray bans = jsonObject.get("bans").getAsJsonArray();
            JsonObject history = jsonObject.get("history").getAsJsonObject();
            JsonArray logins = history.get("Logins").getAsJsonArray();
            JsonArray username = history.get("Username").getAsJsonArray();
            JsonArray antiCheatFlags = history.get("AntiCheatFlags").getAsJsonArray();

            List<OffenceSchema> warnList = GenerateOffences(warns);
            List<OffenceSchema> banList = GenerateOffences(bans);
            HistorySchema historicalData = new HistorySchema(
                    GenerateIpHistory(logins),
                    GenerateUsernames(username),
                    GenerateOffences(antiCheatFlags)
            );

            DatabaseSchema startupDatabase = new DatabaseSchema(
                    warnList,
                    banList,
                    historicalData
            );
            startupHashmap.put(uuid, startupDatabase);
        }
        return startupHashmap;
    }

    public static JsonObject putPlayerDatabase() {
        Gson gson = new Gson();
        return gson.fromJson(gson.toJson(PlayerDatabase), JsonObject.class);
    }

    private static List<OffenceSchema> GenerateOffences(JsonArray inputArray) {
        List<OffenceSchema> outputList = new ArrayList<>();
        for (JsonElement inputList : inputArray) {
            UUID caller = UUID.fromString(inputList.getAsJsonArray().get(0).getAsString());
            String reason = inputList.getAsJsonArray().get(1).getAsString();
            long duration = inputList.getAsJsonArray().get(2).getAsLong();
            long timestamp = inputList.getAsJsonArray().get(3).getAsLong();
            boolean resolved = inputList.getAsJsonArray().get(4).getAsBoolean();
            outputList.add(new OffenceSchema(caller, reason, duration, timestamp, resolved));
        }
        return outputList;
    }

    private static List<String> GenerateUsernames(JsonArray inputArray) {
        List<String> outputList = new ArrayList<>();
        for (JsonElement inputList : inputArray) {
            outputList.add(inputList.getAsString());
        }
        return outputList;
    }

    private static List<LogginIPs> GenerateIpHistory(JsonArray inputArray) throws UnknownHostException {
        List<LogginIPs> outputList = new ArrayList<>();
        for (JsonElement inputList : inputArray) {
            outputList.add(new LogginIPs(getByName(inputList.getAsJsonArray().get(0).getAsString()), inputList.getAsJsonArray().get(1).getAsLong()));
        }
        return outputList;
    }

    public static int WarnPlayer() {
        return 3;
    }
}
