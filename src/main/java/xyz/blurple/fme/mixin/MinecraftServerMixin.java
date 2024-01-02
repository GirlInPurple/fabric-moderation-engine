package xyz.blurple.fme.mixin;

import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xyz.blurple.fme.files.FileHandler.*;

import java.nio.file.Path;

import static xyz.blurple.fme.FMEInit.ModConfigs;
import static xyz.blurple.fme.FMEInit.PlayerDatabase;

@Mixin(MinecraftServer.class)
public class MinecraftServerMixin {
    /**
     * On world save, the .json file will be saved.
     * */
    @Inject(at = @At("HEAD"), method = "save")
    private void saveMixin(boolean suppressLogs, boolean flush, boolean force, CallbackInfoReturnable<Boolean> cir) {
        JSON.writeJSON(ModConfigs, Path.of("./config/FME/fme-config.json"));
        JSON.writeJSON(PlayerDatabase, Path.of("./config/FME/fme-db.json"));
    }
}
