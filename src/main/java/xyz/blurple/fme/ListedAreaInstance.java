package xyz.blurple.fme;

import net.minecraft.util.math.BlockPos;

public class ListedAreaInstance {
    BlockPos ListingPosition;
    String ListingType;
    int ListingRadius;

    public ListedAreaInstance(BlockPos pos, String type, int radius) {
        this.ListingPosition = pos;
        this.ListingType = type;
        this.ListingRadius = radius;
    }

    public BlockPos getListingPosition() {return ListingPosition;}
    public int getListingRadius() {return ListingRadius;}
    public String getListingType() {return ListingType;}
}
