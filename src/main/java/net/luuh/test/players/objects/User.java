package net.luuh.test.players.objects;

import net.luuh.test.abstraction.modules.metadata.Metadata;
import net.luuh.test.abstraction.request.Request;
import net.luuh.test.players.economy.object.UserEconomy;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.math.BigDecimal;
import java.util.*;

public class User {

    private final UPT upt;
    private final String displayName;
    private final UserEconomy userEconomy;
    private Set<Request> requests;
    private final Map<Class<? extends Metadata>, Metadata> metadata;

    public User(UPT upt, String displayName, UserEconomy userEconomy) {
        this.upt = upt;
        this.displayName = displayName;
        this.userEconomy = userEconomy;
        this.metadata = new HashMap<>();
    }

    public UPT getUpt() {
        return upt;
    }

    public String getDisplayName() {
        return displayName;
    }

    public Player getPlayer() {return Bukkit.getPlayer(displayName);}

    public UserEconomy getUserEconomy() {
        return userEconomy;
    }

    public void setRequests(Set<Request> requests) {
        this.requests = requests;
    }

    public void SetRequest(Request request) {
        this.requests.stream().filter(request1 -> request1.getRequestType().equals(request.getRequestType())).findFirst().ifPresent(request1 -> requests.remove(request1));
    }

    public Set<Request> getRequests() {
        return requests;
    }

    public Request getRequest(String name) {
        return requests.stream().filter(request -> request.getPath(name) != null).findFirst().orElse(null);
    }

    public void addRequest(Request request) {
        requests.add(request);
    }

    public void removeRequest(Request request) {
        requests.remove(request);
    }

    public void clearRequests() {
        requests.clear();
    }

    public <T extends Metadata> Optional<T> getMetadata(Class<T> dataClass) {
        return Optional.ofNullable(dataClass.cast(metadata.get(dataClass)));
    }

    public void loadMetadata(Metadata data) {
        if (data != null) metadata.put(data.getClass(), data);
    }

    public <T extends  Metadata> void unloadMetadata(Class<T> clazz) {
        metadata.remove(clazz);
    }




}
