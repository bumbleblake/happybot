package com.wheezygold.happybot.commands;

import com.jagrosh.jdautilities.commandclient.Command;
import com.jagrosh.jdautilities.commandclient.CommandEvent;
import com.wheezygold.happybot.util.C;
import com.wheezygold.happybot.util.Roles;

public class StaffManagementCommand extends Command {

    public StaffManagementCommand() {
        this.name = "staffmng";
        this.help = "A command to help Recruiters with their job.";
        this.arguments = "<deny/deny-level/approve> <user>";
        this.guildOnly = true;
        this.category = new Category("Staff Tools");
    }

    @Override
    protected void execute(CommandEvent e) {
        if (C.hasRole(e.getMember(), Roles.RECRUITER)) {
            if (!e.getArgs().isEmpty()) {
                if (e.getArgs().startsWith("deny ")) {
                    if (e.getMessage().getMentionedUsers().size() == 1) {
                        C.privChannel(C.getMentionedMember(e),
                                "Hey! I am sorry to say but your application has been denied due to **lack of detail**. You may reapply in 1 week!");
                        e.replySuccess("Application Denied!");
                    } else {
                        e.replyError("^staffmng <deny/deny-level/approve> <user>");
                    }
                } else if (e.getArgs().startsWith("deny-level ")) {
                    if (e.getMessage().getMentionedUsers().size() == 1) {
                        C.privChannel(C.getMentionedMember(e),
                                "Hey! I am sorry to say but your application has been denied due to lack of **community involvement**. You may reapply in 1 week!");
                        e.replySuccess("Application Denied!");
                    } else {
                        e.replyError("^staffmng <deny/deny-level/approve> <user>");
                    }
                } else if (e.getArgs().startsWith("approve ")) {
                    C.privChannel(C.getMentionedMember(e),
                            "Hey! I am sorry to say but your application has been APPROVED!!!111 Your rank will be applied very soon ;)");
                    e.replySuccess("Application Approved!");
                } else {
                    e.replyError("**Correct Usage:** ^" + name + " " + arguments);
                }
            } else {
                e.replyError("**Correct Usage:** ^" + name + " " + arguments);
            }
        } else {
            e.replyError(C.permMsg(Roles.RECRUITER));
        }
    }
}
