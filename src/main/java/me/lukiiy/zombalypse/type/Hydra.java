package me.lukiiy.zombalypse.type;

import me.lukiiy.zombalypse.Zombalypse;
import me.lukiiy.zombalypse.CustomType;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.PigZombie;
import org.bukkit.entity.Zombie;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.persistence.PersistentDataType;

import java.util.HashMap;

public class Hydra implements CustomType {
    private final HashMap<Zombie, Integer> multiples = new HashMap<>();

    @Override
    public String getId() {
        return "hydra";
    }

    @Override
    public String getName() {
        return "Multiplier";
    }

    @Override
    public void onSpawn(Zombie zombie, CreatureSpawnEvent e) {
        if (zombie instanceof PigZombie) incrAttribute(zombie, Attribute.GENERIC_MAX_HEALTH, -12);
        zombie.setRemoveWhenFarAway(true);
    }

    @Override
    public void onDeath(Zombie zombie, EntityDeathEvent e) {
        int multi = multiples.getOrDefault(zombie, 1);

        if (multi > 3) {
            multiples.remove(zombie);
            return;
        }

        for (int i = 0; i < 2; i++) {
            Zombie copy = (Zombie) zombie.getWorld().spawn(zombie.getLocation(), zombie.getType().getEntityClass(), it -> {
                Zombalypse.getInstance().setType(zombie, Zombalypse.getInstance().getType("hydra"));
                multiples.put((Zombie) it, multi + 1);
            });

            AttributeInstance h = copy.getAttribute(Attribute.GENERIC_MAX_HEALTH);
            if (h != null) h.setBaseValue(h.getValue() / ((double) multi / 2));
        }
    }
}
