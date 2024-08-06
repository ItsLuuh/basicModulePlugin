package net.luuh.descent.players.listeners;

import net.luuh.descent.Helper;
import net.luuh.descent.abstraction.DefaultListener;
import net.luuh.descent.attributes.AttributeManager;
import net.luuh.descent.attributes.attributes.Health;
import net.luuh.descent.players.objects.User;
import net.luuh.descent.players.stats.constant.StatType;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.concurrent.ExecutionException;

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
    public void playerJoin(PlayerJoinEvent e){
        if(!e.getPlayer().hasPlayedBefore()) Bukkit.broadcast(helper.getRMUtils().readTranslation(e.getPlayer(), "welcome-message"));
        e.joinMessage(helper.getRMUtils().readTranslation(e.getPlayer(), "join-message"));
    }

    @EventHandler
    public void playerQuit(PlayerQuitEvent e) {
        Player player = e.getPlayer();
        e.quitMessage(helper.getRMUtils().readTranslation(player, "quit-message"));
        helper.getPlayerManager().remove(player);
    }

}
