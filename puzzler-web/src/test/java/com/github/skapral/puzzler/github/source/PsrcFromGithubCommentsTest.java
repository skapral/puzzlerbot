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

package com.github.skapral.puzzler.github.source;

import com.github.skapral.puzzler.core.puzzle.PzlStatic;
import com.github.skapral.puzzler.core.source.AssertPuzzleSourceProducesCertainPuzzles;
import oo.atom.tests.TestCase;
import oo.atom.tests.TestsSuite;
import org.apache.commons.io.IOUtils;

import java.nio.charset.Charset;

public class PsrcFromGithubCommentsTest extends TestsSuite {
    private static final String COMMENTS;

    static {
        try {
            COMMENTS = IOUtils.resourceToString("/testComments.json", Charset.defaultCharset());
        } catch(Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public PsrcFromGithubCommentsTest() {
        super(
            new TestCase(
                "puzzle source parses JSON comments correctly",
                new AssertPuzzleSourceProducesCertainPuzzles(
                    new PsrcFromGithubComments(
                        COMMENTS
                    ),
                    new PzlStatic(
                        "Something went wrong with some subsystem",
                        "**Steps to reproduce**:\r\n1. Step one\r\n2. Step two\r\n3. Step three\r\n\r\n**Expected result**:\r\nEverything went smooth\r\n\r\n**Actual result**:\r\nEverything goes apart"
                    )
                )
            )
        );
    }
}