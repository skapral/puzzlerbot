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
package com.github.skapral.puzzler.core.source;

import com.github.skapral.puzzler.core.Puzzle;
import com.github.skapral.puzzler.core.PuzzleSource;
import com.github.skapral.puzzler.core.puzzle.PzlUsingThreeParsText;
import com.github.skapral.puzzler.core.text.threepars.TriggerWord;
import com.github.skapral.puzzler.core.text.threepars.TxtStandard;
import io.vavr.collection.List;

import static com.github.skapral.puzzler.core.text.threepars.Paragraph.Type.CONTROLLING;

/**
 * Puzzle source from a set of generic comments
 *
 * @author Kapralov Sergey
 */
public class PsrcFromComments implements PuzzleSource {
    private final TriggerWord triggerWord;
    private final List<String> comments;

    /**
     * Ctor.
     *
     * @param triggerWord Trigger word for three-pars algorithm
     * @param comments comments to parse
     */
    public PsrcFromComments(TriggerWord triggerWord, List<String> comments) {
        this.triggerWord = triggerWord;
        this.comments = comments;
    }

    /**
     * Ctor.
     *
     * @param triggerWord Trigger word for three-pars algorithm
     * @param comments comments to parse
     */
    public PsrcFromComments(TriggerWord triggerWord, String... comments) {
        this(
            triggerWord,
            List.of(comments)
        );
    }

    @Override
    public final List<Puzzle> puzzles() {
        return comments
            .map(body -> new TxtStandard(
                triggerWord,
                body
            ))
            .filter(txt -> txt.paragraphs().exists(p -> p.type() == CONTROLLING))
            .map(txt -> new PzlUsingThreeParsText(txt))
            .collect(List.collector());
    }
}
