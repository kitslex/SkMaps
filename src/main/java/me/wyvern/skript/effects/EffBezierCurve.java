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

import javax.annotation.Nullable;

public class EffBezierCurve extends Effect {
    static {
        Skript.registerEffect(EffBezierCurve.class, "draw bezier curve (from|between) %pixelloc% [to] %pixelloc% [with] control points %pixelloc% [and] %pixelloc% (on|of) [map] %string% with color %colorrgba%");
    }

    private Expression<PixelLoc> start;
    private Expression<PixelLoc> end;
    private Expression<PixelLoc> control1;
    private Expression<PixelLoc> control2;
    private Expression<String> map;
    private Expression<ColorRGBA> color;



    @Override
    protected void execute(Event e) {
        String mapName = map.getSingle(e);
        ColorRGBA color = this.color.getSingle(e);
        if (mapName == null || color == null) {
            Skript.warning("Map name or color is null!");
            return;
        }

        NamedMap namedMap = SkMaps.getInstance().getMapManager().getMap(mapName);
        if (namedMap == null) {
            Skript.warning("Map " + mapName + " is null!");
            return;
        }

        namedMap.bezierCurve(start.getSingle(e), end.getSingle(e), control1.getSingle(e), control2.getSingle(e), color.toColor());
    }



    @Override
    public String toString(@Nullable Event e, boolean debug) {
        return "draw bezier curve from " + start.toString(e, debug) + " to " + end.toString(e, debug) + " with control points " + control1.toString(e, debug) + " and " + control2.toString(e, debug) + " on map " + map.toString(e, debug) + " with color " + color.toString(e, debug);
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        start = (Expression<PixelLoc>) exprs[0];
        end = (Expression<PixelLoc>) exprs[1];
        control1 = (Expression<PixelLoc>) exprs[2];
        control2 = (Expression<PixelLoc>) exprs[3];
        map = (Expression<String>) exprs[4];
        color = (Expression<ColorRGBA>) exprs[5];
        return true;
    }
}
