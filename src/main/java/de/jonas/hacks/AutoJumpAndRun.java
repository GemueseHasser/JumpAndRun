package de.jonas.hacks;

import de.jonas.jar.Jump_And_Run;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;

import java.util.Timer;
import java.util.TimerTask;

public class AutoJumpAndRun implements CommandExecutor {

    private static int minX = 0, minY = 100, minZ = 0;
    private static int maxX = 200, maxY = 200, maxZ = 200;

    private static Timer timer;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player) {
            Player p = (Player) sender;
            if(p.hasPermission("jar.hacks")) {
                if(args.length == 0) {
                    timer = new Timer();
                    timer.scheduleAtFixedRate(new TimerTask() {
                        @Override
                        public void run() {
                            double rX = Math.random()*(maxX-minX+1)+minX;
                            double rY = Math.random()*(maxY-minY+1)+minY;
                            double rZ = Math.random()*(maxZ-minZ+1)+minZ;

                            Location RandomLocation = new Location(p.getWorld(),rX,rY,rZ);

                            p.teleport(RandomLocation);
                            p.sendMessage("§4§lDU WURDEST VERARSCHT xD!");
                        }
                    },0,100);
                } else
                    p.sendMessage(Jump_And_Run.prefix + "Bitte benutze §c/hacks§6!");
            } else
                p.sendMessage("§cDazu hast du keine rechte!");
        } else
            sender.sendMessage("Du musst ein Spieler sein!");
        return true;
    }
}
