package me.lukiiy.zombalypse;

import org.bukkit.entity.Zombie;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;

public interface CustomType {
    String getId();
    String getName();

    default void onSpawn(Zombie zombie, CreatureSpawnEvent e) {return;}
    default void onDamage(Zombie zombie, EntityDamageEvent e) {return;}
    default void whenAttacked(Zombie zombie, EntityDamageByEntityEvent e) {return;}
    default void onAttack(Zombie zombie, EntityDamageByEntityEvent e) {return;}
    default void onDeath(Zombie zombie, EntityDeathEvent e) {return;}
}
