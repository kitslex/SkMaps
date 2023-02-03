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

package me.wyvern.map;

import ch.njol.skript.Skript;
import ch.njol.skript.util.ColorRGB;
import me.wyvern.SkMaps;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.stream.Stream;

public class Map implements Serializable {

    public static final int MAP_WIDTH = 128;
    public static final int MAP_HEIGHT = 128;

    private MapPixel[][] pixels = new MapPixel[MAP_WIDTH][MAP_HEIGHT];

    public MapPixel[][] getPixels() {
        return pixels;
    }

    public void setPixels(MapPixel[][] pixels) {
        this.pixels = pixels;
    }

    public MapPixel getPixel(int x, int y) {
        return pixels[x][y];
    }

    public void setPixel(int x, int y, MapPixel pixel) {
        pixels[x][y] = pixel;
    }

    public void setPixel(int x, int y, Color color) {
        pixels[x][y] = new MapPixel(x, y, color);
    }

    public void setPixel(int x, int y, ColorRGB color) {
        org.bukkit.Color bukkitColor = color.asBukkitColor();
        Color awtColor = new Color(bukkitColor.getRed(), bukkitColor.getGreen(), bukkitColor.getBlue());
        pixels[x][y] = new MapPixel(x, y, awtColor);
    }


    public void setPixel(int x, int y, int r, int g, int b) {
        pixels[x][y] = new MapPixel(x, y, new Color(r, g, b));
    }

    public void fill(Color color) {
        Stream.iterate(0, n -> n + 1)
                .limit(MAP_WIDTH)
                .parallel()
                .forEach(x -> Stream.iterate(0, n -> n + 1)
                        .limit(MAP_HEIGHT)
                        .parallel()
                        .forEach(y -> setPixel(x, y, color)));
    }

    public void fill(ColorRGB color) {
        org.bukkit.Color bukkitColor = color.asBukkitColor();
        Color awtColor = new Color(bukkitColor.getRed(), bukkitColor.getGreen(), bukkitColor.getBlue());
        fill(awtColor);
    }

    public void fillWithColor(int r, int g, int b) {
        Stream.iterate(0, n -> n + 1)
                .limit(MAP_WIDTH)
                .parallel()
                .forEach(x -> Stream.iterate(0, n -> n + 1)
                        .limit(MAP_HEIGHT)
                        .parallel()
                        .forEach(y -> setPixel(x, y, r, g, b)));
    }

    public void fillRandomly() {
        Stream.iterate(0, n -> n + 1)
                .limit(MAP_WIDTH)
                .parallel()
                .forEach(x -> Stream.iterate(0, n -> n + 1)
                        .limit(MAP_HEIGHT)
                        .parallel()
                        .forEach(y -> setPixel(x, y, new Color((int) (Math.random() * 0x1000000)))));
    }

    public void fillSection(PixelLoc start, PixelLoc end, Color color) {
        int startX = start.getX();
        int startY = start.getY();
        int endX = end.getX();
        int endY = end.getY();
        if (startX > endX) {
            int temp = startX;
            startX = endX;
            endX = temp;
        }
        if (startY > endY) {
            int temp = startY;
            startY = endY;
            endY = temp;
        }
        int finalEndY = endY;
        int finalStartY = startY;
        Stream.iterate(startX, n -> n + 1)
                .limit(endX - startX)
                .parallel()
                .forEach(x -> Stream.iterate(finalStartY, n -> n + 1)
                        .limit(finalEndY - finalStartY)
                        .parallel()
                        .forEach(y -> setPixel(x, y, color)));
    }

    public void fillSection(PixelLoc start, PixelLoc end, ColorRGB color) {
        org.bukkit.Color bukkitColor = color.asBukkitColor();
        Color awtColor = new Color(bukkitColor.getRed(), bukkitColor.getGreen(), bukkitColor.getBlue());
        fillSection(start, end, awtColor);
    }


    public void drawCircle(PixelLoc center, int radius, Color color) {
        int x0 = center.getX();
        int y0 = center.getY();

        double dtr = Math.PI / 180.0;
        for(int x=0; x<=360; x++)
        {
            double radians = dtr * x;
            int x1 = (int)(x0 + Math.cos(radians) * radius);
            int y1 = (int)(y0 + Math.sin(radians) * radius);
            setPixel(x1, y1, color);
        }
    }

    public void drawCircle(PixelLoc center, int radius, ColorRGB color) {
        org.bukkit.Color bukkitColor = color.asBukkitColor();
        Color awtColor = new Color(bukkitColor.getRed(), bukkitColor.getGreen(), bukkitColor.getBlue());
        drawCircle(center, radius, awtColor);
    }

