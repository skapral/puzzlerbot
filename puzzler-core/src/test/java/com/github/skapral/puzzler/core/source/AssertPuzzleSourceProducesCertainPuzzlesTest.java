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

import com.github.skapral.puzzler.core.puzzle.PzlStatic;
import com.pragmaticobjects.oo.tests.AssertAssertionFails;
import com.pragmaticobjects.oo.tests.AssertAssertionPasses;
import com.pragmaticobjects.oo.tests.TestCase;
import com.pragmaticobjects.oo.tests.junit5.TestsSuite;

/**
 * Tests suite for {@link AssertPuzzleSourceProducesCertainPuzzles}
 *
 * @author Kapralov Sergey
 */
class AssertPuzzleSourceProducesCertainPuzzlesTest extends TestsSuite {
    /**
     * Ctor.
     */
    public AssertPuzzleSourceProducesCertainPuzzlesTest() {
        super(
            new TestCase(
                "positive case",
                new AssertAssertionPasses(
                    new AssertPuzzleSourceProducesCertainPuzzles(
                        new PsrcStatic(
                            new PzlStatic(
                                "test puzzle",
                                "desc"
                            )
                        ),
                        new PzlStatic(
                            "test puzzle",
                            "desc"
                        )
                    )
                )
            ),
            new TestCase(
                "negative case",
                new AssertAssertionFails(
                    new AssertPuzzleSourceProducesCertainPuzzles(
                        new PsrcStatic(
                            new PzlStatic(
                                "test puzzle",
                                "desc"
                            )
                        ),
                        new PzlStatic(
                            "test puzzle #2",
                            "some other desc"
                        )
                    )
                )
            ),
            new TestCase(
                "an order of produced puzzles doesn't matter",
                new AssertAssertionPasses(
                    new AssertPuzzleSourceProducesCertainPuzzles(
                        new PsrcStatic(
                            new PzlStatic(
                                "test puzzle 1",
                                "desc"
                            ),
                            new PzlStatic(
                                "test puzzle 2",
                                "desc"
                            )
                        ),
                        new PzlStatic(
                            "test puzzle 2",
                            "desc"
                        ),
                        new PzlStatic(
                            "test puzzle 1",
                            "desc"
                        )
                    )
                )
            )
        );
    }
}
