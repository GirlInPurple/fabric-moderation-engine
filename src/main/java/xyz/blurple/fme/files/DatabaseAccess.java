package xyz.blurple.fme.files;


import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import xyz.blurple.fme.files.DatabaseSchema.HistorySchema;
import xyz.blurple.fme.files.DatabaseSchema.HistorySchema.LogginIPs;
import xyz.blurple.fme.files.DatabaseSchema.OffenceSchema;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import static xyz.blurple.fme.FMEInit.PlayerDatabase;
import static xyz.blurple.fme.files.FileHandler.JSON.modifiedReadJSON;

public class DatabaseAccess {

    public static HashMap<UUID, DatabaseSchema> getPlayerDatabase() throws IOException {
        HashMap<UUID, DatabaseSchema> startupHashmap = new HashMap<>();
        JsonObject DatabaseFile = modifiedReadJSON(Path.of("./config/FME/fme-db.json"));

        for (String UUIDKey : DatabaseFile.keySet()) {
            JsonObject jsonObject = DatabaseFile.getAsJsonObject(UUIDKey);

            JsonArray warns = jsonObject.get("Warns").getAsJsonArray();
            JsonArray bans = jsonObject.get("Bans").getAsJsonArray();

            JsonObject history = jsonObject.get("History").getAsJsonObject();
            JsonArray logins = history.get("Logins").getAsJsonArray();
            JsonArray username = history.get("Username").getAsJsonArray();
            JsonArray antiCheatFlags = history.get("AntiCheatFlags").getAsJsonArray();

            List<OffenceSchema> warnList = GenerateOffences(warns);
            List<OffenceSchema> banList = GenerateOffences(bans);
            HistorySchema historicalData = new HistorySchema(
                GenerateIpHistory(logins),
                GenerateUsernames(username),
                GenerateOffences(antiCheatFlags),
                false
            );

            DatabaseSchema startupDatabase = new DatabaseSchema(
                warnList,
                banList,
                historicalData
            );
            startupHashmap.put(UUID.fromString(UUIDKey), startupDatabase);
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

    private static List<LogginIPs> GenerateIpHistory(JsonArray inputArray) {
        Gson gson = new Gson();
        Type type = new TypeToken<List<LogginIPs>>(){}.getType();
        return gson.fromJson(inputArray, type);
    }

    public static int WarnPlayer() {
        return 3;
    }
}
