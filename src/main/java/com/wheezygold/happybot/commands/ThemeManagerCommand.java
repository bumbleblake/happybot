package com.wheezygold.happybot.commands;

import com.jagrosh.jdautilities.commandclient.Command;
import com.jagrosh.jdautilities.commandclient.CommandEvent;
import com.wheezygold.happybot.theme.ThemeManager;
import com.wheezygold.happybot.theme.exceptions.InvalidThemeFileException;
import com.wheezygold.happybot.theme.exceptions.ThemeNotFoundException;
import com.wheezygold.happybot.util.C;
import com.wheezygold.happybot.util.Roles;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.net.URL;

public class ThemeManagerCommand extends Command {

    private ThemeManager themeManager;

    public ThemeManagerCommand(ThemeManager themeManager) {
        this.name = "thememngr";
        this.help = "A command to manage themes.";
        this.arguments = "<load/delete> <link/theme name> <theme name>";
        this.guildOnly = true;
        this.category = new Category("Bot Management");
        this.themeManager = themeManager;
    }

    @Override
    protected void execute(CommandEvent e) {
        if (C.hasRole(e.getMember(), Roles.ADMIN)) {
            if (e.getArgs().isEmpty()) {
                e.replyError("**Correct Usage:** ^" + name + " " + arguments);
                return;
            }
            String[] args = e.getArgs().split("[ ]");
            if (args[0].equalsIgnoreCase("load")) {
                if (!(args.length == 3)) {
                    e.replyError("**Correct Usage:** ^" + name + " " + arguments);
                    return;
                }
                try {
                    new URL(args[1]);
                } catch (MalformedURLException ignored) {
                    e.replyError("Invalid URL");
                    return;
                }

                C.dlFile(args[1], "themes/"+args[2]+".dat");

                try {
                    if (themeManager.validateTheme(args[2]+".dat", true)) {
                        e.replySuccess("Loaded Theme: " + args[2]);
                    }
                    e.replyError("The provided theme file was invalid!");
                    new File(args[2] + ".dat").delete();
                } catch (FileNotFoundException e1) {
                    e1.printStackTrace();
                } catch (InvalidThemeFileException e1) {
                    e.replyError("An InvalidThemeFileException was thrown when parsing your file:" + C.codeblock(e1.getMessage()));
                    new File("themes"+args[2]+".dat").delete();
                }
            } else if (args[0].equalsIgnoreCase("delete")) {
                if (args.length != 2) {
                    e.replyError("**Correct Usage:** ^" + name + " " + arguments);
                    return;
                }
                if (themeManager.getThemes().contains(args[1])) {
                    try {
                        themeManager.removeTheme(args[1]);
                        e.replySuccess("Deleted Theme: "+args[1]);
                        new File(args[1] + ".dat").renameTo(new File("themes/deleted/"+args[1]+".dat"));
                    } catch (ThemeNotFoundException e1) {
                        e.replyError("The target theme was not found!");
                    }
                }
            } else {
                e.replyError("**Correct Usage:** ^" + name + " " + arguments);
            }
        } else {
            e.replyError(C.permMsg(Roles.ADMIN));
        }
    }
}
