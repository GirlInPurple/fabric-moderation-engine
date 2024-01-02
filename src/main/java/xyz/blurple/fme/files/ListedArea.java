package xyz.blurple.fme.files;

import net.minecraft.util.math.BlockPos;

public class ListedArea {
    String ListingName;
    BlockPos ListingPosition;
    int ListingRadius;
    ListingConfigs ListingConfigs;

    public ListedArea(String name, BlockPos pos, int radius, ListingConfigs configs) {
        this.ListingName = name;
        this.ListingPosition = pos;
        this.ListingRadius = radius;
        this.ListingConfigs = configs;
    }

    public String getListingName() {return ListingName;}
    public BlockPos getListingPosition() {return ListingPosition;}
    public int getListingRadius() {return ListingRadius;}
    public ListingConfigs getListingConfigs() {return ListingConfigs;}

    public static class ListingConfigs {
        boolean logActions;
        boolean explosivesExplode;
        boolean liquidsInteractions;
        int playersInteractions;
        boolean mobInteractions;

        public ListingConfigs(boolean logActions, boolean explosivesExplode, boolean liquidsInteractions, int playersInteractions, boolean mobInteractions) {
            this.logActions = logActions;
            this.explosivesExplode = explosivesExplode;
            this.liquidsInteractions = liquidsInteractions;
            this.playersInteractions = playersInteractions;
            this.mobInteractions = mobInteractions;
        }
    }
}
