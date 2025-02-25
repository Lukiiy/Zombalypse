package me.lukiiy.zombalypse.type;

import me.lukiiy.zombalypse.CustomType;
import me.lukiiy.zombalypse.Zombalypse;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Zombie;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.util.Vector;

import java.util.Random;

public class Thrower implements CustomType {
    private final Random random;

    public Thrower(Random random) {
        this.random = random;
    }

    @Override
    public String getId() {
        return "thrower";
    }

    @Override
    public String getName() {
        return "Flinger";
    }

    @Override
    public void onAttack(Zombie zombie, EntityDamageByEntityEvent e) { // TODO
        if (!(e.getEntity() instanceof LivingEntity l)) return;

        zombie.addPassenger(l);
        zombie.getWorld().playSound(zombie.getLocation(), Sound.ENTITY_HORSE_SADDLE, 1, 1);

        Bukkit.getScheduler().runTaskLater(Zombalypse.getInstance(), () -> {
            if (!zombie.getPassengers().isEmpty() && zombie.getPassengers().getFirst() == l) {
                zombie.removePassenger(l);
                Bukkit.getScheduler().runTaskLater(Zombalypse.getInstance(), () -> {
                    Vector d = zombie.getLocation().getDirection().normalize();
                    Vector v = d.multiply(1 + random.nextDouble(1.25)).setY(1 + random.nextDouble(0.75));

                    zombie.getWorld().playSound(zombie.getLocation(), Sound.BLOCK_PISTON_EXTEND, .75f, 1);

                    l.setVelocity(v);
                }, 1L);
            }
        }, 8L);
    }

    @Override
    public void onDeath(Zombie zombie, EntityDeathEvent e) {
        zombie.eject();
    }
}
