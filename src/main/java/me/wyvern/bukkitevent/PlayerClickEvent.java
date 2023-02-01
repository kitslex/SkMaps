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

package me.wyvern.bukkitevent;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.MapMeta;
import org.bukkit.util.RayTraceResult;

public class PlayerClickEvent implements Listener {
    @EventHandler
    public void onBlockInteract(PlayerInteractEvent e) {
        RayTraceResult result = e.getPlayer().rayTraceBlocks(5);

        if (result == null) return;
        if (result.getHitEntity() == null) return;
        if (!(result.getHitEntity() instanceof ItemFrame)) return;

        ItemFrame frame = (ItemFrame) result.getHitEntity();
        ItemStack item = frame.getItem();
        if (!(item.getItemMeta() instanceof MapMeta)) return;
        Player player = e.getPlayer();

        double hitX = result.getHitPosition().getX();
        double hitY = result.getHitPosition().getY();

        double frameX = frame.getLocation().getX();
        double frameY = frame.getLocation().getY();

        double x = hitX - frameX;
        double y = hitY - frameY;

        MapClickEvent event = new MapClickEvent(player, frame, item.getDurability(), x, y);
        Bukkit.getPluginManager().callEvent(event);
    }
    @EventHandler
    public void onEntityInteract(PlayerInteractEntityEvent e) {
        if (!(e.getRightClicked() instanceof ItemFrame)) return;
        ItemFrame frame = (ItemFrame) e.getRightClicked();
        ItemStack item = frame.getItem();
        if (!(item.getItemMeta() instanceof MapMeta)) return;
        Player player = e.getPlayer();

        double hitX = e.getRightClicked().getLocation().getX();
        double hitY = e.getRightClicked().getLocation().getY();

        double frameX = frame.getLocation().getX();
        double frameY = frame.getLocation().getY();

        double x = hitX - frameX;
        double y = hitY - frameY;

        MapClickEvent event = new MapClickEvent(player, frame, item.getDurability(), x, y);
        Bukkit.getPluginManager().callEvent(event);
    }

}
