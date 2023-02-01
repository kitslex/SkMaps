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

package me.wyvern.bukkitevent;

import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

/**
 * x,y subcoords of blockface;
 */
public class MapClickEvent extends Event {

    private double x, y;
    private int eventID;

    private static final HandlerList HANDLER_LIST = new HandlerList();
    private final Player player;
    private final ItemFrame itemFrame;

    private int pX, pY;
    public MapClickEvent(Player player, ItemFrame frame, int eventID, double x, double y) {
        this.player = player;
        this.itemFrame = frame;
        this.eventID = eventID;
        this.x = x;
        this.y = y;
        this.pX = (int) ((int) x*128D);
        this.pY = (int) ((int) y*128D);
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public int getEventID() {
        return eventID;
    }

    public Player getPlayer() {
        return player;
    }

    public int getPx() {
        return pX;
    }

    public int getPy() {
        return pY;
    }

    @NotNull
    @Override
    public HandlerList getHandlers() {
        return HANDLER_LIST;
    }
}
