package com.wheezygold.happybot.commands;

import com.jagrosh.jdautilities.commandclient.Command;
import com.jagrosh.jdautilities.commandclient.CommandEvent;
import com.kbrewster.hypixelapi.exceptions.APIException;
import com.kbrewster.hypixelapi.player.Player;
import com.wheezygold.happybot.util.Hypixel;
import com.wheezygold.happybot.util.YouTube;
import net.dv8tion.jda.core.EmbedBuilder;

import java.util.HashMap;

public class StatsCommand extends Command {

    private Hypixel hypixel;

    public StatsCommand(Hypixel hypixel) {
        this.name = "stats";
        this.help = "Gives stats of a happy's channel and hypixel player.";
        this.arguments = "<youtube/hypixel>";
        this.category = new Category("Fun");
        this.hypixel = hypixel;
    }

    @Override
    protected void execute(CommandEvent e) {
        if (e.getArgs().equalsIgnoreCase("youtube")) {
            new Thread(new GetYoutubeStats(e)).start();
        } else if (e.getArgs().equalsIgnoreCase("hypixel")) {
            new Thread(new GetHypixelStats(e)).start();
        } else {
            e.replyError("**Correct Usage:** ^" + name + " " + arguments);
        }
    }

    class GetYoutubeStats implements Runnable {

        private CommandEvent e;

        GetYoutubeStats(CommandEvent e) {
            this.e = e;
        }

        @Override
        public void run() {

            YouTube youTube = new YouTube().pullAPI();

            e.reply(new EmbedBuilder()
                    .setTitle("Happyheart's YouTube Statistics")
                    .setDescription("Listing Statistics:")
                    .setFooter("Stats provided by YouTube's Realtime API", "https://www.youtube.com/yts/img/favicon-vfl8qSV2F.ico")
                    .addField("**Subscribers**", youTube.fetchSubs(), true)
                    .addField("**Videos**", youTube.fetchVids(), true)
                    .addField("**Views**", youTube.fetchViews(), true)
                    .build());
            youTube.finish();
        }
    }

    class GetHypixelStats implements Runnable {

        private CommandEvent e;

        GetHypixelStats(CommandEvent e) {
            this.e = e;
        }

        @Override
        public void run() {
            Player hypixelPlayer = null;
            try {
                hypixelPlayer = hypixel.getPlayer("happyheart");
            } catch (APIException e1) {
                e1.printStackTrace();
            }

            EmbedBuilder embed = new EmbedBuilder()
                    .setTitle("Happyheart's Hypixel Statistics")
                    .setDescription("Listing statistics:")
                    .setFooter("Stats provided by Hypixel's API", "https://media-curse.cursecdn.com/attachments/264/727/f7c76fdb4569546a9ddf0e58c8653823.png");

            for (HashMap.Entry<String, String> entry : Hypixel.getAllFields(hypixelPlayer).entrySet()) {
                if (entry.getValue() != null) {
                    embed.addField("**" + entry.getKey() + "**", entry.getValue(), true);
                }
            }

            e.reply(embed.build());
        }
    }

}
