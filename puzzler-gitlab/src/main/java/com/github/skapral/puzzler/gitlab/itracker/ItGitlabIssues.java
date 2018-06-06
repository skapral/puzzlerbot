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

package com.github.skapral.puzzler.gitlab.itracker;

import com.github.skapral.puzzler.core.IssueTracker;
import com.github.skapral.puzzler.core.Puzzle;
import com.github.skapral.puzzler.gitlab.location.GitlabAPI;
import com.github.skapral.puzzler.gitlab.project.GitlabProject;
import com.pragmaticobjects.oo.atom.anno.NotAtom;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;

import java.net.URLEncoder;

/**
 * Gitlab issue tracker ('issues' tab)
 *
 * @author Kapralov Sergey
 */
public class ItGitlabIssues implements IssueTracker {
    private final GitlabAPI api;
    private final GitlabProject project;

    /**
     * Ctor.
     *
     * @param api Gitlab API.
     * @param project Gitlab project.
     */
    public ItGitlabIssues(GitlabAPI api, GitlabProject project) {
        this.api = api;
        this.project = project;
    }

    @Override
    public final void persistPuzzle(Puzzle puzzle) {
        try {
            String endpoint = api.url()
                + String.format("/projects/%s/issues?", project.repository())
                + "title=" + URLEncoder.encode(
                    String.format("%s", puzzle.title()),
                    "UTF-8"
                )
                + "&"
                + "description=" + URLEncoder.encode(
                    String.format("%s", puzzle.description()),
                    "UTF-8"
                );
            HttpPost request = new HttpPost(
                endpoint
            );
            request.addHeader(
                "Private-Token",
                api.authenticationToken()
            );
            request.addHeader(
                "Content-Type",
                "application/json; charset=utf-8"
            );
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
