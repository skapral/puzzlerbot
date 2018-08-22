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
 *
 */

package com.github.skapral.puzzler.github.itracker;

import com.github.skapral.puzzler.core.operation.AssertOperationMakesHttpCallsOnMockServer;
import com.github.skapral.puzzler.core.operation.OpPersistAllPuzzles;
import com.github.skapral.puzzler.core.puzzle.PzlStatic;
import com.github.skapral.puzzler.core.source.PsrcStatic;
import com.github.skapral.puzzler.github.location.GhapiStatic;
import com.github.skapral.puzzler.github.project.GithubProject;
import com.github.skapral.puzzler.mock.MockSrvImplementation;
import com.pragmaticobjects.oo.tests.TestCase;
import com.pragmaticobjects.oo.tests.junit5.TestsSuite;
import org.mockserver.model.JsonBody;

/**
 * Tests suite for {@link ItGithubIssues}
 *
 * @author Kapralov Sergey
 */
class ItGithubIssuesTest extends TestsSuite {
    /**
     * Ctor.
     */
    public ItGithubIssuesTest() {
        super(
            new TestCase(
                "the puzzle is successfully posted to GitHub",
                new AssertOperationMakesHttpCallsOnMockServer(
                    new MockSrvImplementation(
                        8080,
                        req -> req.withMethod("POST")
                            .withPath("/repos/dummy/dummyrepo/issues")
                            .withBody(
                                new JsonBody("{\"title\":\"test issue\",\"body\":\"test description\"}")
                            ),
                        resp -> resp.withBody("pong")
                    ),
                    new OpPersistAllPuzzles(
                        new PsrcStatic(
                            new PzlStatic(
                                "test issue",
                                "test description"
                            )
                        ),
                        new ItGithubIssues(
                            new GhapiStatic(
                                "http://localhost:8080",
                                "dummyToken"
                            ),
                            new GithubProject.Static(
                                "dummy",
                                "dummyrepo"
                            )
                        )
                    )
                )
            )
        );
    }
}