package me.lukiiy.zombalypse.type;

import me.lukiiy.zombalypse.CustomType;
import org.bukkit.entity.Zombie;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Flash implements CustomType {
    @Override
    public String getId() {
        return "flash";
    }

    @Override
    public String getName() {
        return "Flash";
    }

    @Override
    public void onSpawn(Zombie zombie, CreatureSpawnEvent e) {
        zombie.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, PotionEffect.INFINITE_DURATION, 4, false, false));
    }
}
