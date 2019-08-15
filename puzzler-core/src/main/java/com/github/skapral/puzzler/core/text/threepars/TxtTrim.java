/*-
 * ===========================================================================
 * puzzler-core
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * Copyright (C) 2019 Kapralov Sergey
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 * ============================================================================
 */
package com.github.skapral.puzzler.core.text.threepars;

import io.vavr.collection.List;

import java.util.ArrayList;
import java.util.function.Predicate;
import java.util.stream.IntStream;

import static com.github.skapral.puzzler.core.text.threepars.Paragraph.Type.CONTROLLING;
import static com.github.skapral.puzzler.core.text.threepars.Paragraph.Type.DESCRIPTION;
import static com.github.skapral.puzzler.core.text.threepars.Paragraph.Type.TITLE;


/**
 * Text, omitting empty leading and trailing paragraphs.
 *
 * @author Kapralov Sergey
 */
public class TxtTrim implements Text {
    private final Text text;

    /**
     * Ctor.
     *
     * @param text Text to trim
     */
    public TxtTrim(Text text) {
        this.text = text;
    }

    @Override
    public final List<Paragraph> paragraphs() {
        boolean firstNonEmptyParagraphFound = false;
        int emptyParagraphsCounter = 0;
        final ArrayList<Paragraph> result = new ArrayList<>();
        final Predicate<Paragraph> emptyDescriptionParagraph = p ->
            p.type().equals(DESCRIPTION) &&
            p.content().trim().isEmpty();
        for(Paragraph paragraph : text.paragraphs()) {
            if(emptyDescriptionParagraph.test(paragraph)) {
                emptyParagraphsCounter++;
            } else if(paragraph.type().equals(CONTROLLING)) {
                result.add(paragraph);
            } else if(paragraph.type().equals(TITLE)) {
                emptyParagraphsCounter = 0;
                result.add(paragraph);
            } else if(!firstNonEmptyParagraphFound && !paragraph.content().isEmpty()) {
                firstNonEmptyParagraphFound = true;
                emptyParagraphsCounter = 0;
                result.add(paragraph);
            } else {
                IntStream.range(
                    0,
                    emptyParagraphsCounter
                ).forEach(
                    i -> result.add(
                        new ParStatic(
                            Paragraph.Type.DESCRIPTION,
                            ""
                        )
                    )
                );
                result.add(paragraph);
                emptyParagraphsCounter = 0;
            }
        }
        return List.ofAll(result);
    }
}
