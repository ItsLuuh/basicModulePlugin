package net.luuh.descent.players.task;

import net.luuh.descent.Helper;
import net.luuh.descent.abstraction.request.RequestPaths;
import org.bukkit.scheduler.BukkitRunnable;

public class RequestsTask extends BukkitRunnable {

    private final Helper helper;

    public RequestsTask(Helper helper) {
        this.helper = helper;
    }

    @Override
    public void run() {
        helper.getPlayerManager().forEach(user -> {
            if(user.getRequests() == null) return;
            user.getRequests().forEach(request -> {
                if (request.isExpired()) {
                    request.cancel();
                    user.getPlayer().sendMessage(
                            helper.getRMUtils().readTranslation(
                                    user.getPlayer(),
                                    request.getPath(RequestPaths.TRANSACTION_EXPIRED.getPath()
                                    )
                            )
                    );
                    return;
                }

                request.decreaseTime();
            });

        });
    }
}
