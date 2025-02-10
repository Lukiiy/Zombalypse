package me.lukiiy.zombalypse.type;

import me.lukiiy.zombalypse.Zombalypse;
import me.lukiiy.zombalypse.CustomType;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Zombie;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.persistence.PersistentDataType;

public class Pillar implements CustomType {
    @Override
    public String getId() {
        return "pillar";
    }

    @Override
    public String getName() {
        return "Pillar";
    }

    @Override
    public void onSpawn(Zombie zombie, CreatureSpawnEvent e) {
        Entity last = zombie;
        for (int i = 0; i < 2; i++) {
            Entity z = zombie.getWorld().spawn(zombie.getLocation(), zombie.getType().getEntityClass(), it -> it.getPersistentDataContainer().set(Zombalypse.key, PersistentDataType.STRING, "default"));
            last.addPassenger(z);
            last = z;
        }
    }

    @Override
    public void whenAttacked(Zombie zombie, EntityDamageByEntityEvent e) {
        if (!zombie.getPassengers().isEmpty()) e.setDamage(e.getDamage() / 2);
    }
}
