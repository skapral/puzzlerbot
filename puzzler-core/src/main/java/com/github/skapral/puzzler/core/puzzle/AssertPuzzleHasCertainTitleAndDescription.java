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
import oo.atom.tests.Assertion;
import org.assertj.core.api.Assertions;

/**
 * Fails if the puzzle under test has title and/or description different
 * from what are expected
 *
 * @author Kapralov Sergey
 */
public class AssertPuzzleHasCertainTitleAndDescription implements Assertion {
    private final Puzzle puzzle;
    private final String title;
    private final String description;

    /**
     * Ctor.
     *
     * @param puzzle Puzzle under test.
     * @param title Expected title.
     * @param description Expected description.
     */
    public AssertPuzzleHasCertainTitleAndDescription(Puzzle puzzle, String title, String description) {
        this.puzzle = puzzle;
        this.title = title;
        this.description = description;
    }

    @Override
    public final void check() throws Exception {
        Assertions.assertThat(puzzle.title()).isEqualTo(title);
        Assertions.assertThat(puzzle.description()).isEqualTo(description);
    }
}
