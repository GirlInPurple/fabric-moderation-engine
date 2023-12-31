package xyz.blurple.fme.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.server.command.CommandOutput;
import net.minecraft.util.Nameable;
import net.minecraft.world.World;
import net.minecraft.world.entity.EntityLike;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static xyz.blurple.fme.ListedAreaUtils.isInsideArea;

@Mixin(Entity.class)
public abstract class AbstractEntityMixin implements Nameable, EntityLike, CommandOutput {

    @Shadow public abstract void remove(Entity.RemovalReason reason);
    @Shadow private World world;

    @Inject(at = @At("HEAD"), method = "baseTick")
    public void baseTick(CallbackInfo ci) {
        if (isInsideArea(this, this.world)) {this.remove(Entity.RemovalReason.KILLED);}
    }
}
