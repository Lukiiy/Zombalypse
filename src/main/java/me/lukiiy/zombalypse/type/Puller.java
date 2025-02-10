package me.lukiiy.zombalypse.type;

import me.lukiiy.zombalypse.CustomType;
import me.lukiiy.zombalypse.Zombalypse;
import org.bukkit.Bukkit;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Zombie;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

public class Puller implements CustomType {
    @Override
    public String getId() {
        return "puller";
    }

    @Override
    public String getName() {
        return "Puller";
    }

    @Override
    public void onAttack(Zombie zombie, EntityDamageByEntityEvent e) {
        Vector direction = e.getEntity().getLocation().toVector().subtract(zombie.getLocation().toVector());

        Bukkit.getScheduler().runTaskLater(Zombalypse.getInstance(), () -> {
            e.getEntity().setVelocity(direction.multiply(-.75));
            zombie.getWorld().playSound(zombie.getLocation(), Sound.ENTITY_SLIME_JUMP, 0.3f, 1);

            if (!(e.getEntity() instanceof LivingEntity l)) return;

            l.addPotionEffect(new PotionEffect(PotionEffectType.NAUSEA, 40, 1, false, false));
            l.addPotionEffect(new PotionEffect(PotionEffectType.SLOWNESS, 8, 2, false, false));
            zombie.getWorld().spawnParticle(Particle.DUST_PLUME, e.getEntity().getLocation(), 6, 0,0.5,0, 0.25);
        }, 5L);
    }
}
