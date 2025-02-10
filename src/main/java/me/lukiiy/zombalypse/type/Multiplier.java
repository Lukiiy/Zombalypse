package me.lukiiy.zombalypse.type;

import me.lukiiy.zombalypse.Zombalypse;
import me.lukiiy.zombalypse.CustomType;
import org.bukkit.entity.Zombie;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.persistence.PersistentDataType;

import java.util.HashMap;

public class Multiplier implements CustomType {
    private final HashMap<Zombie, Integer> multiples = new HashMap<>();

    @Override
    public String getId() {
        return "multiplier";
    }

    @Override
    public String getName() {
        return "Multiplier";
    }

    @Override
    public void onDeath(Zombie zombie, EntityDeathEvent e) {
        int multi = multiples.getOrDefault(zombie, 1);

        if (multi > 3) {
            multiples.remove(zombie);
            return;
        }

        for (int i = 0; i < 2; i++) {
            zombie.getWorld().spawn(zombie.getLocation(), zombie.getType().getEntityClass(), it -> {
                it.getPersistentDataContainer().set(Zombalypse.key, PersistentDataType.STRING, getId());
                multiples.put((Zombie) it, multi + 1);
            });
        }
    }
}
