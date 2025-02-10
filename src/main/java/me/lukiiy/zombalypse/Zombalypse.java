package me.lukiiy.zombalypse;

import me.lukiiy.zombalypse.cmd.Reload;
import me.lukiiy.zombalypse.cmd.TypeSpawn;
import me.lukiiy.zombalypse.type.*;
import me.lukiiy.zombalypse.type.Explosive;
import me.lukiiy.zombalypse.type.Giant;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.*;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public final class Zombalypse extends JavaPlugin {
    private FileConfiguration config;
    public static NamespacedKey key;

    private List<CustomType> types = List.of();
    private Random rng = new Random();

    @Override
    public void onEnable() {
        setupConfig();

        getCommand("zombalypse").setExecutor(new Reload());
        getCommand("zombspawn").setExecutor(new TypeSpawn(this));

        key = new NamespacedKey(this, "t");
        types = new ArrayList<>();
        getServer().getPluginManager().registerEvents(new Listen(rng, this), this);

        registerType(new Default());
        registerType(new Explosive());
        registerType(new Fiery(rng));
        registerType(new Flash());
        registerType(new Giant());
        registerType(new Hopper());
        registerType(new Infected());
        registerType(new Multiplier());
        registerType(new Ninja(rng));
        registerType(new Pillar());
        registerType(new Puller());
        registerType(new Robber(rng));
        registerType(new Latcher());
        registerType(new Soul(rng));
        registerType(new Tank());
        registerType(new Thrower(rng));
    }

    public static Zombalypse getInstance() {
        return JavaPlugin.getPlugin(Zombalypse.class);
    }

    // Config

    public void setupConfig() {
        saveDefaultConfig();
        config = getConfig();
        config.options().copyDefaults(true);
        saveConfig();
    }

    public boolean getBool(String path) {
        return config.getBoolean(path, false);
    }

    // API

    public void registerType(CustomType type) {
        types.add(type);
    }

    public List<CustomType> getTypes() {
        return Collections.unmodifiableList(types);
    }

    public CustomType getType(Zombie zombie) {
        String typeKey = zombie.getPersistentDataContainer().get(Zombalypse.key, PersistentDataType.STRING);
        return typeKey != null ? getType(typeKey) : null;
    }

    public CustomType getType(String id) {
        return types.stream().filter(it -> it.getId().equals(id)).findFirst().orElse(null);
    }

    public CustomType getRandomType() {
        return types.get(rng.nextInt(types.size()));
    }
}
