/*
 * Copyright (c) 2023. Wyvern
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package me.wyvern;

import ch.njol.skript.Skript;
import ch.njol.skript.SkriptAddon;
import me.wyvern.command.CMDPrintContents;
import me.wyvern.event.Events;
import me.wyvern.map.MapManager;
import me.wyvern.util.Color;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

public final class SkMaps extends JavaPlugin {

    private static SkMaps instance;

    private SkriptAddon skriptAddon;
    private MapManager mapManager;


    private String prefix;
    private String adminPrefix;
    private String debugLevel;
    private String adminDebugLevel;
    @Override
    public void onEnable() {
        instance = this;
        loadConfig();
        long start = System.currentTimeMillis();
        mapManager = new MapManager();
        mapManager.loadMaps();

        Bukkit.getPluginManager().registerEvents(new Events(), this);
        Bukkit.getPluginCommand("printm").setExecutor(new CMDPrintContents());

        skriptAddon = Skript.registerAddon(this);

        try {
            skriptAddon.loadClasses("me.wyvern", "skript");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        long end = System.currentTimeMillis();
        getLogger().info(Color.colorize(prefix + " &aSuccessfully enabled! Took &e" + (end - start) + "ms&a!"));
    }



    public void loadConfig() {
        getConfig().options().copyDefaults();
        saveDefaultConfig();
        prefix = getConfig().getString("prefix");
        adminPrefix = getConfig().getString("admin-prefix");
        debugLevel = getConfig().getString("debug-level");
        adminDebugLevel = getConfig().getString("admin-debug-level");
    }

    public void reloadLocalConfig() {
        reloadConfig();
        prefix = getConfig().getString("prefix");
        adminPrefix = getConfig().getString("admin-prefix");
        debugLevel = getConfig().getString("debug-level");
        adminDebugLevel = getConfig().getString("admin-debug-level");
    }



    @Override
    public void onDisable() {
        mapManager.saveMaps();
    }


    public static SkMaps getInstance() {
        return instance;
    }

    public String getPrefix() {
        return prefix;
    }

    public String getAdminPrefix() {
        return adminPrefix;
    }

    public String getDebugLevel() {
        return debugLevel;
    }

    public String getAdminDebugLevel() {
        return adminDebugLevel;
    }

    public static int getDebugLevel(String level) {
        switch (level) {
            case "small":
                return 1;
            case "normal":
                return 2;
            case "verbose":
                return 3;
            default:
                return 0;
        }
    }

    public MapManager getMapManager() {
        return mapManager;
    }
}
