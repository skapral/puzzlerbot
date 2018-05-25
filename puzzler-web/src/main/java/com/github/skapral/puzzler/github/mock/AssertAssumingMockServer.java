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

package com.github.skapral.puzzler.github.mock;

import oo.atom.tests.Assertion;

/**
 * Assertion on up-and-running Github mock server
 *
 * @author Kapralov Sergey
 */
public class AssertAssumingMockServer implements Assertion {
    private final GithubMockServer server;
    private final Assertion assertion;

    /**
     * Ctor.
     *
     * @param server Mock server.
     * @param assertion Assertion to check.
     */
    public AssertAssumingMockServer(GithubMockServer server, Assertion assertion) {
        this.server = server;
        this.assertion = assertion;
    }

    @Override
    public final void check() throws Exception {
        try {
            server.bootstrap();
            assertion.check();
        } finally {
            server.destroy();
        }
    }
}
