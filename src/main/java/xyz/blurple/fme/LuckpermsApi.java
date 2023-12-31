package xyz.blurple.fme;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.group.Group;

import java.util.Collection;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class LuckpermsApi {

    /**
     * The Luckperms API instance. Visit the <a href="https://luckperms.net/wiki/Developer-API">Luckperms Docs</a> for more info
     * */
    public static final LuckPerms api = LuckPermsProvider.get();

    public static CompletableFuture<Boolean> isAdmin(UUID who) {
        return api.getUserManager().loadUser(who)
                .thenApplyAsync(user -> {
                    Collection<Group> inheritedGroups = user.getInheritedGroups(user.getQueryOptions());
                    return inheritedGroups.stream().anyMatch(g -> g.getName().equals("admin"));
                });
    }
}
