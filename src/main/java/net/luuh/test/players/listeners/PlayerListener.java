package net.luuh.test.players.listeners;

import net.kyori.adventure.text.Component;
import net.luuh.test.Helper;
import net.luuh.test.abstraction.DefaultListener;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerListener extends DefaultListener {
    public PlayerListener(Helper helper) {
        super(helper);
    }

    @EventHandler
    public void playerLogin(PlayerLoginEvent e) {
        Player player = e.getPlayer();
        if(e.getResult() != PlayerLoginEvent.Result.ALLOWED) return;
        helper.getPlayerManager().load(player);
    }

    @EventHandler
    public void playerJoin(PlayerJoinEvent e) {
        e.joinMessage(Component.empty());
    }

    @EventHandler
    public void playerQuit(PlayerQuitEvent e) {
        Player player = e.getPlayer();
        e.quitMessage(helper.getRMUtils().readTranslation(player, "player-quit"));
        helper.getPlayerManager().remove(player);
    }

}
