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

import com.github.skapral.puzzler.config.ConfigProperty;
import com.github.skapral.puzzler.core.IssueTracker;
import com.github.skapral.puzzler.core.Puzzle;
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
 */
public class ItGithubIssues implements IssueTracker {
    private final GithubProject project;
    private final ConfigProperty authToken;

    /**
     * Ctor.
     *
     * @param project Github project.
     * @param authToken Github API authentication token.
     */
    public ItGithubIssues(GithubProject project, ConfigProperty authToken) {
        this.project = project;
        this.authToken = authToken;
    }

    @Override
    public final void persistPuzzle(Puzzle puzzle) {
        try {
            JSONObject body = new JSONObject();
            body.put("title", puzzle.title());
            body.put("body", puzzle.description());
            HttpPost request = new HttpPost(
                String.format(
                    "https://api.github.com/repos/%s/%s/issues",
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
                String.format("token %s", authToken.optionalValue().get())
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

/*    public static final void main(String... args) {
        new ItGithubIssues(
            new GithubProject.Static("skapral", "_testing"),
            " 09d81461155f51a9a4af3c27d19b384f36dc7926"
        ).persistPuzzle(
            new PzlStatic(
                "The title",
                "The description"
            )
        );
    }*/
}
