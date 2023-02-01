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

package me.wyvern.skript.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.wyvern.SkMaps;
import me.wyvern.map.NamedMap;
import me.wyvern.map.MapManager;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ItemFrame;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class EffRenderMap extends Effect {
    static {
        Skript.registerEffect(EffRenderMap.class, "render map [named] %string% at %location% facing %blockface%");
    }

    private Expression<String> name;
    private Expression<Location> location;
    private Expression<BlockFace> direction;

    @Override
    protected void execute(@NotNull Event event) {
        BlockFace blockFace = direction.getSingle(event);
        Location loc = location.getSingle(event);
        int debugLevel = SkMaps.getDebugLevel(SkMaps.getInstance().getDebugLevel());

        if (blockFace == null || loc == null) {
            if (debugLevel >= 1) {
                Skript.warning("BlockFace or Location is null!");
            }
            return;
        }

        Block block = loc.getBlock();
        if (block.getType() == Material.AIR) {
            if (debugLevel >= 1) {
                Skript.warning("Block is air!");
            }
            return;
        }

        String name = this.name.getSingle(event);

        MapManager mapManager = SkMaps.getInstance().getMapManager();
        if (!mapManager.mapExists(name)) {
            if (debugLevel >= 1) {
                Skript.warning("Map does not exist!");
            }
            return;
        }
        NamedMap namedMap = mapManager.getMap(name);

        ItemFrame entity = (ItemFrame) loc.getWorld().spawnEntity(loc, EntityType.ITEM_FRAME);
        entity.setFacingDirection(blockFace, true);
        entity.setItem(namedMap.buildItem());
    }

    @Override
    public @NotNull String toString(@Nullable Event event, boolean debug) {
        return "render map " + name.toString(event, debug) + " at " + location.toString(event, debug) + " facing " + direction.toString(event, debug);
    }

    @SuppressWarnings("all")
    @Override
    public boolean init(Expression<?>[] expressions, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parser) {
        this.name = (Expression<String>) expressions[0];
        this.location = (Expression<Location>) expressions[1];
        this.direction = (Expression<BlockFace>) expressions[2];
        return true;
    }
}
