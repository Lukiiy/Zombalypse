package me.lukiiy.zombalypse.type;

import me.lukiiy.zombalypse.CustomType;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Zombie;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

public class Robber implements CustomType {
    private final Random random;

    public Robber(Random random) {
        this.random = random;
    }

    @Override
    public String getId() {return "robber";}

    @Override
    public String getName() {return "Robber";}

    @Override
    public void onSpawn(Zombie zombie, CreatureSpawnEvent e) {
        if (zombie.getEquipment() == null) return;

        zombie.getEquipment().setHelmet(new ItemStack(Material.TINTED_GLASS), false);
        zombie.getEquipment().setHelmetDropChance(0);

        zombie.setCanPickupItems(false);
    }

    @Override
    public void onAttack(Zombie zombie, EntityDamageByEntityEvent e) {
        if (!(e.getEntity() instanceof LivingEntity livingEntity)) return;
        EntityEquipment lEquip = livingEntity.getEquipment();

        if (lEquip == null) return;

        ItemStack item = lEquip.getItemInMainHand();
        lEquip.setItemInMainHand(null);

        if (item.getType() == Material.AIR) {
            item = lEquip.getItemInOffHand();
            lEquip.setItemInOffHand(null);
        }
        if (item.getType() == Material.AIR) return;

        Location location = e.getEntity().getLocation();
        World world = e.getEntity().getWorld();

        if (zombie.getEquipment().getItemInMainHand().getType() == Material.AIR && random.nextInt(24) == 0) {
            zombie.getEquipment().setItemInMainHand(item);
        } else {
            world.dropItemNaturally(location, item);
        }

        world.playSound(location, Sound.ENTITY_CHICKEN_EGG, .75f, 1);
    }
}
