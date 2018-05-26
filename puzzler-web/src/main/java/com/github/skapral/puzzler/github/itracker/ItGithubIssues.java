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

package com.github.skapral.puzzler.github.itracker;

import com.github.skapral.puzzler.core.IssueTracker;
import com.github.skapral.puzzler.core.Puzzle;
import com.github.skapral.puzzler.github.location.GithubAPI;
import com.github.skapral.puzzler.github.project.GithubProject;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONObject;

import java.nio.charset.Charset;

/**
 * Github issue tracker ('issues' tab)
 *
 * @author Kapralov Sergey
 * @todo #20 Improve test coverage for {@link ItGithubIssues}.
 */
public class ItGithubIssues implements IssueTracker {
    private final GithubAPI api;
    private final GithubProject project;

    /**
     * Ctor.
     *
     * @param api Github API.
     * @param project Github project.
     */
    public ItGithubIssues(GithubAPI api, GithubProject project) {
        this.api = api;
        this.project = project;
    }

    @Override
    public final void persistPuzzle(Puzzle puzzle) {
        try {
            JSONObject body = new JSONObject();
            body.put("title", puzzle.title());
            body.put("body", puzzle.description());
            HttpPost request = new HttpPost(
                String.format(
                    api.url() + "/repos/%s/%s/issues",
                    project.owner(),
                    project.repository()
                )
            );
            request.addHeader(
                "Accept",
                "application/vnd.github.v3+json"
            );
            request.addHeader(
                "Authorization",
                String.format("token %s", api.authenticationToken())
            );
            request.addHeader(
                "Content-Type",
                "application/json; charset=utf-8"
            );
            request.setEntity(new StringEntity(body.toString(), Charset.forName("UTF-8")));
            CloseableHttpResponse response = HttpClients.createDefault().execute(request);
            if(response.getStatusLine().getStatusCode() > 299) {
                throw new Exception(
                    String.format("Failed request: %s", response.getStatusLine().getStatusCode())
                );
            }
        } catch(Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
