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
import me.wyvern.util.ColorRGBA;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.awt.*;

public class EffDrawText extends Effect {
    static {
        Skript.registerEffect(EffDrawText.class, "draw text %string% at %pixelloc% [with font %-string%] [with size %-number%] [with color|colour] %colorrgba% on [map] [named|with name] %string%");
    }

    private Expression<String> text;
    private Expression<PixelLoc> pixelLoc;
    private Expression<String> font;
    private Expression<Number> size;
    private Expression<ColorRGBA> color;
    private Expression<String> map;


    @Override
    protected void execute(@NotNull Event e) {
        String text = this.text.getSingle(e);
        String font;
        int size;
        if (this.font == null) {
            font = "Arial";
        } else {
            font = this.font.getSingle(e);
        }
          if (this.size == null) {
                size = 12;
          } else {
                size = this.size.getSingle(e).intValue();
          }
        ColorRGBA color = this.color.getSingle(e);
        String mapName = this.map.getSingle(e);
        if (text == null) {
            Skript.error("Text is null");
        }
        if (color == null) {
            color = new ColorRGBA(0, 0, 0, 255);
        }
        if (mapName == null) {
            Skript.error("Map name is null!");
            return;
        }
        Font drawFont = new Font(font, Font.PLAIN, size);
        MapManager manager = SkMaps.getInstance().getMapManager();

        NamedMap map = manager.getMap(mapName);
        map.drawText(text, pixelLoc.getSingle(e), color.toColor(), drawFont);
    }

    @Override
    public String toString(@Nullable Event e, boolean debug) {
        return null;
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        this.text = (Expression<String>) exprs[0];
        this.pixelLoc = (Expression<PixelLoc>) exprs[1];
        this.font = (Expression<String>) exprs[2];
        this.size = (Expression<Number>) exprs[3];
        this.color = (Expression<ColorRGBA>) exprs[4];
        this.map = (Expression<String>) exprs[5];
        return true;
    }
}

