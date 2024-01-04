package xyz.blurple.fme.files;

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

    public List<OffenceSchema> getWarns() {return Warns;}
    public List<OffenceSchema> getBans() {return Bans;}
    public HistorySchema getHistory() {return History;}

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

        public UUID getCaller() {return Caller;}
        public String getReason() {return Reason;}
        public long getDuration() {return Duration;}
        public long getTimestamp() {return Timestamp;}
        public boolean isResolved() {return Resolved;}
    }

    public static class HistorySchema {
        List<LogginIPs> Logins;
        List<String> Username;
        List<OffenceSchema> AntiCheatFlags;
        boolean IsOnline;

        public HistorySchema(List<LogginIPs> IPs, List<String> usernames, List<OffenceSchema> antiCheatFlags, Boolean isOnline) {
            this.Logins = IPs;
            this.Username = usernames;
            this.AntiCheatFlags = antiCheatFlags;
            this.IsOnline = isOnline;
        }

        public List<LogginIPs> getLogins() {return Logins;}
        public LogginIPs getMostRecentLogin() {
            return getLogins().get(0);
        }
        public boolean doesLogginsContain(String ip) {
            for (LogginIPs IPInstance : getLogins()) {
                if (IPInstance.getIPAddress().contains(ip)) {return true;}
            }
            return false;
        }
        public List<String> getUsername() {return Username;}
        public String getMostRecentUsername() {
            return getUsername().get(0);
        }
        public boolean doesUsernamesContain(String name) {
            for (String nameInstance : getUsername()) {
                if (nameInstance.equals(name)) {return true;}
            }
            return false;
        }
        public List<OffenceSchema> getAntiCheatFlags() {return AntiCheatFlags;}
        public boolean isOnline() {return IsOnline;}

        public static class LogginIPs {
            String IPAddress;
            Long Timestamp;

            /**
             * Stores the first time this player has logged in from this IP address.
             * */
            public LogginIPs(String IP, long Timestamp) {
                this.IPAddress = IP;
                this.Timestamp = Timestamp;
            }

            public String getIPAddress() {return IPAddress;}
            public Long getTimestamp() {return Timestamp;}
        }
    }
}
