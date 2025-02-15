package me.lukiiy.zombalypse.cmd;

import me.lukiiy.zombalypse.Zombalypse;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class Reload implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        commandSender.sendMessage("§aZombalypse Config reloaded!");
        Zombalypse.getInstance().reloadConfig();
        return true;
    }
}
