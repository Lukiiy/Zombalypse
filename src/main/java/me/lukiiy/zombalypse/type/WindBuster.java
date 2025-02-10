package me.lukiiy.zombalypse.type;

import me.lukiiy.zombalypse.CustomType;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.WindCharge;
import org.bukkit.entity.Zombie;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

public class WindBuster implements CustomType {
    @Override
    public String getId() {return "";}

    @Override
    public String getName() {return "";}

    @Override
    public void onSpawn(Zombie zombie, CreatureSpawnEvent e) {
        zombie.addPotionEffect(new PotionEffect(PotionEffectType.WIND_CHARGED, PotionEffect.INFINITE_DURATION, 3, false, false));
    }

    @Override
    public void whenAttacked(Zombie zombie, EntityDamageByEntityEvent e) {
        zombie.getWorld().spawn(zombie.getLocation().add(0, 0.25, 0), WindCharge.class, w -> w.getVelocity().setY(-1));
    }

    @Override
    public void onAttack(Zombie zombie, EntityDamageByEntityEvent e) {
        zombie.playHurtAnimation(2);
        if (e.getEntity() instanceof LivingEntity l) l.launchProjectile(WindCharge.class, new Vector(0,-1, 0));
        zombie.getWorld().playSound(zombie.getLocation(), Sound.ENTITY_BREEZE_DEFLECT, 1, 1);
    }
}
