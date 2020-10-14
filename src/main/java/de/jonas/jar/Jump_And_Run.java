package de.jonas.jar;

import de.jonas.commands.Erklaerung;
import de.jonas.commands.SetPos;
import de.jonas.listener.OnMove;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Jump_And_Run extends JavaPlugin {

    private static  Jump_And_Run plugin;

    public static String prefix = "§f§l[§aJump-And-Run§f§l] §6";

    @Override
    public void onEnable() {
        plugin = this;

        //Commands
        getCommand("dec").setExecutor(new Erklaerung());
        getCommand("jarpos").setExecutor(new SetPos());

        PluginManager pm = Bukkit.getServer().getPluginManager();
        pm.registerEvents(new OnMove(), this);

        loadConfig();
    }

    @Override
    public void onDisable() {

    }

    public static Jump_And_Run getPlugin() {
        return plugin;
    }

    public void loadConfig() {
        getConfig().options().copyDefaults(true);
        saveConfig();
    }



}
