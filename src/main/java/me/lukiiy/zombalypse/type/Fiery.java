package me.lukiiy.zombalypse.type;

import me.lukiiy.zombalypse.CustomType;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Zombie;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Random;

public class Fiery implements CustomType {
    private final Random random;

    public Fiery(Random random) {
        this.random = random;
    }

    @Override
    public String getId() {
        return "fiery";
    }

    @Override
    public String getName() {
        return "Fiery";
    }

    @Override
    public void onSpawn(Zombie zombie, CreatureSpawnEvent e) {
        if (zombie.getEquipment() != null) {
            zombie.getEquipment().setHelmet(new ItemStack(Material.FLINT_AND_STEEL), false);
            zombie.getEquipment().setHelmetDropChance(0);
        }
        zombie.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, PotionEffect.INFINITE_DURATION, 1, false, false));
        zombie.setVisualFire(true);
    }

    @Override
    public void onAttack(Zombie zombie, EntityDamageByEntityEvent e) {
        e.getEntity().setFireTicks(20 + 30 * random.nextInt(2));
        zombie.getWorld().playSound(zombie.getLocation(), Sound.ENTITY_BLAZE_AMBIENT, 0.25f, 1);
        zombie.getWorld().spawnParticle(Particle.FLAME, e.getEntity().getLocation(), 0,2,0, 0.5);
    }
}
