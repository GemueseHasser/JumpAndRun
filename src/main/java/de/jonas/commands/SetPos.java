package de.jonas.commands;

import de.jonas.jar.Jump_And_Run;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetPos implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player) {
            Player p = (Player) sender;
            if(p.hasPermission("jar.jarpos") || p.hasPermission("jar.*")) {
                if(args.length == 0) {
                    p.getLocation().subtract(0.0D, 1.0D, 0.0).getBlock().setType(Material.BEDROCK);
                    p.getLocation().getBlock().setType(Material.IRON_PLATE);
                } else
                    p.sendMessage(Jump_And_Run.prefix + "Bitte benutze §c/jarpos§6!");
            } else
                p.sendMessage("§cDazu hast du keine Rechte!");
        } else
            sender.sendMessage("Du musst ein Spieler sein!");
        return true;
    }
}
