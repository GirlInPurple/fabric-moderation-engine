package xyz.blurple.fme;

import net.minecraft.entity.TntEntity;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.entity.vehicle.TntMinecartEntity;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;

import static xyz.blurple.fme.FMEInit.ListedAreaList;

public class ListedAreaUtils {

    public static boolean isInsideArea(Object self, World world) {
        if (!(self instanceof TntEntity) && !(self instanceof TntMinecartEntity) && !(self instanceof EndCrystalEntity)) {return false;}
        for (ListedAreaInstance ListedArea : ListedAreaList) {
            Box EntitySearchBox = new Box(ListedArea.getListingPosition()).expand(ListedArea.getListingRadius());
            if (world.getEntitiesByClass(TntEntity.class, EntitySearchBox, tntEntity -> true).contains(self)) {return true;}
            if (world.getEntitiesByClass(TntMinecartEntity.class, EntitySearchBox, tntMinecartEntity -> true).contains(self)) {return true;}
            if (world.getEntitiesByClass(EndCrystalEntity.class, EntitySearchBox, endCrystalEntity -> true).contains(self)) {return true;}
        }
        return false;
    }
}
