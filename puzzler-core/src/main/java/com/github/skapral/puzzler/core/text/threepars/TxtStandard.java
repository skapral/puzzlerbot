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

package com.github.skapral.puzzler.core.text.threepars;

import io.vavr.collection.List;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Text standard implementation. Splits the string by three-paragraph approach,
 * omitting leading and trailing empty paragraphs.
 *
 * @author Kapralov Sergey
 * @todo #7 Split this object to two: one for splitting text to paragraphs,
 *  another for trimming empty paragraphs in the beginning and end.
 */
public class TxtStandard implements Text {
    private final TriggerWord triggerWord;
    private final String text;

    /**
     * Ctor.
     *
     * @param triggerWord Trigger word.
     * @param text Text to parse.
     */
    public TxtStandard(TriggerWord triggerWord, String text) {
        this.triggerWord = triggerWord;
        this.text = text;
    }

    @Override
    public final List<Paragraph> paragraphs() {
        try(BufferedReader reader = new BufferedReader(new StringReader(text))) {
            boolean titleFound = false;
            boolean firstNonEmptyParagraphFound = false;
            int emptyParagraphsCounter = 0;
            ArrayList<Paragraph> result = new ArrayList<>();
            for(String line : reader.lines().collect(Collectors.toList())) {
                if(line.trim().isEmpty()) {
                    emptyParagraphsCounter++;
                } else if(line.contains(triggerWord.value())) {
                    result.add(
                        new ParStatic(Paragraph.Type.CONTROLLING, line)
                    );
                } else if(!titleFound) {
                    titleFound = true;
                    emptyParagraphsCounter = 0;
                    result.add(
                        new ParStatic(Paragraph.Type.TITLE, line)
                    );
                } else if(!firstNonEmptyParagraphFound) {
                    firstNonEmptyParagraphFound = true;
                    emptyParagraphsCounter = 0;
                    result.add(
                        new ParStatic(
                            Paragraph.Type.DESCRIPTION,
                            line
                        )
                    );
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
                    result.add(
                        new ParStatic(
                            Paragraph.Type.DESCRIPTION,
                            line
                        )
                    );
                    emptyParagraphsCounter = 0;
                }
            }
            return List.ofAll(result);
        } catch(IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
