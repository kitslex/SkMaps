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
import org.bukkit.event.Event;

import javax.annotation.Nullable;

public class EffBezierCurve extends Effect {
    static {
        Skript.registerEffect(EffBezierCurve.class, "draw bezier curve (from|between) %number%,[ ]%number% [to] %number%,[ ]%number% [with] control points %number%,[ ]%number% [and] %number%,[ ]%number% (on|of) [map] %string% with color %color%");
    }

    private Expression<Number> x1, y1, x2, y2, x3, y3, x4, y4;
    private Expression<String> map;
    private Expression<ColorRGB> color;

    private PixelLoc p1, p2;
    private PixelLoc control1, control2;

    @Override
    protected void execute(Event e) {
        setPoints(e);
        String mapName = map.getSingle(e);
        ColorRGB color = this.color.getSingle(e);
        if (mapName == null || color == null) {
            Skript.warning("Map name or color is null!");
            return;
        }

        NamedMap namedMap = SkMaps.getInstance().getMapManager().getMap(mapName);
        if (namedMap == null) {
            Skript.warning("Map " + mapName + " is null!");
            return;
        }

        namedMap.bezierCurve(p1, p2, control1, control2, color);
    }

    public void setPoints(Event e) {
        p1 = new PixelLoc(x1.getSingle(e).intValue(), y1.getSingle(e).intValue());
        p2 = new PixelLoc(x2.getSingle(e).intValue(), y2.getSingle(e).intValue());
        control1 = new PixelLoc(x3.getSingle(e).intValue(), y3.getSingle(e).intValue());
        control2 = new PixelLoc(x4.getSingle(e).intValue(), y4.getSingle(e).intValue());
    }

    @Override
    public String toString(@Nullable Event e, boolean debug) {
        return "draw bezier curve from " + x1.toString(e, debug) + ", " + y1.toString(e, debug) + " to " + x2.toString(e, debug) + ", " + y2.toString(e, debug) + " with control points " + x3.toString(e, debug) + ", " + y3.toString(e, debug) + " and " + x4.toString(e, debug) + ", " + y4.toString(e, debug) + " on map " + map.toString(e, debug) + " with color " + color.toString(e, debug);
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        x1 = (Expression<Number>) exprs[0];
        y1 = (Expression<Number>) exprs[1];
        x2 = (Expression<Number>) exprs[2];
        y2 = (Expression<Number>) exprs[3];
        x3 = (Expression<Number>) exprs[4];
        y3 = (Expression<Number>) exprs[5];
        x4 = (Expression<Number>) exprs[6];
        y4 = (Expression<Number>) exprs[7];
        map = (Expression<String>) exprs[8];
        color = (Expression<ColorRGB>) exprs[9];
        return true;
    }
}
