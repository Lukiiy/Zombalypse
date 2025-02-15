package me.lukiiy.zombalypse.type;

import me.lukiiy.zombalypse.CustomType;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Zombie;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;

public class Hopper implements CustomType {
    @Override
    public String getId() {
        return "hopper";
    }

    @Override
    public String getName() {
        return "Leaper";
    }

    @Override
    public void onSpawn(Zombie zombie, CreatureSpawnEvent e) {
        incrAttribute(zombie, Attribute.GENERIC_JUMP_STRENGTH, 1.3);
        incrAttribute(zombie, Attribute.GENERIC_FALL_DAMAGE_MULTIPLIER, -1);
        incrAttribute(zombie, Attribute.GENERIC_FOLLOW_RANGE, 8);
    }

    @Override
    public void onAttack(Zombie zombie, EntityDamageByEntityEvent e) {
        zombie.setVelocity(zombie.getVelocity().multiply(0.25));
    }

    @Override
    public void onDeath(Zombie zombie, EntityDeathEvent e) {
        zombie.setVelocity(zombie.getVelocity().setY(3));
    }
}
