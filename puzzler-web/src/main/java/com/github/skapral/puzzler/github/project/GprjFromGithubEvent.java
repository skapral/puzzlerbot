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

package com.github.skapral.puzzler.github.project;

import org.json.JSONObject;

/**
 * Github project's coordinates, obtained from Github event JSON.
 *
 * @author Kapralov Sergey
 */
public class GprjFromGithubEvent implements GithubProject {
    private final String eventBody;

    /**
     * Ctor.
     *
     * @param eventBody Github event body (JSON)
     */
    public GprjFromGithubEvent(String eventBody) {
        this.eventBody = eventBody;
    }

    @Override
    public final String owner() {
        return new JSONObject(eventBody)
            .getJSONObject("repository")
            .getJSONObject("owner")
            .getString("login");
    }

    @Override
    public final String repository() {
        return new JSONObject(eventBody)
            .getJSONObject("repository")
            .getString("name");
    }
}
