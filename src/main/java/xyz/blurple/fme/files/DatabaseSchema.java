package xyz.blurple.fme.files;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.command.ServerCommandSource;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DatabaseSchema {
    UUID PlayerUUID;
    List<WarnSchema> Warns;
    List<BanSchema> Bans;
    List<HistorySchema> History;

    public DatabaseSchema(PlayerEntity player) {
        this.PlayerUUID = player.getUuid();
        this.Warns = new ArrayList<WarnSchema>();
        this.Bans = new ArrayList<BanSchema>();
        this.History = new ArrayList<HistorySchema>();
    }

    public abstract class OffenceSchema {
        UUID Caller;
        String Reason;
        long Duration;
        long Timestamp;
        boolean Resolved;

        public OffenceSchema(ServerCommandSource context, String reason, long duration, long timestamp) {
            if (context.getPlayer() != null) {this.Caller = context.getPlayer().getUuid();}
            else {this.Caller = null;}

            if (reason != null) {this.Reason = reason;}
            else {this.Reason = "No Reason Given";}

            if (duration != 0L) {this.Duration = duration;}
            else {this.Duration = 3600L;}

            this.Timestamp = timestamp;

            this.Resolved = false;
        }
    }

    public class WarnSchema extends OffenceSchema {
        public WarnSchema(ServerCommandSource context, String reason, long duration, long timestamp) {
            super(context, reason, duration, timestamp);
        }
    }

    public class BanSchema extends OffenceSchema {
        public BanSchema(ServerCommandSource context, String reason, long duration, long timestamp) {
            super(context, reason, duration, timestamp);
        }
    }

    public class HistorySchema {
        List<LogginIPs> Logins;
        List<String> Username;
        List<AntiCheatFlags> AntiCheatFlags;
        boolean IsOnline;

        public HistorySchema(PlayerEntity player) {
            this.Logins = new ArrayList<LogginIPs>();
            this.Username = new ArrayList<String>();
            this.AntiCheatFlags = new ArrayList<AntiCheatFlags>();
            this.IsOnline = true;
        }

        public class LogginIPs {
            InetAddress IPAddress;
            Long Timestamp;

            /**
             * Stores the first time this player has logged in from this IP address.
             * */
            public LogginIPs(InetAddress IP, long Timestamp) {
                this.IPAddress = IP;
                this.Timestamp = Timestamp;
            }
        }
    }

    public class AntiCheatFlags extends OffenceSchema {
        @Deprecated
        boolean Resolved;
        public AntiCheatFlags(ServerCommandSource context, String reason, long duration, long timestamp) {
            super(context, reason, duration, timestamp);
        }
    }
}
