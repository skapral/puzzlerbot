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

/**
 * Text standard implementation
 *
 * @author Kapralov Sergey
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
            ArrayList<Paragraph> result = new ArrayList<>();
            for(String line : reader.lines().collect(Collectors.toList())) {
                final Paragraph.Type type;
                if(line.contains(triggerWord.value())) {
                    type = Paragraph.Type.CONTROLLING;
                } else if(!titleFound) {
                    type = Paragraph.Type.TITLE;
                    titleFound = true;
                } else {
                    type = Paragraph.Type.DESCRIPTION;
                }
                Paragraph paragraph = new ParStatic(type, line);
                result.add(paragraph);
            }
            return List.ofAll(result);
        } catch(IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
