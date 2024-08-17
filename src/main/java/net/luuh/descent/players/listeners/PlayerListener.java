package net.luuh.descent.players.listeners;

import net.luuh.descent.Helper;
import net.luuh.descent.abstraction.DefaultListener;
import net.luuh.descent.players.mana.HealthBar;
import net.luuh.descent.players.mana.ManaBar;
import net.luuh.descent.players.objects.User;
import net.luuh.descent.players.stats.object.UserAttributes;
import net.luuh.descent.players.task.HealthRegenTask;
import net.luuh.descent.players.task.ManaRegenTask;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.Arrays;

public class PlayerListener extends DefaultListener {
    public PlayerListener(Helper helper) {
        super(helper);
    }

    @EventHandler
    public void playerLogin(PlayerLoginEvent e) {
        Player player = e.getPlayer();
        if(e.getResult() != PlayerLoginEvent.Result.ALLOWED) return;
        helper.getUserManager().load(player).thenRun(() -> {
            User user = helper.getUserManager().getUser(player);
            user.setUserAttributes(new UserAttributes(user.getUpt()));
            user.getUserAttributes().load(player);
            user.createManaBar();
            user.createHealthBar();
            user.getManaBar().setMana(user.getManaBar().getMaxMana());
        });
        player.setFoodLevel(8);
    }

    @EventHandler
    public void playerJoin(PlayerJoinEvent e){
        if(!e.getPlayer().hasPlayedBefore()) Bukkit.broadcast(helper.getRMUtils().readTranslation("welcome-message"));
        e.joinMessage(helper.getRMUtils().readTranslation("join-message"));
    }

    @EventHandler
    public void playerQuit(PlayerQuitEvent e) {
        Player player = e.getPlayer();
        e.quitMessage(helper.getRMUtils().readTranslation(player, "quit-message"));
        helper.getUserManager().remove(player);
    }

}
