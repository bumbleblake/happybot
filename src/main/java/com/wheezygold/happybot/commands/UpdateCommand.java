package com.wheezygold.happybot.commands;

import com.jagrosh.jdautilities.commandclient.Command;
import com.jagrosh.jdautilities.commandclient.CommandEvent;
import com.wheezygold.happybot.util.C;
import com.wheezygold.happybot.util.Roles;

import java.util.concurrent.TimeUnit;

public class UpdateCommand extends Command {

    public UpdateCommand() {
        this.name = "update";
        this.help = "Downloads new code for the bot!";
        this.guildOnly = false;
        this.category = new Category("Bot Management");
    }

    @Override
    protected void execute(CommandEvent e) {
        if (C.hasRole(e.getMember(), Roles.DEVELOPER)) {
            new Thread(new Update(e)).start();
        } else {
            e.replyError(C.permMsg(Roles.DEVELOPER));
        }
    }

    class Update implements Runnable {

        private CommandEvent e;

        Update(CommandEvent e) { this.e = e; }

        @Override
        public void run() {
            e.reply(":white_check_mark: Downloading Update!");
            e.reply(":information_source: Restarting Bot...");
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
            e.getJDA().shutdown();
            C.dlFile("https://dl.dropbox.com/s/momoz7ciigj2msg/HappyBot.jar?dl=1", "HappyBot.jar");
            C.log("The JDA instance has been shutdown...exiting the program.");
            System.exit(0);
        }
    }

}
