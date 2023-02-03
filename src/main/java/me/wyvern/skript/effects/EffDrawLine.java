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
import ch.njol.skript.util.ColorRGB;
import ch.njol.util.Kleenean;
import me.wyvern.SkMaps;
import me.wyvern.map.NamedMap;
import me.wyvern.map.PixelLoc;
import me.wyvern.util.ColorRGBA;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class EffDrawLine extends Effect {
    static {
        Skript.registerEffect(EffDrawLine.class, "draw line (from|between) %pixelloc% [to] %pixelloc% with [color|colour] %colorrgba% on [map] [named|with name] %string%");
    }

    private Expression<PixelLoc> exprPixelLoc1;
    private Expression<PixelLoc> exprPixelLoc2;
    private Expression<ColorRGBA> color;
    private Expression<String> map;
    @Override
    protected void execute(@NotNull Event e) {
        int debugLevel = SkMaps.getDebugLevel(SkMaps.getInstance().getDebugLevel());
        if (map == null) {
            if (debugLevel >= 1) {
                Skript.warning("Map's name is null!");
            }
            return;
        }
        String mapName = map.getSingle(e);
        if (!SkMaps.getInstance().getMapManager().mapExists(mapName)) {
            if (debugLevel >= 1) {
                Skript.warning("Map " + mapName + " does not exist!");
            }
            return;
        }

        ColorRGBA color = this.color.getSingle(e);

        NamedMap namedMap = SkMaps.getInstance().getMapManager().getMap(mapName);
        assert color != null;
        namedMap.drawLine(exprPixelLoc1.getSingle(e), exprPixelLoc2.getSingle(e), color.toColor());
    }

    @Override
    public @NotNull String toString(@Nullable Event e, boolean debug) {
        return "draw line from " + exprPixelLoc1.toString(e, debug) + " to " + exprPixelLoc2.toString(e, debug) + " with color " + color.toString(e, debug) + " on map named " + map.toString(e, debug);
    }

    @SuppressWarnings("all")
    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        exprPixelLoc1 = (Expression<PixelLoc>) exprs[0];
        exprPixelLoc2 = (Expression<PixelLoc>) exprs[1];
        color = (Expression<ColorRGBA>) exprs[2];
        return true;
    }
}
