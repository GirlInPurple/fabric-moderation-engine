package xyz.blurple.fme.mixin;

import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xyz.blurple.fme.files.FileHandler.JSON;

import java.nio.file.Path;

import static xyz.blurple.fme.FMEInit.LOGGER;
import static xyz.blurple.fme.FMEInit.ModConfigs;
import static xyz.blurple.fme.files.DatabaseAccess.putPlayerDatabase;

@Mixin(MinecraftServer.class)
public class MinecraftServerMixin {
    /**
     * On world save, the .json file will be saved.
     * */
    @Inject(at = @At("HEAD"), method = "save")
    private void saveMixin(boolean suppressLogs, boolean flush, boolean force, CallbackInfoReturnable<Boolean> cir) {
        LOGGER.info("Saving Configs, Database, and Area files");
        JSON.writeJSON(ModConfigs, Path.of("./config/FME/fme-config.json"));
        JSON.writeJSON(putPlayerDatabase(), Path.of("./config/FME/fme-db.json"));
    }
}
