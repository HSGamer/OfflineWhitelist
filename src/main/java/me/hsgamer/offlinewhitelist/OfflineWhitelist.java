package me.hsgamer.offlinewhitelist;

import org.bukkit.OfflinePlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public final class OfflineWhitelist extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
    }

    @Override
    public void onDisable() {
        HandlerList.unregisterAll((Plugin) this);
    }

    @EventHandler
    public void onJoin(PlayerLoginEvent event) {
        if (event.getResult() != PlayerLoginEvent.Result.KICK_WHITELIST) {
            return;
        }
        if (getServer().getOnlineMode()) {
            return;
        }
        List<String> name = getServer().getWhitelistedPlayers().stream()
                .map(OfflinePlayer::getName)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        if (name.contains(event.getPlayer().getName())) {
            event.allow();
        }
    }
}
