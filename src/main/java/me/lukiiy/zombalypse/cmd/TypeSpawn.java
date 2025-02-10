package me.lukiiy.zombalypse.cmd;

import me.lukiiy.zombalypse.CustomType;
import me.lukiiy.zombalypse.Zombalypse;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.persistence.PersistentDataType;

import java.util.List;
import java.util.stream.Collectors;

public class TypeSpawn implements CommandExecutor, TabExecutor {
    private final Zombalypse instance;

    public TypeSpawn(Zombalypse instance) {
        this.instance = instance;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (!(commandSender instanceof Player p)) {
            commandSender.sendMessage("§cOnly in-game players can use this command!");
            return true;
        }

        if (strings.length == 0) {
            commandSender.sendMessage("§eRegistered types: " + instance.getTypes().stream().map(CustomType::getId).collect(Collectors.joining(", ")));
            return true;
        }

        CustomType type = Zombalypse.getInstance().getType(strings[0].toLowerCase());
        if (type == null) {
            commandSender.sendMessage("§cZombie type not found.");
            return true;
        }

        commandSender.sendMessage("§aSpawned " + type.getName() + " type zombie.");
        p.getWorld().spawn(p.getLocation(), Zombie.class, it -> it.getPersistentDataContainer().set(Zombalypse.key, PersistentDataType.STRING, type.getId()));
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        return instance.getTypes().stream().map(CustomType::getId).toList();
    }
}
