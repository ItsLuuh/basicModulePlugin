package net.luuh.test.players.objects;

import org.bukkit.entity.Player;

import java.security.SecureRandom;

public class UPT {

    /*
     * Unique Personal Token
     *
     * Use: Adds a possibility to Offline-mode to have a decentralized unique token for each player.
     */

    private static final String CHARACTERS = "abcdefghijklmnopqrstuvwxyz0123456789";
    private static final int ID_LENGTH = 12;
    private final Player player;
    private final String token;

    public UPT(Player player, String token) {
        this.player = player;
        this.token = token;
    }

    public static UPT generate(Player player) {
        SecureRandom random = new SecureRandom();
        StringBuilder id = new StringBuilder(ID_LENGTH);

        for (int i = 0; i < ID_LENGTH; i++) {
            int randomIndex = random.nextInt(CHARACTERS.length());
            id.append(CHARACTERS.charAt(randomIndex));
        }

        return new UPT(player, id.toString());
    }

    public String getToken() {
        return token;
    }

    public Player getPlayer() {
        return player;
    }
}
