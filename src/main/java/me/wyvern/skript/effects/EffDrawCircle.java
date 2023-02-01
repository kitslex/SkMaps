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
import me.wyvern.map.Map;
import me.wyvern.map.PixelLoc;
import me.wyvern.util.Color;
import org.bukkit.entity.SkeletonHorse;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;

public class EffDrawCircle extends Effect {

    static {
        Skript.registerEffect(EffDrawCircle.class, "draw circle at %number%,[ ]%number% with radius %number% with [color|colour] %color% on [map] %string%");
    }

    private Expression<Number> x;
    private Expression<Number> y;
    private Expression<Number> radius;
    private Expression<ColorRGB> color;
    private Expression<String> map;

    @Override
    protected void execute(Event e) {
        int x = this.x.getSingle(e).intValue();
        int y = this.y.getSingle(e).intValue();
        int radius = this.radius.getSingle(e).intValue();
        ColorRGB color = this.color.getSingle(e);
        String mapName = this.map.getSingle(e);
        Map map = SkMaps.getInstance().getMapManager().getMap(mapName);
        if (x == 0 || y == 0 || radius == 0 || color == null || map == null) return;
        map.drawCircle(new PixelLoc(x, y), radius, color);
    }

    @Override
    public @NotNull String toString(Event e, boolean debug) {
        return "draw circle at " + x.toString(e, debug) + ", " + y.toString(e, debug) + " with radius " + radius.toString(e, debug) + " with color " + color.toString(e, debug) + " on " + map.toString(e, debug);
    }

    @SuppressWarnings("all")
    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        x = (Expression<Number>) exprs[0];
        y = (Expression<Number>) exprs[1];
        radius = (Expression<Number>) exprs[2];
        color = (Expression<ColorRGB>) exprs[3];
        map = (Expression<String>) exprs[4];
        return true;
    }
}
