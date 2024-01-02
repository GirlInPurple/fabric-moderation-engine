package xyz.blurple.fme.files;

import java.net.InetAddress;
import java.util.List;
import java.util.UUID;

public class DatabaseSchema {
    List<OffenceSchema> Warns;
    List<OffenceSchema> Bans;
    HistorySchema History;

    public DatabaseSchema(List<OffenceSchema> warns, List<OffenceSchema> bans, HistorySchema history) {
        this.Warns = warns;
        this.Bans = bans;
        this.History = history;
    }

    public static class OffenceSchema {
        UUID Caller;
        String Reason;
        long Duration;
        long Timestamp;
        boolean Resolved;

        public OffenceSchema(UUID caller, String reason, long duration, long timestamp, boolean resolved) {
            this.Caller = caller;

            if (reason != null) {this.Reason = reason;}
            else {this.Reason = "No Reason Given";}

            if (duration != 0L) {this.Duration = duration;}
            else {this.Duration = 3600L;}

            this.Timestamp = timestamp;

            this.Resolved = resolved;
        }
    }

    public static class HistorySchema {
        List<LogginIPs> Logins;
        List<String> Username;
        List<OffenceSchema> AntiCheatFlags;
        boolean IsOnline;

        public HistorySchema(List<LogginIPs> IPs, List<String> usernames, List<OffenceSchema> antiCheatFlags) {
            this.Logins = IPs;
            this.Username = usernames;
            this.AntiCheatFlags = antiCheatFlags;
            this.IsOnline = false; // Assuming this is run as the server starts, this should always be false
        }

        public static class LogginIPs {
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
}
