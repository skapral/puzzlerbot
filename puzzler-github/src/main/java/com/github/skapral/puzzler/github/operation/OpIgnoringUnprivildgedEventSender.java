/*-
 * ===========================================================================
 * puzzler-github
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
package com.github.skapral.puzzler.github.operation;

import com.github.skapral.puzzler.core.Operation;
import io.vavr.control.Option;
import org.json.JSONObject;

/**
 * Operation, which is executed only if GitHub event sender and
 * repository owner are the same person.
 *
 * @author Kapralov Sergey
 */
public class OpIgnoringUnprivildgedEventSender implements Operation {
    private final String githubEvent;
    private final Operation delegate;

    /**
     * Ctor.
     *
     * @param githubEvent GitHub webhook event to validate (JSON)
     * @param delegate operation to pass control to if verification is ok
     */
    public OpIgnoringUnprivildgedEventSender(String githubEvent, Operation delegate) {
        this.githubEvent = githubEvent;
        this.delegate = delegate;
    }

    @Override
    public final void execute() {
        final JSONObject jsonObject = new JSONObject(githubEvent);
        final String repoOwner = Option.of(jsonObject)
            .map(jo -> jo.getJSONObject("repository"))
            .map(jo -> jo.getJSONObject("owner"))
            .map(jo -> jo.getString("login"))
            .get();
        final String eventOwner = Option.of(jsonObject)
            .map(jo -> jo.getJSONObject("sender"))
            .map(jo -> jo.getString("login"))
            .get();
        if(repoOwner.equals(eventOwner)) {
            delegate.execute();
        }
    }
}
