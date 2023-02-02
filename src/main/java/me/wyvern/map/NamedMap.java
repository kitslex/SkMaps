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

import me.wyvern.SkMaps;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.MapMeta;
import org.bukkit.map.MapView;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;

public class NamedMap extends Map implements Serializable {

    private final String mapName;
    private int mapId;


    public NamedMap(String mapName) {
        this.mapName = mapName;
    }

    public String getMapName() {
        return mapName;
    }

    public int getMapId() {
        return mapId;
    }

    public void setMapId(int mapId) {
        this.mapId = mapId;
    }


    public ItemStack buildItem() {
        ItemStack item = new ItemStack(Material.FILLED_MAP);
        ItemMeta stackMeta = item.getItemMeta();

        if (stackMeta == null) return null;

        MapMeta mapMeta = (MapMeta) stackMeta;
        if (!mapMeta.hasMapView()) {
            MapView mapView = Bukkit.getMap(mapId);
            mapMeta.setMapView(mapView);
        }
        MapView mapView = mapMeta.getMapView();
        if (mapView == null) return null;
        mapView.setLocked(false);
        mapMeta.setMapView(mapView);

        item.setItemMeta(mapMeta);

        return item;
    }

    @Override
    public int hashCode() {
        return mapName.hashCode();
    }


    public void save() {
        File file = new File(SkMaps.getInstance().getDataFolder(), "maps/" + mapName + ".map");
        if (!file.getParentFile().exists()) file.getParentFile().mkdirs();
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            MapManager.saveMap(this, file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "NamedMap{" +
                "mapName='" + mapName + '\'' +
                ", mapId=" + mapId +
                '}';
    }
}