    public void drawText(String text, PixelLoc loc, Color color, Font font) {
        int x = loc.getX();
        int y = loc.getY();
        BufferedImage image = toBufferedImage();
        Graphics2D g2d = image.createGraphics();
        g2d.setFont(font);
        g2d.setColor(color);
        g2d.drawString(text, x, y);
        g2d.dispose();
        drawImage(image);
    }

    public void drawText(String text, PixelLoc loc, ColorRGB color, Font font) {
        org.bukkit.Color bukkitColor = color.asBukkitColor();
        Color awtColor = new Color(bukkitColor.getRed(), bukkitColor.getGreen(), bukkitColor.getBlue());
        drawText(text, loc, awtColor, font);
    }

    public BufferedImage toBufferedImage() {
        BufferedImage image = new BufferedImage(MAP_WIDTH, MAP_HEIGHT, BufferedImage.TYPE_INT_RGB);
        Stream.iterate(0, n -> n + 1)
                .limit(MAP_WIDTH)
                .parallel()
                .forEach(x -> Stream.iterate(0, n -> n + 1)
                        .limit(MAP_HEIGHT)
                        .parallel()
                        .forEach(y -> image.setRGB(x, y, getPixel(x, y).getColor().getRGB())));
        return image;
    }

    public void drawImage(BufferedImage image) {
        BufferedImage fImage;
        int debugLevel = SkMaps.getDebugLevel(SkMaps.getInstance().getDebugLevel());
        int imWidth = image.getWidth();
        int imHeight = image.getHeight();
        if (imWidth > MAP_WIDTH || imHeight > MAP_HEIGHT) {
            if (debugLevel >= 2) {
                Skript.warning("Image is too large to be drawn on the map. Resizing...");
                fImage = resize(image, MAP_WIDTH, MAP_HEIGHT);
            } else {
                fImage = image;
            }
        } else {
            fImage = image;
        }
        Stream.iterate(0, n -> n + 1)
                .limit(MAP_WIDTH)
                .parallel()
                .forEach(x -> Stream.iterate(0, n -> n + 1)
                        .limit(MAP_HEIGHT)
                        .parallel()
                        .forEach(y -> setPixel(x, y, new Color(fImage.getRGB(x, y)))));
    }

    public static BufferedImage resize(BufferedImage img, int newW, int newH) {
        // rescale the image
        Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
        BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);

        // draw the image into the BufferedImage
        Graphics2D g2d = dimg.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();

        return dimg;
    }

    public void drawLine(PixelLoc a, PixelLoc b, Color c) {
        int x0 = a.getX();
        int y0 = a.getY();
        int x1 = b.getX();
        int y1 = b.getY();
        int dx = Math.abs(x1 - x0);
        int dy = Math.abs(y1 - y0);
        int sx = x0 < x1 ? 1 : -1;
        int sy = y0 < y1 ? 1 : -1;
        int err = dx - dy;
        while (true) {
            setPixel(x0, y0, c);
            if (x0 == x1 && y0 == y1) break;
            int e2 = 2 * err;
            if (e2 > -dy) {
                err = err - dy;
                x0 = x0 + sx;
            }
            if (e2 < dx) {
                err = err + dx;
                y0 = y0 + sy;
            }
        }
    }

    public void drawLine(PixelLoc a, PixelLoc b, ColorRGB c) {
        org.bukkit.Color bukkitColor = c.asBukkitColor();
        Color awtColor = new Color(bukkitColor.getRed(), bukkitColor.getGreen(), bukkitColor.getBlue());
        drawLine(a, b, awtColor);
    }

    public void bezierCurve(PixelLoc a, PixelLoc b, PixelLoc c, PixelLoc d, Color color) {
        int x0 = a.getX();
        int y0 = a.getY();
        int x1 = b.getX();
        int y1 = b.getY();
        int x2 = c.getX();
        int y2 = c.getY();
        int x3 = d.getX();
        int y3 = d.getY();
        double t = 0;
        while (t <= 1) {
            double x = Math.pow(1 - t, 3) * x0 + 3 * t * Math.pow(1 - t, 2) * x1 + 3 * Math.pow(t, 2) * (1 - t) * x2 + Math.pow(t, 3) * x3;
            double y = Math.pow(1 - t, 3) * y0 + 3 * t * Math.pow(1 - t, 2) * y1 + 3 * Math.pow(t, 2) * (1 - t) * y2 + Math.pow(t, 3) * y3;
            setPixel((int) x, (int) y, color);
            t += 0.01;
        }
    }

    public void bezierCurve(PixelLoc a, PixelLoc b, PixelLoc c, PixelLoc d, ColorRGB color) {
        org.bukkit.Color bukkitColor = color.asBukkitColor();
        Color awtColor = new Color(bukkitColor.getRed(), bukkitColor.getGreen(), bukkitColor.getBlue());
        bezierCurve(a, b, c, d, awtColor);
    }

}
