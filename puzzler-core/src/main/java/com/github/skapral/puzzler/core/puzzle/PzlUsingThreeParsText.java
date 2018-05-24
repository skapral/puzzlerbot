/*
 * MIT License
 *
 * Copyright (c) 2018 Kapralov Sergey
 *
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
 *
 */

package com.github.skapral.puzzler.core.puzzle;

import com.github.skapral.puzzler.core.Puzzle;
import com.github.skapral.puzzler.core.text.threepars.Paragraph;
import com.github.skapral.puzzler.core.text.threepars.Text;

import java.util.stream.Collectors;


/**
 * Puzzler, which uses a text, parsed using three-paragraphs approach.
 * (see {@link com.github.skapral.puzzler.core.text.threepars} for details)
 *
 * @author Kapralov Sergey
 */
public class PzlUsingThreeParsText implements Puzzle {
    private final Text text;

    /**
     * Ctor.
     *
     * @param text Text.
     */
    public PzlUsingThreeParsText(Text text) {
        this.text = text;
    }

    @Override
    public final String title() {
        return text.paragraphs()
            .filter(p -> p.type() == Paragraph.Type.TITLE)
            .map(Paragraph::content)
            .get(0);
    }

    @Override
    public final String description() {
        return text.paragraphs()
            .filter(p -> p.type() == Paragraph.Type.DESCRIPTION)
            .map(Paragraph::content)
            .collect(Collectors.joining("\r\n"));
    }
}
