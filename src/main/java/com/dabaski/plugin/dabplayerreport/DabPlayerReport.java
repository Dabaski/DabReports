package com.dabaski.plugin.dabplayerreport;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public final class DabPlayerReport extends Plugin {
    public static Map<String, UUID> playerList = new HashMap<>();
    public static String image, webhook, name;
    public static BaseComponent prefix = new TextComponent(ChatColor.translateAlternateColorCodes('&',"&7[&6Dab's Reports&7]&r "));

    @Override
    public void onEnable() {
        ProxyServer.getInstance().getPluginManager().registerCommand(this, new ReportCommand("report"));
        ProxyServer.getInstance().getPluginManager().registerListener(this, new TheListener());
        ProxyServer.getInstance().getLogger().info(prefix.toPlainText()+"HI IM ACTIVE NOW! WOULD YOU LIKE TO BE FRIENDS? :)");

        // Register other commands here
        try {
            Configuration config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(getDataFolder(), "config.yml"));
            DabPlayerReport.image = config.getString("image");
            DabPlayerReport.webhook = config.getString("webhook");
            DabPlayerReport.name = config.getString("name");
            assert !DabPlayerReport.image.isEmpty();
            assert !DabPlayerReport.name.isEmpty();
            assert !DabPlayerReport.webhook.isEmpty();
        } catch (IOException e) {
            this.onDisable();
            getDataFolder().mkdir();
            Configuration config = new Configuration();
            config.set("image", "");
            config.set("webhook", "");
            config.set("name", "");
            try {
                ConfigurationProvider.getProvider(YamlConfiguration.class).save(config, new File(getDataFolder(), "config.yml"));
            } catch (IOException ex) {
                throw new RuntimeException(prefix.toPlainText()+"Config.yml couldn't be saved!! Something's fucked!");
            }
            throw new RuntimeException(prefix.toPlainText()+"Invalid YML config, You forgot the info in the config! DUMBASS");

        }
    }

    @Override
    public void onDisable() {
        ProxyServer.getInstance().getPluginManager().unregisterCommands(this);
        ProxyServer.getInstance().getPluginManager().unregisterListeners(this);
        ProxyServer.getInstance().getLogger().severe(prefix.toPlainText()+"I don't wanna die. I've been disabled! ;-;");
    }
    // ... other methods


}
