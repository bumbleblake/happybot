package com.wheezygold.happybot.commands;

import com.jagrosh.jdautilities.commandclient.Command;
import com.jagrosh.jdautilities.commandclient.CommandEvent;
import com.wheezygold.happybot.util.C;
import com.wheezygold.happybot.util.Roles;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.PermissionOverride;
import net.dv8tion.jda.core.managers.PermOverrideManager;

public class LockCommand extends Command {
    public LockCommand() {
        this.name = "lock";
        this.help = "Locks the current channel!";
        this.guildOnly = true;
        this.category = new Category("Staff Tools");
    }

    @Override
    protected void execute(CommandEvent e) {
        if (C.hasRole(e.getMember(), Roles.ADMIN)) {

            try {
                PermissionOverride permissionOverride = e.getTextChannel().getPermissionOverride(Roles.EVERYONE.getRole());
                PermOverrideManager manager = permissionOverride.getManager();
                manager.deny(Permission.MESSAGE_WRITE).queue();
                e.replySuccess(":lock: Channel has been locked!");
            } catch (NullPointerException npe) {
                e.replyError("An error occurred while locking the channel! Please make sure this channel is setup correctly.");
            }


        } else {
            e.replyError(C.permMsg(Roles.ADMIN));
        }
    }
}
