/*
 * MIT License
 *
 * Copyright (c) %today.year Kapralov Sergey
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
 *
 */

package com.github.skapral.puzzler.core.config;

import com.pragmaticobjects.oo.tests.TestCase;
import com.pragmaticobjects.oo.tests.junit5.TestsSuite;

/**
 * Tests suite for {@link CpOneOf}
 *
 * @author Kapralov Sergey
 */
class CpOneOfTest extends TestsSuite {
    /**
     * Ctor.
     */
    public CpOneOfTest() {
        super(
            new TestCase(
                "returns first initialized value",
                new AssertConfigPropertyHasValue(
                    new CpOneOf(
                        new CpStatic("test"),
                        new CpStatic("test2")
                    ),
                    "test"
                )
            ),
            new TestCase(
                "falls back to the other config properties",
                new AssertConfigPropertyHasValue(
                    new CpOneOf(
                        new CpMissingValue(),
                        new CpStatic("test2")
                    ),
                    "test2"
                )
            ),
            new TestCase(
                "is unitialized if every options were failed",
                new AssertConfigPropertyIsUnitialized(
                    new CpOneOf(
                        new CpMissingValue(),
                        new CpMissingValue(),
                        new CpMissingValue()
                    )
                )
            )
        );
    }
}