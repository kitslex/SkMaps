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
import me.wyvern.map.PixelLoc;
import org.checkerframework.checker.units.qual.C;

import javax.annotation.Nullable;

public class PixelLocType {
    static {
        Classes.registerClass(new ClassInfo<PixelLoc>(PixelLoc.class, "pixelloc")
                .user("pixelloc")
                .name("PixelLoc")
                .description("A pixel location.")
                .usage("pixelloc")
                .examples("set {_pixelloc} to pixelloc 0,0", "set {_pixelloc} to pixelloc 0x0")
                .since("1.0")
                .parser(new Parser<PixelLoc>() {

                    @Override
                    public @Nullable PixelLoc parse(String s, ParseContext context) {
                        String[] split = s.split("x|,");
                        if (split.length == 2) {
                            return new PixelLoc(Integer.parseInt(split[0]), Integer.parseInt(split[1]));
                        }
                        return null;
                    }

                    @Override
                            public String toString(PixelLoc o, int flags) {
                                return "pixelloc " + o.getX() + "," + o.getY();
                            }

                            @Override
                            public String toVariableNameString(PixelLoc o) {
                                return "pixelloc " + o.getX() + "," + o.getY();
                            }
                        }
                ));
    }
}
