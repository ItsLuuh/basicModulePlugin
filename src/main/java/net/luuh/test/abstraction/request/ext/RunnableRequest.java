package net.luuh.test.abstraction.request.ext;

import net.luuh.test.abstraction.request.Request;
import net.luuh.test.abstraction.request.RequestPaths;
import net.luuh.test.abstraction.request.RequestType;
import net.luuh.test.players.objects.User;

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
