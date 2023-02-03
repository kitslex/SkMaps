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

package me.wyvern.util;

import org.bukkit.ChatColor;

import java.awt.*;

public class ColorRGBA {

    public int r, g, b, a;

    public ColorRGBA(int r, int g, int b, int a) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
    }

    public ColorRGBA(int r, int g, int b) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = 255;
    }

    public ColorRGBA(int rgb) {
        this.r = (rgb >> 16) & 0xFF;
        this.g = (rgb >> 8) & 0xFF;
        this.b = (rgb) & 0xFF;
        this.a = 255;
    }

    public ColorRGBA(String hex) {
        this.r = Integer.valueOf(hex.substring(0, 2), 16);
        this.g = Integer.valueOf(hex.substring(2, 4), 16);
        this.b = Integer.valueOf(hex.substring(4, 6), 16);
        this.a = 255;
    }

    public ColorRGBA(org.bukkit.Color color) {
        this.r = color.getRed();
        this.g = color.getGreen();
        this.b = color.getBlue();
        this.a = 255;
    }


    public int getR() {
        return r;
    }

    public void setR(int r) {
        this.r = r;
    }

    public int getG() {
        return g;
    }

    public void setG(int g) {
        this.g = g;
    }

    public int getB() {
        return b;
    }

    public void setB(int b) {
        this.b = b;
    }

    public int getA() {
        return a;
    }

    public void setA(int a) {
        this.a = a;
    }

    public Color toColor() {
        return new Color(r, g, b, a);
    }

    @Override
    public String toString() {
        return "ColorRGBA{" +
                "r=" + r +
                ", g=" + g +
                ", b=" + b +
                ", a=" + a +
                '}';
    }

    public static String colorize(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }
}
