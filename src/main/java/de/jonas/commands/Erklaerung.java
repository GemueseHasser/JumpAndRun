package de.jonas.commands;

import de.jonas.jar.Jump_And_Run;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Erklaerung implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player) {
            Player p = (Player) sender;
            if(p.hasPermission("jar.declaration") || p.hasPermission("jar.*")) {
                if(args.length == 0) {
                    p.sendMessage("§c--------------------------------");
                    p.sendMessage("§aFühre den Command /jarpos aus!");
                    p.sendMessage(" ");
                    p.sendMessage("§aWenn du jetzt, auf die Druckplatte gehst startet das §4Jump-And-Run§a! §6:D");
                    p.sendMessage("§c--------------------------------");
                } else
                    p.sendMessage(Jump_And_Run.prefix + "Bitte benutze §c/dec§6!");
            } else
                p.sendMessage("§cDazu hast du keine Rechte!");
        } else
            sender.sendMessage("Du musst ein Spieler sein!");
        return true;
    }
}
