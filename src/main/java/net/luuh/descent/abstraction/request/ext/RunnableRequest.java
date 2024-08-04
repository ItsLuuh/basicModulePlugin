package net.luuh.descent.abstraction.request.ext;

import net.luuh.descent.abstraction.request.Request;
import net.luuh.descent.abstraction.request.RequestPaths;
import net.luuh.descent.abstraction.request.RequestType;
import net.luuh.descent.players.objects.User;

import java.util.Set;

public class RunnableRequest extends Request {

    private final Runnable runnable;

    public RunnableRequest(User user, RequestType requestType, Set<RequestPaths> requestPaths, int time, Runnable runnable) {
        super(user, requestType, requestPaths, time);

        this.runnable = runnable;
    }

    @Override
    public void execute() {
        runnable.run();
    }
}
