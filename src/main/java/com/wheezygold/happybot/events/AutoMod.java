package com.wheezygold.happybot.events;

import com.wheezygold.happybot.util.C;
import com.wheezygold.happybot.util.Channels;
import com.wheezygold.happybot.util.Roles;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.events.message.guild.GuildMessageUpdateEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.util.List;

public class AutoMod extends ListenerAdapter {

    private List<Message> processedMessages;

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        checkForAdvertising(event.getMember(), event.getMessage(), event.getChannel());
    }

    @Override
    public void onGuildMessageUpdate(GuildMessageUpdateEvent event) {
        checkForAdvertising(event.getMember(), event.getMessage(), event.getChannel());
    }

    private void checkForAdvertising(Member member, Message message, TextChannel channel) {
        if (C.hasRole(member, Roles.SUPER_ADMIN) || C.hasRole(member, Roles.BOT))
            return;
        if (!message.getContent().toLowerCase().contains("discord.gg/"))
            return;
        message.delete().reason("Advertising Link with Message: " + message.getStrippedContent()).queue();
        Channels.LOG.getChannel().sendMessage(member.getAsMention() + " attempted to advert the following link: " + message.getContent()).queue();
        C.privChannel(member, "You cannot advertise in the happyheart guild!");
        if (!processedMessages.contains(message)) {
            channel.sendMessage(member.getAsMention() + "! Do not advert other discord servers!").queue();
            processedMessages.add(message);
        }
    }

}
