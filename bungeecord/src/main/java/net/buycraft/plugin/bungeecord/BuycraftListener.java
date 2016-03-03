package net.buycraft.plugin.bungeecord;

import lombok.RequiredArgsConstructor;
import net.buycraft.plugin.data.QueuedPlayer;
import net.buycraft.plugin.execution.PlayerLoginExecution;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

@RequiredArgsConstructor
public class BuycraftListener implements Listener {
    private final BuycraftPlugin plugin;

    @EventHandler
    public void onPostLogin(PostLoginEvent event) {
        if (plugin.getApiClient() == null) {
            return;
        }

        QueuedPlayer qp = plugin.getDuePlayerFetcher().fetchAndRemoveDuePlayer(event.getPlayer().getName());
        if (qp != null) {
            plugin.getLogger().info(String.format("Executing login commands for %s...", event.getPlayer().getName()));
            plugin.getProxy().getScheduler().runAsync(plugin, new PlayerLoginExecution(qp, plugin.getPlatform()));
        }
    }
}
