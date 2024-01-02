package xyz.blurple.fme.areas;

public class ListingConfigs {
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
