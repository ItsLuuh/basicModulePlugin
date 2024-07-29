package net.luuh.test.abstraction.request;

import net.luuh.test.players.objects.User;

import java.util.Set;

public abstract class Request {

    public final static String ACCEPT_COMMAND = "/#@acceptRequest@#";

    protected final User user;
    protected final RequestType requestType;
    protected final Set<RequestPaths> paths;
    protected int time; //seconds

    protected Request(User user, RequestType requestType, Set<RequestPaths> paths, int time) {
        this.user = user;
        this.requestType = requestType;
        this.paths = paths;
        this.time = time;
    }

    /*
    public void send() {
        this.user.setRequest(this);

        Component fixedComponent = component
                .hoverEvent(HoverEvent.hoverEvent(HoverEvent.Action.SHOW_TEXT, Component.text("ᴄʟɪᴄᴄᴀ ᴘᴇʀ ᴀᴄᴄᴇᴛᴛᴀʀᴇ", Palette.YELLOW)))
                .clickEvent(ClickEvent.clickEvent(ClickEvent.Action.RUN_COMMAND, ACCEPT_COMMAND));

        this.user.getPlayer().sendMessage(fixedComponent);
    }
     */

    public RequestType getRequestType() {
        return requestType;
    }

    public String getPath(String name) {
        return paths.stream().filter(requestPaths -> requestPaths.getPath().equals(name)).findFirst().get().getPath();
    }

    public Set<RequestPaths> getPaths() {
        return paths;
    }

    public int getTime() {
        return time;
    }

    public void decreaseTime() {
        time--;
    }

    public boolean isExpired() {
        return time <= 0;
    }

    public void cancel() {
        user.removeRequest(this);
    }

    public void accept() {
        execute();

        cancel();
    }

    public abstract void execute();

}
