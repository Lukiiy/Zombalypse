package me.lukiiy.zombalypse.type;

import me.lukiiy.zombalypse.CustomType;
import org.bukkit.*;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.OminousItemSpawner;
import org.bukkit.entity.Zombie;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Soul implements CustomType {
    private static final List<ItemStack> soulItems = new ArrayList<>();
    final Random random;

    static {
        ItemStack pot = new ItemStack(Material.SPLASH_POTION);
        PotionMeta potMeta = (PotionMeta) pot.getItemMeta();
        if (potMeta != null) {potMeta.setBasePotionType(PotionType.HARMING);}
        pot.setItemMeta(potMeta);

        soulItems.add(pot);
        soulItems.add(new ItemStack(Material.FIRE_CHARGE));
        soulItems.add(new ItemStack(Material.ARROW));
    }

    public Soul(Random random) {
        this.random = random;
    }

    @Override
    public String getId() {
        return "soul";
    }

    @Override
    public String getName() {
        return "Soul";
    }

    @Override
    public void onSpawn(Zombie zombie, CreatureSpawnEvent e) {
        if (zombie.getEquipment() != null) {
            zombie.getEquipment().setHelmet(new ItemStack(Material.ZOMBIE_HEAD), false);
            if (zombie.getType() == EntityType.ZOMBIFIED_PIGLIN) zombie.getEquipment().setHelmet(new ItemStack(Material.PIGLIN_HEAD), false);
            zombie.getEquipment().setHelmetDropChance(0);
        }
        zombie.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, PotionEffect.INFINITE_DURATION, 1, false, false));
        zombie.setCollidable(false);
        zombie.setSilent(true);
    }

    @Override
    public void whenAttacked(Zombie zombie, EntityDamageByEntityEvent e) {
        Location dropperSpawn = e.getDamager().getLocation().add(random.nextDouble() * 1.5 - 0.75, 3 + random.nextInt(4), random.nextDouble() * 1.5 - 0.75);

        e.getDamager().getWorld().spawn(dropperSpawn, OminousItemSpawner.class, it -> {
            it.setItem(soulItems.get(random.nextInt(soulItems.size())));
            it.setSpawnItemAfterTicks(40);
        });
    }

    @Override
    public void onDeath(Zombie zombie, EntityDeathEvent e) {
        World w = zombie.getWorld();

        w.spawnParticle(Particle.LARGE_SMOKE, zombie.getLocation().add(0, 1, 0), 1); // TODO
        w.playSound(zombie.getLocation(), Sound.ENTITY_VEX_DEATH, 0.25f, 1);
    }
}
