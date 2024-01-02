package xyz.blurple.fme.files;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.entity.TntEntity;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.entity.vehicle.TntMinecartEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static xyz.blurple.fme.FMEInit.ListedAreaList;

public class ListedUtils {

    public static boolean isInsideArea(Object self, World world) {
        if (!(self instanceof TntEntity) && !(self instanceof TntMinecartEntity) && !(self instanceof EndCrystalEntity)) {return false;}
        for (xyz.blurple.fme.files.ListedArea ListedArea : ListedAreaList) {
            Box EntitySearchBox = new Box(ListedArea.getListingPosition()).expand(ListedArea.getListingRadius());
            if (world.getEntitiesByClass(TntEntity.class, EntitySearchBox, tntEntity -> true).contains(self)) {return true;}
            if (world.getEntitiesByClass(TntMinecartEntity.class, EntitySearchBox, tntMinecartEntity -> true).contains(self)) {return true;}
            if (world.getEntitiesByClass(EndCrystalEntity.class, EntitySearchBox, endCrystalEntity -> true).contains(self)) {return true;}
        }
        return false;
    }

    public static List<ListedArea> ParseJson(Path filepath) throws IOException {
        List<ListedArea> ParserListedArea = new ArrayList<>();
        try (FileReader reader = new FileReader(filepath.toFile())) {
            Gson gson = new Gson();
            JsonArray JArray = gson.fromJson(reader, JsonArray.class);
            for (JsonElement element : JArray) {
                JsonObject JObject = element.getAsJsonObject();
                JsonObject AreaConfigs = JObject.get("configs").getAsJsonObject();
                ParserListedArea.add(
                    new ListedArea(
                        JObject.get("name").getAsString(),
                        new BlockPos(JObject.get("x").getAsInt(), 0, JObject.get("z").getAsInt()),
                        JObject.get("radius").getAsInt(),
                        ParseAreaConfigs(AreaConfigs)
                    )
                );
            }
        }
        return ParserListedArea;
    }

    private static ListedArea.ListingConfigs ParseAreaConfigs(JsonObject AreaConfigs) {
        boolean logActions;
        boolean explosivesExplode;
        boolean liquidsInteractions;
        int playersInteractions;
        boolean mobInteractions;
        try {logActions = AreaConfigs.get("logActions").getAsBoolean();} catch (Exception ignored) {logActions = true;}
        try {explosivesExplode = AreaConfigs.get("explosivesExplode").getAsBoolean();} catch (Exception ignored) {explosivesExplode = true;}
        try {liquidsInteractions = AreaConfigs.get("liquidsInteractions").getAsBoolean();} catch (Exception ignored) {liquidsInteractions = true;}
        try {playersInteractions = AreaConfigs.get("playersInteractions").getAsInt();} catch (Exception ignored) {playersInteractions = 0;}
        try {mobInteractions = AreaConfigs.get("mobInteractions").getAsBoolean();} catch (Exception ignored) {mobInteractions = true;}
        return new ListedArea.ListingConfigs(logActions, explosivesExplode, liquidsInteractions, playersInteractions, mobInteractions);
    }
}
