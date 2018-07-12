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

import com.github.skapral.puzzler.core.PuzzleSource;
import com.github.skapral.puzzler.core.source.PsrcInferred;
import com.github.skapral.puzzler.core.source.PsrcStatic;
import com.github.skapral.puzzler.gitlab.location.GitlabAPI;
import io.vavr.control.Option;
import org.apache.commons.io.IOUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONObject;

/**
 * Puzzles source, which parses puzzles from arbitrary Gitlab event.
 *
 * @author Kapralov Sergey
 */
public class PsrcFromGitlabEvent extends PsrcInferred {
    /**
     * Ctor.
     *
     * @param api Gitlab API
     * @param eventType Gitlab event's type (usually passed via X-GitHub-Event header).
     * @param eventBody Gitlab event's body in JSON format.
     */
    public PsrcFromGitlabEvent(GitlabAPI api, String eventType, String eventBody) {
        super(
            new Inference(
                api,
                eventType,
                eventBody
            )
        );
    }

    /**
     * The {@link PsrcFromGitlabEvent} inference.
     *
     * @author Kapralov Sergey
     * @todo #27 refactor this class by splitting it to smaller composable components
     */
    private static class Inference implements PuzzleSource.Inference {
        private final GitlabAPI api;
        private final String eventType;
        private final String eventBody;

        /**
         * Ctor.
         *
         * @param api Gitlab API
         * @param eventType Gitlab event's type (usually passed via X-Gitlab-Event header).
         * @param eventBody Gitlab event's body in JSON format.
         */
        public Inference(GitlabAPI api, String eventType, String eventBody) {
            this.api = api;
            this.eventType = eventType;
            this.eventBody = eventBody;
        }



        @Override
        public final PuzzleSource puzzleSource() {
            final Option<JSONObject> jsonObject;
            final String endpoint;
            switch(eventType.toLowerCase()) {
                case "issue hook": {
                    endpoint = "issues";
                    jsonObject = Option.of(new JSONObject(eventBody))
                        .filter(jo -> jo.getJSONObject("object_attributes").has("action"))
                        .filter(jo -> "close".equals(jo.getJSONObject("object_attributes").getString("action")));
                    break;
                }
                case "merge request hook": {
                    endpoint = "merge_requests";
                    jsonObject = Option.of(new JSONObject(eventBody))
                        .filter(jo -> jo.getJSONObject("object_attributes").has("action"))
                        .filter(jo -> "merge".equals(jo.getJSONObject("object_attributes").getString("action")));
                    break;
                }
                default: {
                    return new PsrcStatic();
                }
            }
            return jsonObject
                .<PuzzleSource>map(obj -> {
                    final String commentsUrl = api.url()
                        + "/projects/" + obj.getJSONObject("project").get("id")
                        + "/" + endpoint + "/" + obj.getJSONObject("object_attributes").get("iid")
                        + "/notes";
                    final HttpGet request = new HttpGet(commentsUrl);
                    request.addHeader(
                        "Private-Token",
                        api.authenticationToken()
                    );
                    try {
                        final CloseableHttpResponse response = HttpClients.createDefault().execute(request);
                        if(response.getStatusLine().getStatusCode() > 299) {
                            throw new RuntimeException();
                        }
                        return new PsrcFromGitlabNotes(
                            IOUtils.toString(response.getEntity().getContent(), "UTF-8")
                        );
                    } catch(Exception ex) {
                        throw new RuntimeException(ex);
                    }
                })
                .getOrElse(() -> new PsrcStatic());
        }
    }
}
