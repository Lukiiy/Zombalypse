package me.lukiiy.zombalypse.type;

import me.lukiiy.zombalypse.CustomType;
import org.bukkit.entity.Zombie;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

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
        zombie.addPotionEffect(new PotionEffect(PotionEffectType.JUMP_BOOST, PotionEffect.INFINITE_DURATION, 4, false, false));
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
