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

package me.wyvern.skript.events;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Literal;
import ch.njol.skript.lang.SkriptEvent;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.registrations.EventValues;
import ch.njol.skript.util.Getter;
import me.wyvern.bukkitevent.MapClickEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class EvtMapClickEvent extends SkriptEvent {

    static {
        Skript.registerEvent("Map Click", EvtMapClickEvent.class, MapClickEvent.class, "[player] [x] [y] map click");
    }

    Literal<Number> x;
    Literal<Number> y;
    Literal<Player> player;
    @Override
    public boolean init(Literal<?> @NotNull [] args, int matchedPattern, SkriptParser.@NotNull ParseResult parseResult) {
        x = (Literal<Number>) args[0];
        y = (Literal<Number>) args[1];
        player = (Literal<Player>) args[2];
        return true;
    }

    @Override
    public boolean check(Event e) {
        if (x == null || y == null || player == null) return false;
        MapClickEvent event = (MapClickEvent) e;
        if (x.getSingle() != null && x.getSingle().doubleValue() != event.getX()) return false;
        if (y.getSingle() != null && y.getSingle().doubleValue() != event.getY()) return false;
        return player.getSingle() == null || player.getSingle() == event.getPlayer();
    }

    @Override
    public @NotNull String toString(@Nullable Event e, boolean debug) {
        return "Map Click Event";
    }
}
