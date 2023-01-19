package com.dabaski.plugin.dabplayerreport;


import com.dabaski.plugin.dabplayerreport.discordwebhook.DiscordWebHook;
import com.dabaski.plugin.dabplayerreport.discordwebhook.embed.Embed;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Command;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ReportCommand extends Command {

    public ReportCommand(String name) {
        super(name);
    }

    public ReportCommand(String name, String permission, String... aliases) {
        super(name, permission, aliases);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        BaseComponent text = DabPlayerReport.prefix.duplicate();
        if (args.length < 2) {
            // Send error message to sender if not enough arguments were provided
            text.addExtra(new TextComponent("Use /report <player> <reason>"));
            sender.sendMessage(text);
            return;
        }
        List<String> arguments = new ArrayList<>(Arrays.asList(args));
        String player = arguments.get(0);
        arguments.remove(0);
        String reason = String.join(" ", arguments);
        if (!DabPlayerReport.playerList.containsKey(player)) {
            text.addExtra(new TextComponent("This player hasn't been on recently, or never joined"));
            sender.sendMessage(text);
            return;
        }

        if (player.equals(sender.getName())) {
            text.addExtra(new TextComponent("You can't report yourself dumbass -NobreHD"));
            sender.sendMessage(text);
            return;
        }
        Embed embed = new Embed.Builder()
                .title("Player Reported")
                .description(String.format("Player: %s\nReason: %s", player, reason))
                .footer(String.format("Reported by %s", sender.getName()))
                .thumbnail(String.format("https://crafatar.com/avatars/%s?overlay", DabPlayerReport.playerList.get(player)))
                .build();
        DiscordWebHook message = new DiscordWebHook.Builder()
                .username(DabPlayerReport.name)
                .avatar_url(DabPlayerReport.image)
                .embed(embed)
                .build();
        DiscordWebHook.sendMessage(DabPlayerReport.webhook, message);
        text.addExtra(new TextComponent("Your report has been made! Thank you!"));
        sender.sendMessage(text);

    }
}