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

import com.github.skapral.puzzler.core.config.CpStatic;
import com.github.skapral.puzzler.core.puzzle.PzlStatic;
import com.github.skapral.puzzler.core.source.AssertPuzzleSourceProducesCertainPuzzles;
import com.github.skapral.puzzler.core.source.AssertPuzzleSourceProducesNoPuzzles;
import com.github.skapral.puzzler.mock.AssertAssumingMockServer;
import com.github.skapral.puzzler.mock.MockSrvImplementation;
import com.pragmaticobjects.oo.tests.TestCase;
import com.pragmaticobjects.oo.tests.junit5.TestsSuite;
import org.apache.commons.io.IOUtils;

import java.nio.charset.Charset;

class PsrcFromGithubEventTest extends TestsSuite {
    private static final String COMMENTS;
    private static final String ISSUE_EVENT;
    private static final String PR_EVENT;

    static {
        try {
            COMMENTS = IOUtils.resourceToString("/testComments.json", Charset.defaultCharset());
            ISSUE_EVENT = IOUtils.resourceToString("/testIssueEvent.json", Charset.defaultCharset());
            PR_EVENT = IOUtils.resourceToString("/testPullRequestEvent.json", Charset.defaultCharset());
        } catch(Exception ex) {
            throw new RuntimeException(ex);
        }
    }


    public PsrcFromGithubEventTest() {
        super(
            new TestCase(
                "source produces correct puzzles from issue event",
                new AssertAssumingMockServer(
                    new MockSrvImplementation(
                        8080,
                        req -> req.withPath("/repos/skapral/_testing/issues/9/comments"),
                        res -> res.withBody(COMMENTS)
                    ),
                    new AssertPuzzleSourceProducesCertainPuzzles(
                        new PsrcFromGithubEvent(
                            "issues",
                            ISSUE_EVENT,
                            new CpStatic("fakeToken")
                        ),
                        new PzlStatic(
                            "Something went wrong with some subsystem",
                            String.join(
                                "\r\n",
                                "**Steps to reproduce**:",
                                "1. Step one",
                                "2. Step two",
                                "3. Step three",
                                "",
                                "**Expected result**:",
                                "Everything went smooth",
                                "",
                                "**Actual result**:",
                                "Everything goes apart"
                            )
                        )
                    )
                )
            ),
            new TestCase(
                "source produces correct puzzles from pull request event",
                new AssertAssumingMockServer(
                    new MockSrvImplementation(
                        8080,
                        req -> req.withPath("/repos/skapral/_testing/issues/14/comments"),
                        res -> res.withBody(COMMENTS)
                    ),
                    new AssertPuzzleSourceProducesCertainPuzzles(
                        new PsrcFromGithubEvent(
                            "pull_request",
                            PR_EVENT,
                            new CpStatic("fakeToken")
                        ),
                        new PzlStatic(
                            "Something went wrong with some subsystem",
                            String.join(
                                "\r\n",
                                "**Steps to reproduce**:",
                                "1. Step one",
                                "2. Step two",
                                "3. Step three",
                                "",
                                "**Expected result**:",
                                "Everything went smooth",
                                "",
                                "**Actual result**:",
                                "Everything goes apart"
                            )
                        )
                    )
                )
            ),
            new TestCase(
                "source produces zero puzzles from event of unknown type",
                new AssertPuzzleSourceProducesNoPuzzles(
                    new PsrcFromGithubEvent(
                        "unknown_event",
                        "{}",
                        new CpStatic("fakeToken")
                    )
                )
            ),
            new TestCase(
                "source produces zero puzzles from issue event with non-closed action",
                new AssertPuzzleSourceProducesNoPuzzles(
                    new PsrcFromGithubEvent(
                        "issues",
                        "{\"action\":\"reopened\", \"issue\":{}}",
                        new CpStatic("fakeToken")
                    )
                )
            ),
            new TestCase(
                "source produces zero puzzles is pull request is closed without merging",
                new AssertPuzzleSourceProducesNoPuzzles(
                    new PsrcFromGithubEvent(
                        "pull_request",
                        "{\"action\":\"closed\", \"pull_request\":{\"merged\":false}}",
                        new CpStatic("fakeToken")
                    )
                )
            )
        );
    }
}