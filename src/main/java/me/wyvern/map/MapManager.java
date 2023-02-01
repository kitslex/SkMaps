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

package me.wyvern.map;

import ch.njol.skript.Skript;
import me.wyvern.SkMaps;
import me.wyvern.util.Color;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MapManager {

    private final HashMap<String, NamedMap> maps = new HashMap<>();

    @SuppressWarnings("all")
    public void addMap(NamedMap map) {
        String debugLevel = SkMaps.getInstance().getDebugLevel();
        int level = SkMaps.getDebugLevel(debugLevel);
        if (maps.containsKey(map.getMapName())) {
            switch(level) {
                case 0:
                    return;
                case 1:
                    Skript.warning(Color.colorize(SkMaps.getInstance().getPrefix() + " &cMap with name " + map.getMapName() + " already exists!"));
                    return;
                case 2:
                case 3:
                    Stream<Player> players = (Stream<Player>) Bukkit.getOnlinePlayers().stream();
                    Stream<Player> playersWithPermission = players.filter(player -> player.hasPermission("skhoneybee.map.admin"));
                    String prefix = SkMaps.getInstance().getAdminPrefix();
                    String message = Color.colorize(prefix + " &cA map with the name &e" + map.getMapName() + " &calready exists!");
                    playersWithPermission.forEach(player -> player.sendMessage(message));

                    Skript.error("A map with the name " + map.getMapName() + " already exists!");
                    return;
            }
            return;
        }
        maps.put(map.getMapName(), map);
    }

    public void removeMap(NamedMap map) {
        maps.remove(map.getMapName());
    }

    public void removeMap(String mapName) {
        maps.remove(mapName);
    }

    public NamedMap getMap(String mapName) {
        return maps.get(mapName);
    }

    public List<NamedMap> getMaps() {
        return maps.values().stream().collect(Collectors.toList());
    }

    public List<String> getMapNames() {
        return maps.keySet().stream().collect(Collectors.toList());
    }

    public boolean mapExists(String mapName) {
        return maps.containsKey(mapName);
    }

    public boolean mapExists(NamedMap map) {
        return maps.containsValue(map);
    }

    public void clearMaps() {
        maps.clear();
    }

    public void saveMaps() {
        maps.values().forEach(NamedMap::save);
    }

    public static void saveMap(NamedMap map, File file) throws IOException {
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        config.set("mapName", map.getMapName());
        config.set("mapId", map.getMapId());
        config.set("mapPixels", map.getPixels());

        config.save(file);
    }

    public void loadMaps() {
        File folder = new File(SkMaps.getInstance().getDataFolder(), "maps");
        if (!folder.exists()) {
            folder.mkdirs();
        }
        File[] files = folder.listFiles();
        if (files == null) {
            return;
        }
        for (File file : files) {
            YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
            String mapName = config.getString("mapName");
            int mapId = config.getInt("mapId");
            MapPixel[][] pixels = (MapPixel[][]) config.get("mapPixels");
            NamedMap map = new NamedMap(mapName, mapId);
            map.setPixels(pixels);
            addMap(map);
        }
    }





}
