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

import ch.njol.skript.classes.ClassInfo;
import ch.njol.skript.classes.Parser;
import ch.njol.skript.lang.ParseContext;
import ch.njol.skript.registrations.Classes;
import me.wyvern.util.ColorRGBA;

import javax.annotation.Nullable;

public class ColorType {

    static {
        Classes.registerClass(new ClassInfo<>(ColorRGBA.class, "colorrgba")
                .user("color")
                .name("Color")
                .description("A color.")
                .usage("color")
                .examples("set {_color} to color 0,0,0,255")
                .since("1.0")
                .parser(new Parser<ColorRGBA>() {

                    @Override
                    public @Nullable ColorRGBA parse(String s, ParseContext context) {
                        String[] split = s.split(",");
                        if (split.length == 3) {
                            return new ColorRGBA(Integer.parseInt(split[0]), Integer.parseInt(split[1]), Integer.parseInt(split[2]));
                        } else if (split.length == 4) {
                            return new ColorRGBA(Integer.parseInt(split[0]), Integer.parseInt(split[1]), Integer.parseInt(split[2]), Integer.parseInt(split[3]));
                        }
                        return null;
                    }

                    @Override
                    public String toString(ColorRGBA o, int flags) {
                        return o.toString();
                    }

                    @Override
                    public String toVariableNameString(ColorRGBA o) {
                        return o.toString();
                    }
                }));

    }
}
