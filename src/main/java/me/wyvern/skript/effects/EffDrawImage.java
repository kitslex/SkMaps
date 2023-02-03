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
import me.wyvern.map.PixelLoc;
import org.bukkit.event.Event;

import javax.annotation.Nullable;
import java.awt.image.BufferedImage;

public class EffDrawImage extends Effect {

    static {
        Skript.registerEffect(EffDrawImage.class, "draw image %imagefile% at %pixelloc% on [the] map [named|with name] %string%");
    }

    private Expression<BufferedImage> image;
    private Expression<PixelLoc> loc;
    private Expression<String> map;
    @Override
    protected void execute(Event e) {
        BufferedImage image = this.image.getSingle(e);
        PixelLoc loc = this.loc.getSingle(e);
        String mapName = this.map.getSingle(e);
        if (image == null || loc == null || mapName == null) return;
        NamedMap map = SkMaps.getInstance().getMapManager().getMap(mapName);
        if (map == null) {
            Skript.warning("Map named " + mapName + " does not exist!");
            return;
        }

        map.drawImage(image, loc);
    }

    @Override
    public String toString(@Nullable Event e, boolean debug) {
        return null;
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        this.image = (Expression<BufferedImage>) exprs[0];
        this.loc = (Expression<PixelLoc>) exprs[1];
        this.map = (Expression<String>) exprs[2];
        return true;
    }
}
