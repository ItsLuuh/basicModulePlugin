package net.luuh.descent;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;

import java.util.concurrent.Executor;

public class Scheduler {

    final BukkitScheduler BUKKIT_SCHEDULER = Bukkit.getServer().getScheduler();

    private final Main plugin;

    public Scheduler(Main plugin) {
        this.plugin = plugin;
    }

    private final Executor syncExecutor = this::sync;

    public BukkitTask sync(Runnable runnable) {
        return BUKKIT_SCHEDULER.runTask(plugin, runnable);
    }

    public BukkitTask later(Runnable runnable, int ticks) {
        return BUKKIT_SCHEDULER.runTaskLater(plugin, runnable, ticks);
    }

    public BukkitTask laterAsync(Runnable runnable, int ticks) {
        return BUKKIT_SCHEDULER.runTaskLaterAsynchronously(plugin, runnable, ticks);
    }

    public BukkitTask timer(Runnable runnable, int after, int delay) {
        return BUKKIT_SCHEDULER.runTaskTimer(plugin, runnable, after, delay);
    }

    public BukkitTask timerAsync(Runnable runnable, int after, int delay) {
        return BUKKIT_SCHEDULER.runTaskTimerAsynchronously(plugin, runnable, after, delay);
    }

    public Executor getSyncExecutor() {
        return syncExecutor;
    }
}
