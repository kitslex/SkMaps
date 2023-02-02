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
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.Objects;

public class EffDrawPixel extends Effect {
    static {
        Skript.registerEffect(EffDrawPixel.class, "draw pixel [at] %number%,[ ]%number% %string% with [color|colour] %color% on [map] [named|with name] %string%");
    }

    private Expression<Number> exprX;
    private Expression<Number> exprY;
    private Expression<String> exprMap;
    private Expression<ColorRGB> exprColor;

    @Override
    protected void execute(@NotNull Event e) {
        int debugLevel = SkMaps.getDebugLevel(SkMaps.getInstance().getDebugLevel());
        if (exprX == null || exprY == null || exprMap == null || exprColor == null) {
            if (debugLevel >= 1) {
                Skript.warning("One of the expressions is null!");
            }
            return;
        }
        int x = Objects.requireNonNull(exprX.getSingle(e)).intValue();
        int y = Objects.requireNonNull(exprY.getSingle(e)).intValue();
        String mapName = exprMap.getSingle(e);
        ColorRGB color = exprColor.getSingle(e);
        if (mapName == null || color == null) {
            if (debugLevel >= 1) {
                Skript.warning("One of the expressions is null!");
            }
            return;
        }
        NamedMap map = SkMaps.getInstance().getMapManager().getMap(mapName);
        if (map == null) {
            if (debugLevel >= 1) {
                Skript.warning("Map " + mapName + " does not exist!");
            }
            return;
        }
        map.setPixel(x, y, color);
    }

    @Override
    public @NotNull String toString(@Nullable Event e, boolean debug) {
        return "draw pixel at " + exprX.toString(e, debug) + "," + exprY.toString(e, debug) + " of map named " + exprMap.toString(e, debug) + " with color " + exprColor.toString(e, debug);
    }

    @SuppressWarnings("all")
    @Override
    public boolean init(Expression<?> @NotNull [] exprs, int matchedPattern, @NotNull Kleenean isDelayed, SkriptParser.@NotNull ParseResult parseResult) {
        exprX = (Expression<Number>) exprs[0];
        exprY = (Expression<Number>) exprs[1];
        exprMap = (Expression<String>) exprs[2];
        exprColor = (Expression<ColorRGB>) exprs[3];
        return true;
    }
}
