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

package com.github.skapral.puzzler.gitlab.operation;


import com.github.skapral.puzzler.core.config.CpStatic;
import com.github.skapral.puzzler.core.operation.AssertOperationFails;
import com.github.skapral.puzzler.core.operation.AssertOperationSuccessful;
import com.github.skapral.puzzler.core.operation.OpDoNothing;
import com.pragmaticobjects.oo.tests.TestCase;
import com.pragmaticobjects.oo.tests.junit5.TestsSuite;


/**
 * Tests suite for {@link OpValidatingGitlabEventToken}
 *
 * @author Kapralov Sergey
 */
class OpValidatingGitlabEventTokenTest extends TestsSuite {
    /**
     * Ctor.
     */
    public OpValidatingGitlabEventTokenTest() {
        super(
            new TestCase(
                "Positive case",
                new AssertOperationSuccessful(
                    new OpValidatingGitlabEventToken(
                        new CpStatic("qwerty"),
                        "qwerty",
                        new OpDoNothing(),
                        RuntimeException::new
                    )
                )
            ),
            new TestCase(
                "Negative case",
                new AssertOperationFails(
                    new OpValidatingGitlabEventToken(
                        new CpStatic("qwerty"),
                        "asdfgh",
                        new OpDoNothing(),
                        RuntimeException::new
                    )
                )
            )
        );
    }
}