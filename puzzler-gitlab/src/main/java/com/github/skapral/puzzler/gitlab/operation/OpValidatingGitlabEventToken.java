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

import com.github.skapral.puzzler.core.Operation;
import com.github.skapral.config.ConfigProperty;

import java.util.function.Function;

/**
 * Operation which is executed only if GitLab event token
 * match on GitLab event. See <a href="https://docs.gitlab.com/ee/user/project/integrations/webhooks.html#secret-token"></a>
 * for details.
 *
 * @author Kapralov Sergey
 */
public class OpValidatingGitlabEventToken implements Operation {
    private final ConfigProperty expectedToken;
    private final String eventToken;
    private final Operation delegate;
    private final Function<String, RuntimeException> exceptionFn;

    /**
     * Ctor.
     *
     * @param expectedToken Secret. If the property value is not provided, validation will be skept.
     * @param eventToken Event token (X-Gitlab-Token)
     * @param delegate Operation to delegate to, if signatures match.
     * @param exceptionFn Constructor of exception to throw if signatures mismatch.
     */
    public OpValidatingGitlabEventToken(ConfigProperty expectedToken, String eventToken, Operation delegate, Function<String, RuntimeException> exceptionFn) {
        this.expectedToken = expectedToken;
        this.eventToken = eventToken;
        this.delegate = delegate;
        this.exceptionFn = exceptionFn;
    }

    @Override
    public final void execute() {
        if(expectedToken.optionalValue().isEmpty()) {
            delegate.execute();
        } else {
            if (expectedToken.optionalValue().get().equals(eventToken)) {
                delegate.execute();
            } else {
                throw exceptionFn.apply("Token mismatch");
            }
        }
    }
}
