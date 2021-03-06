package com.wheezygold.happybot.util;

import com.kbrewster.hypixelapi.HypixelAPI;
import com.kbrewster.hypixelapi.exceptions.APIException;
import com.kbrewster.hypixelapi.exceptions.InvalidPlayerException;
import com.kbrewster.hypixelapi.player.Player;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class Hypixel {

    private static HypixelAPI api;

    public Hypixel(String apikey) {
        api = new HypixelAPI(apikey);
    }

    public Player getPlayer(String playerName) throws APIException {
        try {
            return api.getPlayer(playerName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static HashMap<String, String> getAllFields(Player hypixelPlayer) {
        HashMap<String, String> fields = new HashMap<>();
        if (hypixelPlayer != null) {
            fields.put("Network Level", String.valueOf(hypixelPlayer.getNetworkLevel()));
            fields.put("Rank", hypixelPlayer.getCurrentRank());
            fields.put("MC Version", hypixelPlayer.getMcVersionRp());
            fields.put("Bedwars Wins", String.valueOf(hypixelPlayer.getAchievements().getBedwarsWins()));
            fields.put("Bedwars Level", String.valueOf(hypixelPlayer.getAchievements().getBedwarsLevel()));
            fields.put("Karma", String.valueOf(hypixelPlayer.getKarma()));
            fields.put("Language", hypixelPlayer.getUserLanguage());
            fields.put("Vanity Tokens", String.valueOf(hypixelPlayer.getVanityTokens()));
            fields.put("Join Date", new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(new Date(hypixelPlayer.getFirstLogin())));
            fields.put("Last Join", new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(new Date(hypixelPlayer.getLastLogin())));
        }
        return fields;
    }

    public boolean isValidPlayer(String playerName) {

        if (playerName.contains(" ")) {
            return false;
        }

        try {
            api.getPlayer(playerName);
            return true;
        } catch (APIException | IOException e) {
            e.printStackTrace();
            return false;
        } catch (InvalidPlayerException ie) {
            return false;
        }
    }


}
