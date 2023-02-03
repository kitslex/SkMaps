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
import me.wyvern.map.MapManager;
import me.wyvern.map.NamedMap;
import me.wyvern.map.PixelLoc;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.awt.*;

public class EffDrawText extends Effect {
    static {
        Skript.registerEffect(EffDrawText.class, "draw text %string% at %number%,[ ]%number% [with font %-string%] [with size %-number%] [with color|colour] %color% on [map] [named|with name] %string%");
    }

    private Expression<String> text;
    private Expression<Number> x;
    private Expression<Number> y;
    private Expression<String> font;
    private Expression<Number> size;
    private Expression<ColorRGB > color;
    private Expression<String> map;


    @Override
    protected void execute(@NotNull Event e) {
        String text = this.text.getSingle(e);
        int x = this.x.getSingle(e).intValue();
        int y = this.y.getSingle(e).intValue();
        String font = this.font.getSingle(e);
        int size = this.size.getSingle(e).intValue();
        ColorRGB color = this.color.getSingle(e);
        String mapName = this.map.getSingle(e);
        if (text == null || x == 0 || y == 0) {
            Skript.error("Text, x, or y is null!");
            return;
        }
        if (font == null) {
            font = "Arial";
        }
        if (size == 0) {
            size = 12;
        }
        if (color == null) {
            color = new ColorRGB(0, 0, 0);
        }
        if (mapName == null) {
            Skript.error("Map name is null!");
            return;
        }
        Font drawFont = new Font(font, Font.PLAIN, size);
        MapManager manager = SkMaps.getInstance().getMapManager();

        NamedMap map = manager.getMap(mapName);
        map.drawText(text, new PixelLoc(x, y), color, drawFont);
    }

    @Override
    public String toString(@Nullable Event e, boolean debug) {
        if (e == null) {
            return "draw text";
        }
        if (text == null || x == null || y == null) {
            return "draw text";
        }
        if (font ==  null || size == null) {
            return "draw text " + text.toString(e, debug) + " at " + x.toString(e, debug) + ", " + y.toString(e, debug);
        }
        return "draw text " + text.toString(e, debug) + " at " + x.toString(e, debug) + ", " + y.toString(e, debug) + " with font " + font.toString(e, debug) + " with size " + size.toString(e, debug) + " with color " + color.toString(e, debug) + " on map named " + map.toString(e, debug);
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        text = (Expression<String>) exprs[0];
        x = (Expression<Number>) exprs[1];
        y = (Expression<Number>) exprs[2];
        font = (Expression<String>) exprs[3];
        size = (Expression<Number>) exprs[4];
        color = (Expression<ColorRGB>) exprs[5];
        map = (Expression<String>) exprs[6];
        return true;
    }
}

