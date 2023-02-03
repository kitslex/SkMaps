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

package me.wyvern.skript.types;

import ch.njol.skript.Skript;
import ch.njol.skript.classes.ClassInfo;
import ch.njol.skript.classes.Parser;
import ch.njol.skript.lang.ParseContext;
import ch.njol.skript.registrations.Classes;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ImageFileType {

    public static final List<String> IMAGE_FILE_EXTENSIONS = new ArrayList<String>(List.of(new String[]{
            "png", "jpg", "jpeg", "bmp", "gif"
    }));
    static {
        Classes.registerClass(new ClassInfo<BufferedImage>(BufferedImage.class, "imagefile")
                .user("imagefile")
                .name("ImageFile")
                .description("An image file.")
                .usage("imagefile")
                .examples("set {_imagefile} to imagefile \"C:\\Users\\Wyvern\\Desktop\\image.png\"")
                .since("1.0")
                .parser(new Parser<BufferedImage>() {
                    @Override
                    public BufferedImage parse(String s, ParseContext context) {
                        File file = new File(s);
                        if (!file.exists()) Skript.error("File does not exist!");
                        if (!file.isFile()) Skript.error("File is not a file!");
                        if (!file.canRead()) Skript.error("File is not readable!");
                        String extension = s.substring(s.lastIndexOf(".") + 1);
                        if (!IMAGE_FILE_EXTENSIONS.contains(extension)) Skript.error("File is not an image file!");
                        BufferedImage image;
                        try {
                            image = ImageIO.read(file);
                        } catch (IOException e) {
                            Skript.error("File could not be read!: " + e.getMessage());
                            return null;
                        }
                        return image;
                    }

                    @Override
                    public String toString(BufferedImage o, int flags) {
                        return "imagefile " + o.toString();
                    }

                    @Override
                    public String toVariableNameString(BufferedImage o) {
                        return "imagefile " + o.toString();
                    }
                }));
    }
}
