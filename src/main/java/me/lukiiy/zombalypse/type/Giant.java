package me.lukiiy.zombalypse.type;

import me.lukiiy.zombalypse.CustomType;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Zombie;
import org.bukkit.event.entity.CreatureSpawnEvent;

public class Giant implements CustomType {
    @Override
    public String getId() {
        return "giant";
    }

    @Override
    public String getName() {
        return "Giant";
    }

    @Override
    public void onSpawn(Zombie zombie, CreatureSpawnEvent e) {
        if (!zombie.getLocation().add(0,8,0).getBlock().getType().isAir()) {
            e.setCancelled(true);
            return;
        }

        zombie.setAdult();
        zombie.setRemoveWhenFarAway(true);

        incrAttribute(zombie, Attribute.GENERIC_SCALE, 2);
        incrAttribute(zombie, Attribute.GENERIC_JUMP_STRENGTH, .2);
        incrAttribute(zombie, Attribute.GENERIC_BURNING_TIME, -.2);
        incrAttribute(zombie, Attribute.GENERIC_ATTACK_DAMAGE, 5);
        incrAttribute(zombie, Attribute.GENERIC_ATTACK_KNOCKBACK, 1.5);
        incrAttribute(zombie, Attribute.GENERIC_ARMOR, 0.75);
        incrAttribute(zombie, Attribute.GENERIC_FOLLOW_RANGE, 16);
        incrAttribute(zombie, Attribute.GENERIC_SAFE_FALL_DISTANCE, 6);
        incrAttribute(zombie, Attribute.GENERIC_STEP_HEIGHT, 2);
        incrAttribute(zombie, Attribute.GENERIC_WATER_MOVEMENT_EFFICIENCY, 1);
    }
}
