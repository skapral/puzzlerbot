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

package com.github.skapral.puzzler.core.source;

import com.github.skapral.puzzler.core.Puzzle;
import com.github.skapral.puzzler.core.PuzzleSource;
import io.vavr.collection.List;
import oo.atom.tests.Assertion;
import org.assertj.core.api.Assertions;

import java.util.function.Function;

/**
 * Assertion that fails if the puzzle source under test provides
 * unexpected list of pizzles.
 *
 * @author Kapralov Sergey
 */
public class AssertPuzzleSourceProducesCertainPuzzles implements Assertion {
    private final PuzzleSource source;
    private final List<Puzzle> expectedPuzzles;

    /**
     * Ctor.
     *
     * @param source Puzzle source under the test.
     * @param expectedPuzzles Expected puzzles.
     */
    public AssertPuzzleSourceProducesCertainPuzzles(PuzzleSource source, List<Puzzle> expectedPuzzles) {
        this.source = source;
        this.expectedPuzzles = expectedPuzzles;
    }

    /**
     * Ctor.
     *
     * @param source Puzzle source under the test.
     * @param expectedPuzzles Expected puzzles.
     */
    public AssertPuzzleSourceProducesCertainPuzzles(PuzzleSource source, Puzzle... expectedPuzzles) {
        this(source, List.of(expectedPuzzles));
    }

    @Override
    public final void check() throws Exception {
        final Function<Puzzle, String> normalizer = p -> String.format("Puzzle(%s, %s)", p.title(), p.description());
        Assertions.assertThat(source.puzzles().map(normalizer))
            .containsExactlyElementsOf(expectedPuzzles.map(normalizer));
    }
}
