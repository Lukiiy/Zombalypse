package me.lukiiy.zombalypse;

import org.bukkit.GameMode;
import org.bukkit.World;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityRemoveEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.EnumSet;
import java.util.List;
import java.util.Random;

public class Listen implements Listener {
    private final Random random;
    private final Zombalypse instance;

    private static final EnumSet<EntityType> REPLACEABLE = EnumSet.of(EntityType.SKELETON, EntityType.CREEPER, EntityType.SPIDER, EntityType.WITCH, EntityType.STRAY, EntityType.PILLAGER);

    public Listen(Random random, Zombalypse instance) {
        this.random = random;
        this.instance = instance;
    }

    @EventHandler
    public void spawn(CreatureSpawnEvent e) {
        Entity entity = e.getEntity();
        World world = entity.getWorld();

        if (instance.getBool("replaceHostiles") && REPLACEABLE.contains(entity.getType())) {
            world.spawnEntity(entity.getLocation(), EntityType.ZOMBIE);
            e.setCancelled(true);
            return;
        }

        if (instance.getBool("endSpawn") && world.getEnvironment() == World.Environment.THE_END && entity instanceof Enderman && random.nextInt(8) == 0) {
            world.spawnEntity(entity.getLocation(), EntityType.ZOMBIE);
            e.setCancelled(true);
            return;
        }

        if (entity instanceof Zombie zombie) {
            PersistentDataContainer data = zombie.getPersistentDataContainer();
            String typeKey = data.get(Zombalypse.key, PersistentDataType.STRING);

            CustomType type = (typeKey != null) ? instance.getType(typeKey) : instance.getRandomType();
            if (type == null) return;

            if (!data.has(Zombalypse.key)) data.set(Zombalypse.key, PersistentDataType.STRING, type.getId());

            if (instance.getBool("displayType") && !type.getName().isEmpty()) zombie.setCustomName(type.getName());

            type.onSpawn(zombie, e);

            if (instance.getBool("zombifiedPiglinAttack") && zombie instanceof PigZombie pigZombie) {
                new BukkitRunnable() {
                    @Override
                    public void run() { // TODO
                        if (pigZombie.isDead() || !pigZombie.isValid()) {
                            this.cancel();
                            return;
                        }

                        if (!pigZombie.getTarget().isDead() && pigZombie.getTarget().isValid()) return;

                        double closestDist = instance.getConfig().getInt("zombifiedPiglinRadius", 8) * 2;
                        Player target = null;

                        for (Player player : pigZombie.getWorld().getPlayers()) {
                            if (player.getGameMode() != GameMode.SURVIVAL && player.getGameMode() != GameMode.ADVENTURE) continue;

                            double distSquared = pigZombie.getLocation().distanceSquared(player.getLocation());
                            if (distSquared < closestDist) {
                                closestDist = distSquared;
                                target = player;
                            }
                        }

                        if (target != null) {
                            pigZombie.setAnger(2);
                            pigZombie.setTarget(target);
                        }
                    }
                }.runTaskTimer(instance, 20L, 160L);
            }
        }
    }

    @EventHandler(priority = EventPriority.LOW)
    public void dmg(EntityDamageByEntityEvent e) {
        if (e.getDamager() instanceof Zombie entity) {
            PersistentDataContainer data = entity.getPersistentDataContainer();
            String typeKey = data.get(Zombalypse.key, PersistentDataType.STRING);

            if (typeKey != null) {
                CustomType type = instance.getType(typeKey);
                if (type != null) type.onAttack(entity, e);
            }
        }


        if (e.getEntity() instanceof Zombie entity) {
            PersistentDataContainer data = entity.getPersistentDataContainer();
            String typeKey = data.get(Zombalypse.key, PersistentDataType.STRING);

            if (typeKey != null) {
                CustomType type = instance.getType(typeKey);
                if (type != null) type.whenAttacked(entity, e);
            }
        }
    }

    @EventHandler(priority = EventPriority.LOW)
    public void kill(EntityDeathEvent e) {
        if (!(e.getEntity() instanceof Zombie entity)) return;

        PersistentDataContainer data = entity.getPersistentDataContainer();
        String typeKey = data.get(Zombalypse.key, PersistentDataType.STRING);

        if (typeKey != null) {
            CustomType type = instance.getType(typeKey);
            if (type != null) type.onDeath(entity, e);
        }
    }
}
