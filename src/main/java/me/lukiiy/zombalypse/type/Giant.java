package me.lukiiy.zombalypse.type;

import me.lukiiy.zombalypse.CustomType;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Zombie;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

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
        if (!zombie.getLocation().add(0,2,0).getBlock().getType().isAir()) {
            e.setCancelled(true);
            return;
        }
        zombie.setAdult();

        AttributeInstance s = zombie.getAttribute(Attribute.GENERIC_SCALE);
        if (s != null) s.setBaseValue(3);

        zombie.addPotionEffect(new PotionEffect(PotionEffectType.STRENGTH, PotionEffect.INFINITE_DURATION, 2, false, false));
    }
}
