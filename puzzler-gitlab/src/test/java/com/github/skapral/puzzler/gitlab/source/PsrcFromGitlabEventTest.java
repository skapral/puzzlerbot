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

package com.github.skapral.puzzler.gitlab.source;

import java.nio.charset.Charset;

import com.github.skapral.puzzler.gitlab.location.GlapiStatic;
import org.apache.commons.io.IOUtils;

import com.github.skapral.puzzler.core.config.CpStatic;
import com.github.skapral.puzzler.core.puzzle.PzlStatic;
import com.github.skapral.puzzler.core.source.AssertPuzzleSourceProducesCertainPuzzles;
import com.github.skapral.puzzler.core.source.AssertPuzzleSourceProducesNoPuzzles;
import com.github.skapral.puzzler.mock.AssertAssumingMockServer;
import com.github.skapral.puzzler.mock.MockSrvImplementation;
import com.pragmaticobjects.oo.tests.TestCase;
import com.pragmaticobjects.oo.tests.junit5.TestsSuite;

class PsrcFromGitlabEventTest extends TestsSuite {
    private static final String COMMENTS;
    private static final String ISSUE_EVENT;

    static {
        try {
            COMMENTS = IOUtils.resourceToString("/testComments.json", Charset.defaultCharset());
            ISSUE_EVENT = IOUtils.resourceToString("/testIssueEvent.json", Charset.defaultCharset());
        } catch(Exception ex) {
            throw new RuntimeException(ex);
        }
    }


    public PsrcFromGitlabEventTest() {
        super(
            new TestCase(
                "source produces correct puzzles from issue event",
                new AssertAssumingMockServer(
                    new MockSrvImplementation(
                        8080,
                        req -> req.withPath("/projects/1/issues/23/notes"),
                        res -> res.withBody(COMMENTS)
                    ),
                    new AssertPuzzleSourceProducesCertainPuzzles(
                        new PsrcFromGitlabEvent(
                            new GlapiStatic(
                                "http://localhost:8080",
                                "fakeToken"
                            ),
                            "Issue Hook",
                            ISSUE_EVENT
                        ),
                        new PzlStatic(
                            "Issue 1",
                            ""
                        ),
                        new PzlStatic(
                            "Issue 2",
                            ""
                        )
                    )
                )
            )/*,
            new TestCase(
                "source produces zero puzzles from event of unknown type",
                new AssertPuzzleSourceProducesNoPuzzles(
                    new PsrcFromGitlabEvent(
                        new GlapiStatic(
                            "http://localhost:8080",
                            "fakeToken"
                        ),
                        "unknown_event",
                        "{}"
                    )
                )
            ),
            new TestCase(
                "source produces zero puzzles from issue event with non-closed action",
                new AssertPuzzleSourceProducesNoPuzzles(
                    new PsrcFromGitlabEvent(
                        new GlapiStatic(
                            "http://localhost:8080",
                            "fakeToken"
                        ),
                        "Issue Hook",
                        "{\"object_attributes\":{\"action\":\"reopened\"}}"
                    )
                )
            )*/
        );
    }
}